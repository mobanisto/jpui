/*
 * TreeView
 *
 * $RCSfile: TreeView.java,v $
 * $Revision: 1.4 $
 * $Date: 2004/01/10 20:10:46 $
 * $Source: /cvsroot/jpui/jpui/src/TreeView.java,v $
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

import org.jpui.observable.Observable;
import org.jpui.observable.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;
import java.util.prefs.Preferences;

/**
 * Left side tree view of user and system
 * preference trees
 */
public class TreeView implements Observer, TreeModelListener {
    // panel
    private JPanel moPanel;
    // tree control
    private JTree moTree;
    // tree model
    private PreferencesTreeModel moPreferencesTreeModel;

    public TreeView() {
        moPanel = new JPanel();
        moPanel.setLayout(new BorderLayout());
        moPreferencesTreeModel = new PreferencesTreeModel();
        moTree = new JTree(moPreferencesTreeModel);
        moPreferencesTreeModel.addTreeModelListener(this);

        moTree.setRootVisible(false);
        moTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        moTree.setEditable(true);
        moTree.setShowsRootHandles(true);

        //		Listen for when the selection changes.
        moTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                Preferences oNode =
                        (Preferences) moTree
                                .getLastSelectedPathComponent();

                if (oNode == null)
                    return;

                // update the current node
                PreferencesModel.Instance().setCurrentNode(oNode);
            }
        });

        moPanel.add(moTree, BorderLayout.CENTER);
        PreferencesModel.Instance().addObserver(this);
    }

    /**
     * @return
     */
    public JPanel getPanel() {
        return moPanel;
    }

    @Override
    public void update(Observable oObject) {
        syncTree();
    }

    /**
     * Create a node as a child of the current node
     */
    public void newNode() {
        String sNewNode =
                JOptionPane.showInputDialog(
                        moPanel.getParent(),
                        Resources.getString("new_node_message"),
                        Resources.getString("new_node_title"),
                        JOptionPane.QUESTION_MESSAGE);
        if (sNewNode != null) {
            PreferencesTreeModel oPrefTreeModel =
                    (PreferencesTreeModel) moTree.getModel();
            oPrefTreeModel.newNode(sNewNode);
        }
    }

    /**
     * Delete the current node
     */
    public void deleteNode() {
        PreferencesTreeModel oPrefTreeModel =
                (PreferencesTreeModel) moTree.getModel();
        oPrefTreeModel.deleteNode();
    }

    /**
     * @see javax.swing.event.TreeModelListener#treeNodesChanged(javax.swing.event.TreeModelEvent)
     */
    public void treeNodesChanged(TreeModelEvent e) {
        syncTree();
    }

    /**
     * @see javax.swing.event.TreeModelListener#treeNodesInserted(javax.swing.event.TreeModelEvent)
     */
    public void treeNodesInserted(TreeModelEvent e) {
        syncTree();
    }

    /**
     * @see javax.swing.event.TreeModelListener#treeNodesRemoved(javax.swing.event.TreeModelEvent)
     */
    public void treeNodesRemoved(TreeModelEvent e) {
        syncTree();
    }

    /**
     * @see javax.swing.event.TreeModelListener#treeStructureChanged(javax.swing.event.TreeModelEvent)
     */
    public void treeStructureChanged(TreeModelEvent e) {
        syncTree();
    }

    /**
     * Ensure that the JTree reflects the current node after the tree
     * changes by having a node added or removed.
     */
    private void syncTree() {
        PreferencesModel oModel = PreferencesModel.Instance();
        Preferences oCurrentPref = oModel.getCurrentNode();
//        Preferences oCurrentTreeSelection =
//            (Preferences)moTree.getSelectionPath().getLastPathComponent();

        // update the tree selection
//        if(!oCurrentPref.equals(oCurrentTreeSelection)) {
        PreferencesTreeModel oPrefTreeModel =
                (PreferencesTreeModel) moTree.getModel();

        moTree.setSelectionPath(oPrefTreeModel.toTreePath(oCurrentPref));
//        }
    }
}
