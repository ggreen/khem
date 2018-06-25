///////////////////////////////////////////////////////////////////////////////
//Filename: $RCSfile: CMLReactionModule.java,v $
//Purpose:  Chemical Markup Language.
//Language: Java
//Compiler: Java (TM) 2 Platform Standard Edition 5.0
//Authors:  steinbeck@ice.mpg.de, gezelter@maul.chem.nd.edu,
//                      egonw@sci.kun.nl, wegner@users.sourceforge.net
//Version:  $Revision: 1.7 $
//                      $Date: 2005/02/17 16:48:35 $
//                      $Author: wegner $
//
//Copyright (C) 1997-2003  The Chemistry Development Kit (CDK) project
// Copyright OELIB:          OpenEye Scientific Software, Santa Fe,
//                           U.S.A., 1999,2000,2001
// Copyright JOELIB/JOELib2: Dept. Computer Architecture, University of
//                           Tuebingen, Germany, 2001,2002,2003,2004,2005
// Copyright JOELIB/JOELib2: ALTANA PHARMA AG, Konstanz, Germany,
//                           2003,2004,2005
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public License
//as published by the Free Software Foundation; either version 2.1
//of the License, or (at your option) any later version.
//All we ask is that proper credit is given for our work, which includes
//- but is not limited to - adding the above copyright notice to the beginning
//of your source code files, and to any copyright notice that you may distribute
//with programs based on this work.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////
package joelib2.io.types.cml;

import org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;

import org.xml.sax.Attributes;


/**
 * Chemical Markup Language (CML) reaction module.
 * @.author egonw
 * @.wikipedia  Chemical Markup Language
 * @.license LGPL
 * @.cvsversion    $Revision: 1.7 $, $Date: 2005/02/17 16:48:35 $
 * @.cite rr99b
 * @.cite mr01
 * @.cite gmrw01
 * @.cite wil01
 * @.cite mr03
 * @.cite mrww04
 */
public class CMLReactionModule extends CMLCoreModule
{
    //~ Static fields/initializers /////////////////////////////////////////////

    // Obtain a suitable logger.
    private static Logger logger = LogManager.getLogger(
            "joelib2.io.types.cml.CMLReactionModule");

    //~ Instance fields ////////////////////////////////////////////////////////

    private CMLStack conventionStack = new CMLStack();
    private CMLStack xpath = new CMLStack();

    //~ Constructors ///////////////////////////////////////////////////////////

    public CMLReactionModule(CDOInterface cdo)
    {
        super(cdo);
    }

    public CMLReactionModule(ModuleInterface conv)
    {
        super(conv);

        if (logger.isDebugEnabled())
        {
            logger.debug("New CML-Reaction Module!");
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void characterData(char[] ch, int start, int length)
    {
        //String s = new String(ch, start, length).trim();
        super.characterData(xpath, ch, start, length);
    }

    public void endDocument()
    {
        super.endDocument();
    }

    public void endElement(String uri, String local, String raw)
    {
        if ("reaction".equals(local))
        {
            cdo.endObject("Reaction");
        }
        else if ("reactionList".equals(local))
        {
            cdo.endObject("SetOfReactions");
        }
        else if ("reactant".equals(local))
        {
            cdo.endObject("Reactant");
        }
        else if ("product".equals(local))
        {
            cdo.endObject("Product");
        }
        else if ("molecule".equals(local))
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Storing Molecule");
            }

            super.storeData();

            // do nothing else but store atom/bond information
        }
        else
        {
            super.endElement(xpath, uri, local, raw);
        }
    }

    public CDOInterface returnCDO()
    {
        return this.cdo;
    }

    public void startDocument()
    {
        super.startDocument();
    }

    public void startElement(String uri, String local, String raw,
        Attributes atts)
    {
        if ("reaction".equals(local))
        {
            cdo.startObject("Reaction");
        }
        else if ("reactionList".equals(local))
        {
            cdo.startObject("SetOfReactions");
        }
        else if ("reactant".equals(local))
        {
            cdo.startObject("Reactant");
        }
        else if ("product".equals(local))
        {
            cdo.startObject("Product");
        }
        else if ("molecule".equals(local))
        {
            // do nothing for now
            super.newMolecule();
        }
        else
        {
            super.startElement(xpath, uri, local, raw, atts);
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
