/*
 * Resources
 *
 * $RCSfile: Resources.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:41:45 $
 * $Source: /cvsroot/jpui/jpui/src/Resources.java,v $
 *
 * JPUI - Java Preferences User Interface
 * Copyright (C) 2003
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Author: macksold@users.sourceforge.net
 */

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * exposes i18n resources for JPUI as a singleton
 */
public class Resources {
    // application resource bundle
    private static ResourceBundle moBundle;
    // singleton instance
    private static Resources moInstance = new Resources();

    /**
     * private ctor
     */
    private Resources() {
        moBundle = PropertyResourceBundle.getBundle("Resources");
    }

    /**
     * Retrieve the locale specific string for given key
     * @param sKey
     * @return
     */
    public static String getString(String sKey) {
        return moBundle.getString(sKey);
    }
}
