///////////////////////////////////////////////////////////////////////////////
//  Filename: $RCSfile: LogP.java,v $
//  Purpose:  Calculates the Octanol/Water partition coefficient (logP) or hydrophobicity.
//  Language: Java
//  Compiler: JDK 1.4
//  Authors:  Joerg Kurt Wegner
//  Version:  $Revision: 1.10 $
//            $Date: 2005/02/24 16:58:58 $
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

import joelib2.algo.contribution.BasicGroupContributions;
import joelib2.algo.contribution.GroupContributionPredictor;

import joelib2.data.BasicGroupContributionHolder;
import joelib2.data.IdentifierExpertSystem;

import joelib2.feature.AbstractDouble;
import joelib2.feature.BasicFeatureInfo;
import joelib2.feature.FeatureHelper;

import joelib2.molecule.Molecule;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;


/**
 * Calculates the Octanol/Water partition coefficient (logP) or hydrophobicity.
 *
 * @.author     wegnerj
 * @.wikipedia Lipinski's Rule of Five
 * @.wikipedia ADME
 * @.wikipedia Drug
 * @.wikipedia QSAR
 * @.wikipedia Data mining
 * @.license GPL
 * @.cvsversion    $Revision: 1.10 $, $Date: 2005/02/24 16:58:58 $
 * @.cite wc99
 */
public class LogP extends AbstractDouble
{
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String VENDOR = "http://joelib.sf.net";
    private static final String RELEASE_VERSION = "$Revision: 1.10 $";
    private static final String RELEASE_DATE = "$Date: 2005/02/24 16:58:58 $";
    private static Logger logger = LogManager.getLogger(LogP.class.getName());
    private static final Class[] DEPENDENCIES =
        new Class[]
        {
            BasicGroupContributionHolder.class,
            GroupContributionPredictor.class
        };

    //~ Constructors ///////////////////////////////////////////////////////////

    public LogP()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Initialize " + this.getClass().getName());
        }

        descInfo = FeatureHelper.generateFeatureInfo(this.getClass(),
                BasicFeatureInfo.TYPE_NO_COORDINATES, null,
                "joelib2.feature.result.DoubleResult");
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public static Class[] getDependencies()
    {
        return DEPENDENCIES;
    }

    public static String getName()
    {
        return LogP.class.getName();
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
     * Gets the defaultAtoms attribute of the NumberOfC object
     *
     * @return   The defaultAtoms value
     */
    public double getDoubleValue(Molecule mol)
    {
        BasicGroupContributions contrib = null;
        contrib = BasicGroupContributionHolder.instance().getGroupContributions(
                "LogP");

        double logP;
        logP = GroupContributionPredictor.predict(contrib, mol);

        return logP;
    }

    public int hashedDependencyTreeVersion()
    {
        return IdentifierExpertSystem.getDependencyTreeHash(getName());
    }
}

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
