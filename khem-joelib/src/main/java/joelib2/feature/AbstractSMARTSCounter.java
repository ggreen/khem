///////////////////////////////////////////////////////////////////////////////
//  Filename: $RCSfile: AbstractSMARTSCounter.java,v $
//  Purpose:  Zagreb Group Index 1 - Calculator.
//  Language: Java
//  Compiler: JDK 1.4
//  Authors:  Joerg Kurt Wegner
//  Version:  $Revision: 1.9 $
//            $Date: 2005/02/17 16:48:29 $
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
package joelib2.feature;

import joelib2.data.IdentifierExpertSystem;

import joelib2.feature.result.IntResult;

import joelib2.molecule.Molecule;

import joelib2.smarts.BasicSMARTSPatternMatcher;
import joelib2.smarts.SMARTSPatternMatcher;

import joelib2.util.BasicProperty;
import joelib2.util.PropertyHelper;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;


/**
 * Representation for a simple SMARTS counter descriptor.
 *
 * @.author     wegnerj
 * @.license GPL
 * @.cvsversion    $Revision: 1.9 $, $Date: 2005/02/17 16:48:29 $
 * @see SMARTSPatternMatcher
 * @.cite smarts
 */
public abstract class AbstractSMARTSCounter implements Feature
{
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     *  Obtain a suitable logger.
     */
    private static Logger logger = LogManager.getLogger(
            AbstractSMARTSCounter.class.getName());
    public final static String SMARTS = "SMARTS";
    private final static BasicProperty[] ACCEPTED_PROPERTIES =
        new BasicProperty[]
        {
            new BasicProperty(SMARTS, "java.lang.String",
                "SMARTS pattern to count.", true),
        };

    //~ Instance fields ////////////////////////////////////////////////////////

    public BasicFeatureInfo descInfo;
    private SMARTSPatternMatcher smarts;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Initializes descriptor class for calculating the number of SMARTS patterns.
     */
    public AbstractSMARTSCounter()
    {
        smarts = new BasicSMARTSPatternMatcher();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Gets the default number of SMARTS pattern in the molecule.
     *
     * @param the molecule
     * @return the default number of SMARTS pattern in the molecule
     */
    public abstract String getDefaultSMARTS();

    public BasicProperty[] acceptedProperties()
    {
        return ACCEPTED_PROPERTIES;
    }

    /**
     * Calculate descriptor for this molecule.
     *
     * @param mol                      molecule for which this descriptor should be calculated
     * @return                         the descriptor calculation result for this molecule
     * @exception FeatureException  descriptor calculation exception
     */
    public FeatureResult calculate(Molecule mol) throws FeatureException
    {
        FeatureResult result = ResultFactory.instance().getFeatureResult(
                descInfo.getName());

        return calculate(mol, result, null);
    }

    /**
     * Calculate descriptor for this molecule.
     *
     * @param mol                      molecule for which this descriptor should be calculated
     * @param initData                 initialization properties
     * @return                         the descriptor calculation result for this molecule
     * @exception FeatureException  descriptor calculation exception
     *
     * @see #SMARTS
     */
    public FeatureResult calculate(Molecule mol, Map properties)
        throws FeatureException
    {
        FeatureResult result = ResultFactory.instance().getFeatureResult(
                descInfo.getName());

        return calculate(mol, result, properties);
    }

    /**
     * Calculate descriptor for this molecule.
     *
     * It should be faster, if we can can use an already initialized result class,
     * because this must not be get by Java reflection. Ensure that you will clone
     * this result class before you store these results in molecules, or the next molecule will
     * overwrite this result.
     *
     * @param mol                      molecule for which this descriptor should be calculated
     * @param descResult               the descriptor result class in which the result should be stored
     * @return                         the descriptor calculation result for this molecule
     * @exception FeatureException  descriptor calculation exception
     */
    public FeatureResult calculate(Molecule mol, FeatureResult descResult)
        throws FeatureException
    {
        return calculate(mol, descResult, null);
    }

    /**
     * Calculate descriptor for this molecule.
     *
     * It should be faster, if we can can use an already initialized result class,
     * because this must not be get by Java reflection. Ensure that you will clone
     * this result class before you store these results in molecules, or the next molecule will
     * overwrite this result.
     *
     * @param mol                      molecule for which this descriptor should be calculated
     * @param descResult               the descriptor result class in which the result should be stored
     * @param initData                 initialization properties
     * @return                         the descriptor calculation result for this molecule
     * @exception FeatureException  descriptor calculation exception
     *
     * @see #SMARTS
     */
    public FeatureResult calculate(Molecule mol, FeatureResult descResult,
        Map properties) throws FeatureException
    {
        if (!(descResult instanceof IntResult))
        {
            logger.error(descInfo.getName() + " result should be of type " +
                IntResult.class.getName() + " but it's of type " +
                descResult.getClass().toString());
        }

        if (mol.isEmpty())
        {
            IntResult result = (IntResult) descResult;
            result.setInt(0);
            logger.warn("Empty molecule '" + mol.getTitle() +
                "'. Set descriptor to 0.");

            return result;
        }

        // check if the init type is correct
        if (!initialize(properties))
        {
            return null;
        }

        // match the SMARTS pattern and return matching count
        smarts.match(mol);

        List matchList = smarts.getMatchesUnique();

        IntResult result = (IntResult) descResult;
        result.setInt(matchList.size());
        result.addCMLProperty(IdentifierExpertSystem.instance().getKernelID());

        return result;
    }

    /**
     * Clear descriptor calculation method for a new molecule.
     */
    public void clear()
    {
        if (!smarts.init(getDefaultSMARTS()))
        {
            logger.error("Invalid SMARTS pattern '" + getDefaultSMARTS() +
                "' defined in " + AbstractSMARTSCounter.class.getName());
        }
    }

    /**
            * Gets the descriptor informations for this descriptor.
            *
            * @return   the descriptor information
            */
    public BasicFeatureInfo getDescInfo()
    {
        return descInfo;
    }

    /**
     * Gets the descriptor description.
     *
     * @return   the descriptor description
     */
    public FeatureDescription getDescription()
    {
        return new BasicFeatureDescription(descInfo.getDescriptionFile());
    }

    /**
     * Initialize descriptor calculation method for all following molecules.
     *
     * @param initData  initialization properties
     * @return <tt>true</tt> if the initialization was successfull
     *
     * @see #SMARTS
     */
    public boolean initialize(Map properties)
    {
        if (!PropertyHelper.checkProperties(this, properties))
        {
            logger.error(
                "Empty property definition or missing property entry.");

            return false;
        }

        String smartsPattern = (String) PropertyHelper.getProperty(this, SMARTS,
                properties);

        if (smartsPattern == null)
        {
            if (!smarts.init(getDefaultSMARTS()))
            {
                logger.error("Invalid SMARTS pattern '" + getDefaultSMARTS() +
                    "' defined in " + AbstractSMARTSCounter.class.getName());

                return false;
            }
        }
        else
        {
            if (!smarts.init(smartsPattern))
            {
                logger.error("Invalid SMARTS pattern '" + smartsPattern +
                    "' defined in " + AbstractSMARTSCounter.class.getName());
            }
        }

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

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
