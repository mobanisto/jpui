/*
 * TreeView
 *
 * $RCSfile: TreeView.java,v $
 * $Revision: 1.2 $
 * $Date: 2004/01/01 17:41:45 $
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

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

/**
 * Left side tree view of user and system
 * preference trees
 */
public class TreeView implements Observer {
    // panel
    private JPanel moPanel;
    // tree control
    private JTree moTree;

    /**
     * @param oModel
     */
    public TreeView() {
        moPanel = new JPanel();
        moPanel.setLayout(new BorderLayout());

        moTree = new JTree(new PreferencesTreeModel());
        moTree.setRootVisible(false);
        moTree.getSelectionModel().setSelectionMode(
            TreeSelectionModel.SINGLE_TREE_SELECTION);
        moTree.setEditable(true);

        //		Listen for when the selection changes.
        moTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreeModelNodeInterface oNode =
                    (TreeModelNodeInterface) moTree
                        .getLastSelectedPathComponent();

                if (oNode == null)
                    return;

                // update the current node
                if (oNode instanceof PreferencesNode) {
                    PreferencesModel.Instance().setCurrentNode(
                        ((PreferencesNode) oNode).getPreferences());
                }
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

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable oObject, Object oArg) {
        // TODO: update the tree view as nodes are added/deleted
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
            PreferencesModel.Instance().newNode(sNewNode);
        }
    }
    
    /**
     * Delete the current node
     */
    public void deleteNode() {
        PreferencesModel.Instance().deleteNode();
    }
}
