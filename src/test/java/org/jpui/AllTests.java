/*
 * AllTests
 *
 * $RCSfile: AllTests.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:42:19 $
 * $Source: /cvsroot/jpui/jpui/test/AllTests.java,v $
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

package org.jpui;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AllTests for JPUI
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for default package");

        suite.addTest(new TestSuite(PreferencesModelTest.class));
        return suite;
    }
}
