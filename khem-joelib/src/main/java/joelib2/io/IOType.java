///////////////////////////////////////////////////////////////////////////////
//Filename: $RCSfile: IOType.java,v $
//Purpose:  TODO description.
//Language: Java
//Compiler: JDK 1.4
//Authors:  Joerg Kurt Wegner
//Version:  $Revision: 1.9 $
//        $Date: 2005/02/17 16:48:34 $
//        $Author: wegner $
//
//Copyright OELIB:          OpenEye Scientific Software, Santa Fe,
//                       U.S.A., 1999,2000,2001
//Copyright JOELIB/JOELib2: Dept. Computer Architecture, University of
//                       Tuebingen, Germany, 2001,2002,2003,2004,2005
//Copyright JOELIB/JOELib2: ALTANA PHARMA AG, Konstanz, Germany,
//                       2003,2004,2005
//
//This program is free software; you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation version 2 of the License.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
///////////////////////////////////////////////////////////////////////////////
package joelib2.io;

/**
 * TODO description.
 *
 * @.author     wegnerj
 * @.wikipedia  Chemical file format
 * @.wikipedia  File format
 * @.license    GPL
 * @.cvsversion $Revision: 1.9 $, $Date: 2005/02/17 16:48:34 $
 */
public interface IOType
{
    //~ Methods ////////////////////////////////////////////////////////////////

    boolean equals(Object obj);

    /**
     *  Gets the name attribute of the IOType class
     *
     * @param  type  Description of the Parameter
     * @return       The name value
     */
    String getName();

    /**
     *  Gets the representation attribute of the IOType class
     *
     * @param  type  Description of the Parameter
     * @return       The representation value
     */
    String getRepresentation();

    /**
     *  Gets the type attribute of the IOType class
     *
     * @param  _value  Description of the Parameter
     * @return         The type value
     */
    int getTypeNumber();

    int hashCode();

    /**
     *  Sets the representation attribute of the IOType object
     *
     * @param  _representation  The new representation value
     */
    void setRepresentation(String representation);

    String toString();
}

///////////////////////////////////////////////////////////////////////////////
//END OF FILE.
///////////////////////////////////////////////////////////////////////////////
