///////////////////////////////////////////////////////////////////////////////
//  Filename: $RCSfile: GeomDistanceMatrix.java,v $
//  Purpose:  Geometrical distance matrix.
//  Language: Java
//  Compiler: JDK 1.4
//  Authors:  Joerg Kurt Wegner
//  Version:  $Revision: 1.9 $
//            $Date: 2005/02/17 16:48:31 $
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
package joelib2.feature.types;

import joelib2.data.IdentifierExpertSystem;

import joelib2.feature.BasicFeatureDescription;
import joelib2.feature.BasicFeatureInfo;
import joelib2.feature.Feature;
import joelib2.feature.FeatureDescription;
import joelib2.feature.FeatureException;
import joelib2.feature.FeatureHelper;
import joelib2.feature.FeatureResult;
import joelib2.feature.ResultFactory;

import joelib2.feature.result.DoubleMatrixResult;

import joelib2.molecule.Molecule;

import joelib2.util.BasicProperty;

import java.util.Map;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;


/**
 * Geometrical distance matrix.
 *
 * @.author     wegnerj
 * @.license GPL
 * @.cvsversion    $Revision: 1.9 $, $Date: 2005/02/17 16:48:31 $
 */
public class GeomDistanceMatrix implements Feature
{
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String VENDOR = "http://joelib.sf.net";
    private static final String RELEASE_VERSION = "$Revision: 1.9 $";
    private static final String RELEASE_DATE = "$Date: 2005/02/17 16:48:31 $";
    private static Logger logger = LogManager.getLogger(
            GeomDistanceMatrix.class.getName());
    private static final Class[] DEPENDENCIES = new Class[]{};

    //~ Instance fields ////////////////////////////////////////////////////////

    private BasicFeatureInfo descInfo;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *  Constructor for the DistanceMatrix object
     */
    public GeomDistanceMatrix()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Initialize " + this.getClass().getName());
        }

        descInfo = FeatureHelper.generateFeatureInfo(this.getClass(),
                BasicFeatureInfo.TYPE_GEOMETRICAL, null,
                "joelib2.feature.result.DoubleMatrixResult");
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static Class[] getDependencies()
    {
        return DEPENDENCIES;
    }

    public static String getName()
    {
        return GeomDistanceMatrix.class.getName();
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

    public BasicProperty[] acceptedProperties()
    {
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  mol                      Description of the Parameter
     * @return                          Description of the Return Value
     * @exception  FeatureException  Description of the Exception
     */
    public FeatureResult calculate(Molecule mol) throws FeatureException
    {
        FeatureResult result = ResultFactory.instance().getFeatureResult(
                descInfo.getName());

        return calculate(mol, result, null);
    }

    /**
     *  Description of the Method
     *
     * @param  mol                      Description of the Parameter
     * @param  initData                 Description of the Parameter
     * @return                          Description of the Return Value
     * @exception  FeatureException  Description of the Exception
     */
    public FeatureResult calculate(Molecule mol, Map properties)
        throws FeatureException
    {
        FeatureResult result = ResultFactory.instance().getFeatureResult(
                descInfo.getName());

        return calculate(mol, result, properties);
    }

    /**
     *  Description of the Method
     *
     * @param  mol                      Description of the Parameter
     * @param  descResult               Description of the Parameter
     * @return                          Description of the Return Value
     * @exception  FeatureException  Description of the Exception
     */
    public FeatureResult calculate(Molecule mol, FeatureResult descResult)
        throws FeatureException
    {
        return calculate(mol, descResult, null);
    }

    /**
     *  Description of the Method
     *
     * @param  mol                      Description of the Parameter
     * @param  initData                 Description of the Parameter
     * @param  descResult               Description of the Parameter
     * @return                          Description of the Return Value
     * @exception  FeatureException  Description of the Exception
     */
    public FeatureResult calculate(Molecule mol, FeatureResult descResult,
        Map properties) throws FeatureException
    {
        if (mol.isEmpty())
        {
            logger.error("Empty molecule '" + mol.getTitle() + "'.");

            return null;
        }

        if (!(descResult instanceof DoubleMatrixResult))
        {
            logger.error(descInfo.getName() + " result should be of type " +
                DoubleMatrixResult.class.getName() + " but it's of type " +
                descResult.getClass().toString());
        }

        // check if the init type is correct
        if (!initialize(properties))
        {
            return null;
        }

        double dist;
        double[][] matrix = new double[mol.getAtomsSize()][mol.getAtomsSize()];

        for (int i = 1; i < mol.getAtomsSize(); i++)
        {
            for (int k = 0; k < i; k++)
            {
                dist = Math.sqrt(
                        Math.pow(
                            mol.getAtom(i + 1).get3Dx() -
                            mol.getAtom(k + 1).get3Dx(), 2.0) +
                        Math.pow(
                            mol.getAtom(i + 1).get3Dy() -
                            mol.getAtom(k + 1).get3Dy(), 2.0) +
                        Math.pow(
                            mol.getAtom(i + 1).get3Dz() -
                            mol.getAtom(k + 1).get3Dz(), 2.0));
                matrix[i][k] = dist;
                matrix[k][i] = dist;
            }
        }

        DoubleMatrixResult result = (DoubleMatrixResult) descResult;
        result.value = matrix;

        return result;
    }

    /**
     *  Description of the Method
     */
    public void clear()
    {
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public BasicFeatureInfo getDescInfo()
    {
        return descInfo;
    }

    /**
     *  Gets the description attribute of the Descriptor object
     *
     * @return    The description value
     */
    public FeatureDescription getDescription()
    {
        return new BasicFeatureDescription(descInfo.getDescriptionFile());
    }

    public int hashedDependencyTreeVersion()
    {
        return IdentifierExpertSystem.getDependencyTreeHash(getName());
    }

    /**
     *  Description of the Method
     *
     * @param  initData  Description of the Parameter
     */
    public boolean initialize(Map properties)
    {
        return true;
    }

    /**
     * Test the implementation of this descriptor.
     *
     * @return <tt>true</tt> if the implementation is correct
     */
    public boolean testDescriptor()
    {
        return true;
    }
}

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
