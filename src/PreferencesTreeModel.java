/*
 * PreferencesTreeModel
 *
 * $RCSfile: PreferencesTreeModel.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:41:45 $
 * $Source: /cvsroot/jpui/jpui/src/PreferencesTreeModel.java,v $
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

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Class that exposes java preferences as a TreeModel.
 * The key to this class is the two implementations of 
 * TreeModelNodeInterface.  The first implementation is
 * RootPreferencesNode and it makes up for the fact that
 * java preferences actually have two roots, User and System.
 * RootPreferencesNode is a root node to the TreeModel which
 * turns the User preference root and System preference root
 * into its child nodes.
 * The root nodes two children and all of their descendants
 * are of type PreferencesNode, which is the other
 * implementation of TreeModelNodeInterface.
 */
public class PreferencesTreeModel implements TreeModel {
    // vector of listeners to send tree change events to
    private Vector moListeners = new Vector();
    // root of the preference tree
    private TreeModelNodeInterface moRoot = new RootPreferencesNode();

    /**
     * @see javax.swing.tree.TreeModel#getRoot()
     */
    public Object getRoot() {
        return moRoot;
    }

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(Object oParent, int nIndex) {
        TreeModelNodeInterface oParentImpl = (TreeModelNodeInterface) oParent;

        return oParentImpl.getChild(nIndex);
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object oParent) {
        TreeModelNodeInterface oParentImpl = (TreeModelNodeInterface) oParent;

        return oParentImpl.getChildCount();
    }

    /**
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object oNode) {
        TreeModelNodeInterface oNodeImpl = (TreeModelNodeInterface) oNode;

        return oNodeImpl.isLeaf();
    }

    /**
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath oPath, Object oNewValue) {
        TreeModelNodeInterface oOldValueImpl =
            (TreeModelNodeInterface) oPath.getLastPathComponent();

        oOldValueImpl.valueForPathChanged(oPath, oNewValue);

        for (int i = 0; i < moListeners.size(); i++) {
            TreeModelEvent oEvent =
                new TreeModelEvent(this, new Object[] { moRoot });

            ((TreeModelListener) moListeners.elementAt(i)).treeNodesChanged(
                oEvent);
        }
    }

    /**
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object oParent, Object oChild) {
        if (oParent == null || oChild == null) {
            return -1;
        }

        TreeModelNodeInterface oParentImpl = (TreeModelNodeInterface) oParent;

        return oParentImpl.getIndexOfChild(oChild);
    }

    /**
     * @see javax.swing.tree.TreeModel#addTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void addTreeModelListener(TreeModelListener oListener) {
        moListeners.add(oListener);
    }

    /**
     * @see javax.swing.tree.TreeModel#removeTreeModelListener(javax.swing.event.TreeModelListener)
     */
    public void removeTreeModelListener(TreeModelListener oListener) {
        moListeners.remove(oListener);
    }
}
