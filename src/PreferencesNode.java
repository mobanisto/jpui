/*
 * PreferencesNode
 *
 * $RCSfile: PreferencesNode.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:41:45 $
 * $Source: /cvsroot/jpui/jpui/src/PreferencesNode.java,v $
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

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.tree.TreePath;

/**
 * Class to contain a preference node and implement the 
 * TreeModelNodeInterface which allows this class to be
 * a node of the PreferencesTreeModel.  The PreferencesTreeModel
 * is used to manage the left side tree view.
 */
public class PreferencesNode implements TreeModelNodeInterface {
    // preference node this class contains
    private Preferences moPref = null;

    /**
     * ctor
     * @param oPref
     */
    public PreferencesNode(Preferences oPref) {
        moPref = oPref;
    }

    /**
     * @see TreeModelNodeInterface#getChild(java.lang.Object, int)
     */
    public Object getChild(int nIndex) {
        PreferencesNode oChild = null;
        try {
            String[] sChildren = moPref.childrenNames();
            oChild = new PreferencesNode(moPref.node(sChildren[nIndex]));
        }
        catch (BackingStoreException oEx) {
            // TODO: BackingStoreException
        }

        return oChild;
    }

    /**
     * @see TreeModelNodeInterface#getChildCount(java.lang.Object)
     */
    public int getChildCount() {
        int nCount = 0;
        try {
            nCount = moPref.childrenNames().length;
        }
        catch (BackingStoreException oEx) {
            // TODO: BackingStoreException
        }

        return nCount;
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

    /**
     * @see TreeModelNodeInterface#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object oChild) {
        int nIndex = -1;
        PreferencesNode oPrefChild = (PreferencesNode) oChild;
        try {
            String[] sChildren = moPref.childrenNames();
            for (int i = 0; i < sChildren.length; i++) {
                if (sChildren[i].equals(oPrefChild.getPreferences().name())) {
                    nIndex = i;
                    break;
                }
            }
        }
        catch (BackingStoreException oEx) {
            // TODO: BackingStoreException
        }

        return nIndex;
    }

    /**
     * Accessor for the contained preferences node
     * @return
     */
    public Preferences getPreferences() {
        return moPref;
    }

    /**
     * Returns the node name of this preferences node
     */
    public String toString() {
        String sName;
        if (moPref.parent() == null) {
            if (moPref.isUserNode() == true) {
                sName = "User";
            }
            else {
                sName = "System";
            }
        }
        else {
            sName = moPref.name();
        }
        return sName;
    }
}
