///////////////////////////////////////////////////////////////////////////////
//Filename: $RCSfile: XMLHandler.java,v $
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

import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;


/**
 * Basic XML handler for the Chemical Markup Language (CML).
 *
 * @.author egonw
 * @.wikipedia  Chemical Markup Language
 * @.license LGPL
 * @.cvsversion    $Revision: 1.7 $, $Date: 2005/02/17 16:48:35 $
 */
public class XMLHandler extends HandlerBase
{
    //~ Instance fields ////////////////////////////////////////////////////////

    private CMLCoreModule conv;

    //~ Constructors ///////////////////////////////////////////////////////////

    public XMLHandler(CDOInterface cdo)
    {
        conv = new CMLCoreModule(cdo);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void characters(char[] ch, int start, int length)
    {
    }

    public void doctypeDecl(String name, String publicId, String systemId)
        throws Exception
    {
    }

    public void endDocument()
    {
        conv.endDocument();
    }

    public void endElement(String name)
    {
    }

    public CDOInterface returnCDO()
    {
        return conv.returnCDO();
    }

    public void startDocument()
    {
        conv.startDocument();
    }

    public void startElement(String name, AttributeList atts)
    {
    }
}

///////////////////////////////////////////////////////////////////////////////
//  END OF FILE.
///////////////////////////////////////////////////////////////////////////////
