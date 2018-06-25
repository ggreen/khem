///////////////////////////////////////////////////////////////////////////////
//  Filename: $RCSfile: Title2Data.java,v $
//  Purpose:  Calls corina to create 3D structures.
//  Language: Java
//  Compiler: JDK 1.4
//  Authors:  Joerg Kurt Wegner
//  Version:  $Revision: 1.12 $
//            $Date: 2005/03/03 07:13:36 $
//            $Author: wegner $
//
// Copyright OELIB:          OpenEye Scientific Software, Santa Fe,
//                           U.S.A., 1999,2000,2001
// Copyright JOELIB/JOELib2: Dept. Computer Architecture, University of
//                           Tuebingen, Germany, 2001,2002,2003,2004,2005
// Copyright JOELIB/JOELib2: ALTANA PHARMA AG, Konstanz, Germany,
//                           2003,2004,2005
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation version 2 of the License.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
///////////////////////////////////////////////////////////////////////////////
package joelib2.ext;

import joelib2.feature.FeatureHelper;

import joelib2.feature.result.StringVectorResult;

import joelib2.io.BasicIOTypeHolder;
import joelib2.io.IOType;
import joelib2.io.MoleculeFileHelper;
import joelib2.io.MoleculeFileIO;
import joelib2.io.MoleculeIOException;

import joelib2.molecule.BasicConformerMolecule;
import joelib2.molecule.Molecule;

import joelib2.molecule.types.BasicPairData;

import joelib2.process.MoleculeProcessException;

import joelib2.util.HelperMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;


/**
 * Simple example for calling external programs.
 *
 * This class gets the title from a molecule and stores it as descriptor value.
 *
 * @.author     wegnerj
 * @.license GPL
 * @.cvsversion    $Revision: 1.12 $, $Date: 2005/03/03 07:13:36 $
 * @see External
 */
public class Title2Data extends SimpleExternalProcess
{
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     *  Obtain a suitable logger.
     */
    private static Logger logger = LogManager.getLogger(
            "joelib2.ext.Title2Data");

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *  Constructor for the Corina object
     */
    public Title2Data()
    {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     *  Gets the thisOSsupported attribute of the Corina object
     *
     * @return    The thisOSsupported value
     */
    public boolean isThisOSsupported()
    {
        if (ExternalHelper.getOperationSystemName().equals(
                    ExternalHelper.OS_LINUX) /*||
                                                                                                             ExternalHelper.getOperationSystemName().equals( ExternalHelper.OS_WINDOWS )*/)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *  Description of the Method
     *
     * @param  mol  Description of the Parameter
     * @return      Description of the Return Value
     */
    public boolean process(Molecule mol, Hashtable properties)
        throws MoleculeProcessException
    {
        super.process(mol, properties);

        // generate executable command line
        List argsVector = getExternalInfo().getArguments();

        if ((argsVector == null) || (argsVector.size() == 0))
        {
            logger.error("External " + this.getClass().getName() +
                " not properly defined. See " + getDescriptionFile());

            return false;
        }

        String[] args = new String[argsVector.size() + 1];
        args[0] = getExternalInfo().getExecutable(ExternalHelper
                .getOperationSystemName());

        for (int i = 0; i < (args.length - 1); i++)
        {
            args[i + 1] = (String) argsVector.get(i);
        }

        // get molecule string
        String molString = toMolString(mol);

        if (molString == null)
        {
            logger.error("Molecule not writeable");

            return false;
        }

        // save old pairdata
        Vector data = new Vector(20);

        if (mol.hasData(FeatureHelper.COMMENT_IDENTIFIER))
        {
            StringVectorResult commentData = (StringVectorResult) mol.getData(
                    FeatureHelper.COMMENT_IDENTIFIER);
            data.add(commentData);
        }

        // execute title2data
        Process process;
        StringBuffer buffer = new StringBuffer(1000);

        try
        {
            Runtime runtime = Runtime.getRuntime();
            logger.info("Starting: " + args[0] + " " + args[1]);
            process = runtime.exec(args);

            // set input pipe
            BufferedReader in = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));

            // set output pipe
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        process.getOutputStream()));
            out.write(molString, 0, molString.length());

            //System.out.println(molString);
            out.close();

            // open java output, cpp input pipe
            //      StreamOut output = new StreamOut(process.getOutputStream(), molString);
            //      // kick them off
            //      output.start();
            // wait for extern process termination
            //      System.out.println("wait");
            process.waitFor();

            //      System.out.println("waited");
            // get input pipe data
            String nextLine = null;

            while ((nextLine = in.readLine()) != null)
            {
                buffer.append(nextLine + HelperMethods.eol);

                //        System.out.println("title2data: "+nextLine);
            }
        }
        catch (Exception e)
        {
            logger.error("Could not start executable: " + args[0]);
            e.printStackTrace();

            return false;
        }

        // getting new molecule
        Molecule tmpMol = new BasicConformerMolecule(mol);

        // create backup
        IOType inType = mol.getInputType();
        IOType outType = mol.getOutputType();
        mol.clear();

        ByteArrayInputStream sReader = new ByteArrayInputStream(buffer
                .toString().getBytes());

        // get molecule loader
        MoleculeFileIO loader = null;

        try
        {
            loader = MoleculeFileHelper.getMolReader(sReader,
                    BasicIOTypeHolder.instance().getIOType("SDF"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();

            return false;
        }
        catch (MoleculeIOException ex)
        {
            ex.printStackTrace();

            return false;
        }

        if (!loader.readable())
        {
            // should never happen
            logger.error(inType.getRepresentation() + " is not readable.");
            logger.error("You're invited to write one !;-)");

            return false;
        }

        // load molecules and restore old data
        mol.setInputType(inType);
        mol.setOutputType(outType);

        boolean success = true;

        try
        {
            success = loader.read(mol);

            if (!success)
            {
                mol.set(tmpMol);

                return false;
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            mol.set(tmpMol);

            return false;
        }
        catch (MoleculeIOException ex)
        {
            ex.printStackTrace();

            return false;
        }

        if (mol.isEmpty())
        {
            logger.error("No molecule after " + this.getClass().getName() +
                " execution loaded.");
            mol.set(tmpMol);

            return false;
        }

        // restore old descriptor data
        boolean overwriteProp = true;
        BasicPairData pairData;

        for (int i = 0; i < data.size(); i++)
        {
            pairData = (BasicPairData) data.get(i);

            if (overwriteProp)
            {
                String attribute = pairData.getKey();

                if (!mol.hasData(attribute))
                {
                    mol.addData((BasicPairData) data.get(i));
                }
            }
            else
            {
                mol.addData((BasicPairData) data.get(i));
            }
        }

        return true;
    }

    /**
     *  Description of the Method
     *
     * @param  mol  Description of the Parameter
     * @return      Description of the Return Value
     */
    private String toMolString(Molecule mol)
    {
        // write without descriptor properties if possible
        // should be faster.
        return mol.toString(BasicIOTypeHolder.instance().getIOType("SDF"),
                false);
    }
}

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
