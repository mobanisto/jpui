/*
 * RootPreferenceNode
 *
 * $RCSfile: RootPreferencesNode.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:41:45 $
 * $Source: /cvsroot/jpui/jpui/src/RootPreferencesNode.java,v $
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

import java.util.prefs.Preferences;

import javax.swing.tree.TreePath;

/**
 * Class the helps implement TreeModel for java preferences by
 * creating a virtual root node with the User preference tree
 * and System preference tree is its only two children.
 */
public class RootPreferencesNode implements TreeModelNodeInterface {
    // contants
    private static final int USER = 0;
    private static final int SYSTEM = 1;

    /**
     * @see TreeModelNodeInterface#getChild(java.lang.Object, int)
     */
    public Object getChild(int nIndex) {
        PreferencesNode oChild = null;
        if (nIndex == USER) {
            oChild = new PreferencesNode(Preferences.userRoot());
        }
        else if (nIndex == SYSTEM) {
            oChild = new PreferencesNode(Preferences.systemRoot());
        }
        else {
            // TODO: Invalid child node
        }

        return oChild;
    }

    /**
     * @see TreeModelNodeInterface#getChildCount(java.lang.Object)
     */
    public int getChildCount() {
        // system and user
        return 2;
    }

    /**
     * @see TreeModelNodeInterface#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object oChild) {
        int nIndex;
        PreferencesNode oPref = (PreferencesNode) oChild;
        if (oPref.getPreferences().isUserNode() == true) {
            nIndex = USER;
        }
        else {
            nIndex = SYSTEM;
        }
        return nIndex;
    }

    /**
     * @see TreeModelNodeInterface#isLeaf(java.lang.Object)
     */
    public boolean isLeaf() {
        return false;
    }

    /**
     * @see TreeModelNodeInterface#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath oPath, Object oNewValue) {
        // TODO: valueForPathChanged

    }

}
