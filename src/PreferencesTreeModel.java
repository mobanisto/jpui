/*
 * PreferencesTreeModel
 *
 * $RCSfile: PreferencesTreeModel.java,v $
 * $Revision: 1.2 $
 * $Date: 2004/01/04 18:51:04 $
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

import java.util.Collections;
import java.util.Stack;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Class that exposes java preferences as a TreeModel.
 * RootPreferencesNode is a root node to the TreeModel which
 * turns the User preference root and System preference root
 * into its child nodes.  The root nodes two children and all of their descendants
 * are of type PreferencesNode.
 */
public class PreferencesTreeModel implements TreeModel {
    // vector of listeners to send tree change events to
    private Vector moListeners = new Vector();
    // root of the preference tree
    private RootPreferencesNode moRoot = new RootPreferencesNode();

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
        Preferences oPref = (Preferences) oParent;
        Preferences oChild = null;
        try {
            String[] sChildren = oPref.childrenNames();
            oChild = oPref.node(sChildren[nIndex]);
        }
        catch (BackingStoreException oEx) {
            // TODO: BackingStoreException
        }

        return oChild;
    }

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount(Object oParent) {
        Preferences oPref = (Preferences) oParent;
        int nCount = 0;
        try {
            nCount = oPref.childrenNames().length;
        }
        catch (BackingStoreException oEx) {
            // TODO: BackingStoreException
        }

        return nCount;
    }

    /**
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf(Object oNode) {
        return false;
    }

    /**
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath oPath, Object oNewValue) {
        Preferences oOldValue = (Preferences) oPath.getLastPathComponent();

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
        int nIndex = -1;
        Preferences oPref = (Preferences) oParent;
        Preferences oPrefChild = (Preferences) oChild;
        try {
            String[] sChildren = oPref.childrenNames();
            for (int i = 0; i < sChildren.length; i++) {
                if (sChildren[i].equals(oPrefChild.name())) {
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
    
    /**
     * @param sNewNode
     */
    public void newNode(String sNewNode) {
        // create the node
        Preferences oNewNode = 
            PreferencesModel.Instance().newNode(sNewNode);

        // send notifications
        for (int i = 0; i < moListeners.size(); i++) {
            TreeModelEvent oEvent =
                new TreeModelEvent(this, toTreePath(oNewNode.parent()));
            ((TreeModelListener) moListeners.elementAt(i)).treeNodesInserted(
                oEvent);
        }
    }

    /**
     * 
     */
    public void deleteNode() {
        // delete the node
        Preferences oParent = PreferencesModel.Instance().deleteNode();
        
        // send notifications
        for (int i = 0; i < moListeners.size(); i++) {
            TreeModelEvent oEvent =
                new TreeModelEvent(this, toTreePath(oParent));
            ((TreeModelListener) moListeners.elementAt(i)).treeNodesRemoved(
                oEvent);
        }
    }
    
    /**
     * @param oPref
     * @return javax.swing.tree.TreePath
     */
    public TreePath toTreePath(Preferences oPref) {
        Stack oStack = new Stack();
        
        while(oPref != null) {
            oStack.push(oPref);
            oPref = oPref.parent();
        }
        //  oStack now contains all the nodes up the the root, except the root
        oStack.push(moRoot);
        
        // reverse the order of the stack elements
        Collections.reverse(oStack);
        
        return new TreePath(oStack.toArray(new Preferences[0]));
    }
}
