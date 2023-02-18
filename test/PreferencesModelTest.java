/*
 * PreferencesModelTest
 *
 * $RCSfile: PreferencesModelTest.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:42:19 $
 * $Source: /cvsroot/jpui/jpui/test/PreferencesModelTest.java,v $
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

import junit.framework.TestCase;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Test class for PreferencesModel
 */
public class PreferencesModelTest extends TestCase {
    private static final String TESTNODE = "PreferencesModelTestNode";

    /**
     * Constructor for PreferencesModelTest
     *
     * @param oArg
     */
    public PreferencesModelTest(String oArg) {
        super(oArg);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetCurrentNode() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);

        // current node should be user root
        assertEquals(
                oUser,
                PreferencesModel.Instance().getCurrentNode());
    }

    public void testSetCurrentNode() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);

        // current node should be user root
        assertEquals(
                oUser,
                PreferencesModel.Instance().getCurrentNode());
    }

    public void testSetAttribute() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);
        PreferencesModel.Instance().newNode(TESTNODE);

        Preferences oFooNode = PreferencesModel.Instance().getCurrentNode();
        // set the attribute
        PreferencesModel.Instance().setAttribute("foo", "bar");
        // foo should have a value of bar
        assertEquals(
                oFooNode.get("foo", null),
                "bar");

        // cleanup            
        try {
            oFooNode.remove("foo");
            oFooNode.removeNode();
            oUser.sync();
        } catch (BackingStoreException oEx) {
            fail(oEx.getMessage());
        }
    }

    public void testRemoveAttribute() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);
        PreferencesModel.Instance().newNode(TESTNODE);

        Preferences oFooNode = PreferencesModel.Instance().getCurrentNode();

        // set the attribute
        PreferencesModel.Instance().setAttribute("foo", "bar");
        assertEquals(
                oFooNode.get("foo", null),
                "bar");
        // remove the attribute
        PreferencesModel.Instance().removeAttribute("foo");
        // foo should be gone
        assertNull(
                oFooNode.get("foo", null));

        // cleanup
        try {
            oFooNode.removeNode();
            oUser.sync();
        } catch (BackingStoreException oEx) {
            fail(oEx.getMessage());
        }
    }

    public void testRenameAttribute() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);
        PreferencesModel.Instance().newNode(TESTNODE);

        Preferences oFooNode = PreferencesModel.Instance().getCurrentNode();

        // set the attribute
        PreferencesModel.Instance().setAttribute("foo", "bar");
        assertEquals(
                oFooNode.get("foo", null),
                "bar");

        // rename the attribute
        PreferencesModel.Instance().renameAttribute("foo", "baz");

        // foo should be gone
        assertNull(
                oFooNode.get("foo", null));

        // baz should now have the value foo used to have
        assertEquals(
                oFooNode.get("baz", null),
                "bar");

        // cleanup
        try {
            oFooNode.removeNode();
            oUser.sync();
        } catch (BackingStoreException oEx) {
            fail(oEx.getMessage());
        }
    }

    public void testNewNode() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);
        PreferencesModel.Instance().newNode(TESTNODE);

        Preferences oFooNode = PreferencesModel.Instance().getCurrentNode();

        // new node should have the name TESTNODE
        assertEquals(
                oFooNode.name(),
                TESTNODE);

        // cleanup            
        try {
            oFooNode.remove("foo");
            oFooNode.removeNode();
            oUser.sync();
        } catch (BackingStoreException oEx) {
            fail(oEx.getMessage());
        }
    }

    public void testDeleteNode() {
        Preferences oUser = Preferences.userRoot();
        PreferencesModel.Instance().setCurrentNode(oUser);
        PreferencesModel.Instance().newNode(TESTNODE);

        Preferences oFooNode = PreferencesModel.Instance().getCurrentNode();

        // new node should have the name TESTNODE
        assertEquals(
                oFooNode.name(),
                TESTNODE);

        PreferencesModel.Instance().deleteNode();
        try {
            oFooNode.put("foo", "bar");
            fail("Should throw IllegalStateException: node removed");
        } catch (IllegalStateException oEx) {
        }

        // cleanup            
        try {
            oUser.sync();
        } catch (BackingStoreException oEx) {
            fail(oEx.getMessage());
        }
    }

}
