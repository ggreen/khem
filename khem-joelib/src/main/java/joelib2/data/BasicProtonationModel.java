///////////////////////////////////////////////////////////////////////////////
//  Filename: $RCSfile: BasicProtonationModel.java,v $
//  Purpose:  Model for PH values.
//  Language: Java
//  Compiler: JDK 1.4
//  Authors:  Joerg Kurt Wegner
//  Version:  $Revision: 1.7 $
//            $Date: 2005/02/17 16:48:29 $
//            $Author: wegner $
//  Original Author: ???, OpenEye Scientific Software
//  Original Version: babel 2.0a1
//
//  Copyright (c) Dept. Computer Architecture, University of Tuebingen,
//                Germany, 2001-2005
//, 2003-2005
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
package joelib2.data;

import joelib2.molecule.Molecule;

import joelib2.smarts.BasicSMARTSPatternMatcher;
import joelib2.smarts.SMARTSPatternMatcher;

import joelib2.smarts.types.BasicSMARTSPatternDoubles;

import joelib2.util.HelperMethods;

import wsi.ra.tool.BasicPropertyHolder;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;


/**
 * Model for the protonation/deprotonation of molecules.
 * The definition file can be defined in the
 * <tt>joelib2.data.JOEPhModel.resourceFile</tt> property in the {@link wsi.ra.tool.BasicPropertyHolder}.
 * The {@link wsi.ra.tool.BasicResourceLoader} loads the <tt>joelib2.properties</tt> file for default.
 *
 * <p>
 * Default:<br>
 * joelib2.data.JOEPhModel.resourceFile=<a href="http://cvs.sourceforge.net/cgi-bin/viewcvs.cgi/joelib/joelib/src/joelib2/data/plain/phmodel.txt?rev=HEAD&content-type=text/vnd.viewcvs-markup">joelib2/data/plain/phmodel.txt</a>
 *
 * @.author     wegnerj
 * @.wikipedia  Protonation
 * @.wikipedia  Deprotonation
 * @.wikipedia  PH
 * @.wikipedia Molecule
 * @.license GPL
 * @.cvsversion    $Revision: 1.7 $, $Date: 2005/02/17 16:48:29 $
 * @see wsi.ra.tool.BasicPropertyHolder
 * @see wsi.ra.tool.BasicResourceLoader
 * @see joelib2.data.BasicTransformationRulesHolder
 */
public class BasicProtonationModel extends AbstractDataHolder
    implements IdentifierHardDependencies, ProtonationModel
{
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * Obtain a suitable logger.
     */
    private static Logger logger = LogManager.getLogger(
            BasicProtonationModel.class.getName());
    private static BasicProtonationModel phmodel;
    private static final String DEFAULT_RESOURCE =
        "joelib2/data/plain/phmodel.txt";
    private static final String VENDOR = "http://joelib.sf.net";
    private static final String RELEASE_VERSION = "$Revision: 1.7 $";
    private static final String RELEASE_DATE = "$Date: 2005/02/17 16:48:29 $";
    private static final Class[] DEPENDENCIES =
        new Class[]
        {
            BasicImplicitValenceTyper.class, BasicSMARTSPatternMatcher.class
        };

    //~ Instance fields ////////////////////////////////////////////////////////

    /**
     * Seed charges for the Gasteiger-Marsili partial charges.
     */
    private List<BasicSMARTSPatternDoubles> seedChargeGM;

    /**
     * SMARTS based transformation patterns.
     */
    private List<BasicTransformationRulesHolder> transformation;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *  Constructor for the JOEPhModel object
     */
    private BasicProtonationModel()
    {
        initialized = false;

        Properties prop = BasicPropertyHolder.instance().getProperties();
        resourceFile = prop.getProperty(this.getClass().getName() +
                ".resourceFile", DEFAULT_RESOURCE);

        transformation = new Vector<BasicTransformationRulesHolder>();
        seedChargeGM = new Vector<BasicSMARTSPatternDoubles>();

        IdentifierExpertSystem.instance().addHardCodedKernel(this);
        init();
        IdentifierExpertSystem.instance().addSoftCodedKernel(this);

        logger.info("Using pH value correction model: " + resourceFile);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static Class[] getDependencies()
    {
        return DEPENDENCIES;
    }

    public static String getReleaseDate()
    {
        return VENDOR;
    }

    public static String getReleaseVersion()
    {
        return IdentifierExpertSystem.transformCVStag(RELEASE_VERSION);
    }

    public static String getVendor()
    {
        return IdentifierExpertSystem.transformCVStag(RELEASE_DATE);
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public static synchronized BasicProtonationModel instance()
    {
        if (phmodel == null)
        {
            phmodel = new BasicProtonationModel();
        }

        return phmodel;
    }

    /**
     *  Description of the Method
     *
     * @param  mol  Description of the Parameter
     */
    public void assignSeedPartialCharge(Molecule mol, double[] pCharges)
    {
        if (!initialized)
        {
            init();
        }

        if (!mol.isAssignPartialCharge())
        {
            return;
        }

        BasicSMARTSPatternDoubles sfvec;
        List<int[]> matchList;

        for (int i = 0; i < seedChargeGM.size(); i++)
        {
            sfvec = seedChargeGM.get(i);

            if (sfvec.smartsValue.match(mol))
            {
                matchList = sfvec.smartsValue.getMatchesUnique();

                //System.out.println(sfvec.smartsValue.getSmarts()+" "+matchList.size());
                int k;
                int[] iarr;

                for (int j = 0; j < matchList.size(); j++)
                {
                    iarr = matchList.get(j);

                    for (k = 0; k < iarr.length; k++)
                    {
                        //System.out.println(i+" "+j+" "+" "+k+" "+iarr[k]);
                        pCharges[mol.getAtom(iarr[k]).getIndex() - 1] =
                            sfvec.doubles[k];
                        //System.out.println("mol"+mol.getTitle()+" "+(mol.getAtom(iarr[k]).getIndex() - 1)+" "+sfvec.doubles[k]);
                    }
                }
            }
        }
    }

    /**
     * Corrects the molecule for PH.
     * Changes the state of oxygen and nitrogen atoms, if it
     * is allowed to change the formal charges of the atoms, that means
     * if <tt>Molecule.automaticFormalCharge()</tt> returns <tt>true</tt>
     *
     * @param  mol  Description of the Parameter
     */
    public void correctForPH(Molecule mol)
    {
        if (!initialized)
        {
            init();
        }

        if (mol.isCorrectedForPH())
        {
            return;
        }

        if (!mol.isAssignFormalCharge())
        {
            return;
        }

        mol.setCorrectedForPH();

        //mol.beginModify();
        // set only the atoms to 0 which where considered by
        // the PH value correction, leave all other unchanged
        // Set O,N to zero
        //    Atom       atom;
        //    AtomIterator  ait    = mol.atomIterator();
        //    while (ait.hasNext())
        //    {
        //      atom = ait.nextAtom();
        //      atom.setFormalCharge(0);
        //    }
        BasicTransformationRulesHolder ctsfm;

        for (int i = 0; i < transformation.size(); i++)
        {
            ctsfm = transformation.get(i);
            ctsfm.apply(mol);
        }

        BasicImplicitValenceTyper.instance().correctAromaticNitrogens(mol);

        /*AtomIterator ait = mol.atomIterator();
        Atom atom;
        System.out.println("PH: atoms=" + mol.numAtoms());
        while (ait.hasNext())
        {
                atom = ait.nextAtom();
                System.out.println("idx: " + atom.getIdx());
        }*/
        //mol.endModify();
    }

    /**
     * Release date for this expert system (hard coded).
     *
     * @return Release date for this expert system (hard coded).
     */
    public String getReleaseDateInternal()
    {
        return BasicProtonationModel.getReleaseDate();
    }

    /**
     * Release version for this expert system (hard coded).
     *
     * @return Release version for this expert system (hard coded).
     */
    public String getReleaseVersionInternal()
    {
        return BasicProtonationModel.getReleaseVersion();
    }

    /**
     * Vendor for this expert system (hard coded).
     *
     * @return Vendor for this expert system (hard coded).
     */
    public String getVendorInternal()
    {
        return BasicProtonationModel.getVendor();
    }

    /**
     *  Description of the Method
     *
     * @param  buffer  Description of the Parameter
     */
    protected void parseLine(String buffer)
    {
        Vector vs = new Vector();

        // of type String
        SMARTSPatternMatcher sp;

        if (buffer.trim().equals("") || (buffer.charAt(0) == '#'))
        {
            return;
        }

        if (HelperMethods.EQn(buffer, "TRANSFORM", 7))
        {
            HelperMethods.tokenize(vs, buffer);

            if ((vs.size() == 0) || (vs.size() < 4))
            {
                return;
            }

            BasicTransformationRulesHolder tsfm =
                new BasicTransformationRulesHolder();

            //      System.out.println("vs1: "+(String) vs.get(1));
            //      System.out.println("vs2: "+(String) vs.get(2));
            //      System.out.println("vs3: "+(String) vs.get(3));
            if (!tsfm.init((String) vs.get(1), (String) vs.get(3)))
            {
                tsfm = null;

                return;
            }

            transformation.add(tsfm);
        }
        else if (HelperMethods.EQn(buffer, "SEEDCHARGE", 10))
        {
            HelperMethods.tokenize(vs, buffer);

            if ((vs.size() == 0) || (vs.size() < 2))
            {
                return;
            }

            sp = new BasicSMARTSPatternMatcher();

            if (!sp.init((String) vs.get(1)) ||
                    ((vs.size() - 2) != sp.getQueryAtomsSize()))
            {
                sp = null;

                return;
            }

            double[] seedCharge = new double[vs.size() - 2];
            int index = 0;

            for (int i = 2; i < vs.size(); i++, index++)
            {
                seedCharge[index] = Double.parseDouble((String) vs.get(i));
            }

            seedChargeGM.add(new BasicSMARTSPatternDoubles(sp, seedCharge));
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
