/*
 * TreeView
 *
 * $RCSfile: TreeView.java,v $
 * $Revision: 1.1 $
 * $Date: 2003/10/05 15:03:40 $
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
import java.util.prefs.Preferences;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * Left side tree view of user and system
 * preference trees
 */
public class TreeView implements Observer {
	private PrefModel moPrefModel;
	private JPanel moPanel;
	private JTree moTree;
    
	/**
	 * @param oModel
	 */
	public TreeView(PrefModel oModel) {
		moPrefModel = JPUI.getPrefModel();
		moPrefModel.addObserver(this);
		
		moPanel = new JPanel();
		moPanel.setLayout(new BorderLayout());
		
		DefaultMutableTreeNode oRoot = new DefaultMutableTreeNode("Root");
		PrefTreeNode oUser = new PrefTreeNode(oModel.getUserRootNode(), "User");
		PrefTreeNode oSys = new PrefTreeNode(oModel.getSysRootNode(), "System");
								
		try {
			oUser = loadSubTree(oUser);
			oSys = loadSubTree(oSys);
		}
		catch(Exception oEx) {
			oEx.printStackTrace();
		}
		oRoot.add(oUser);
		oRoot.add(oSys);
		moTree = new JTree(oRoot, true);
		moTree.setRootVisible(false);
		moTree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//		Listen for when the selection changes.
	  	moTree.addTreeSelectionListener(new TreeSelectionListener() {
		  public void valueChanged(TreeSelectionEvent e) {
			  DefaultMutableTreeNode node = (DefaultMutableTreeNode)
								 moTree.getLastSelectedPathComponent();
        
			  if (node == null) return;

			  Object nodeInfo = node.getUserObject();
			  Preferences oPref = (Preferences)nodeInfo;
			  moPrefModel.setCurrentNode(oPref);
		  }
	  });
				
		moPanel.add(moTree, BorderLayout.CENTER);
	}

	private PrefTreeNode loadSubTree(
		PrefTreeNode oNode)
	    throws Exception {

		Preferences oPref = ((Preferences)oNode.getUserObject());
		String[] sChildren = oPref.childrenNames();
		for(int i=0; i < sChildren.length; i++) {
			Preferences oSubPref = oPref.node(sChildren[i]);
			oNode.add(loadSubTree(
				new PrefTreeNode(oSubPref, oSubPref.name())));
		}
	
		return oNode;	
	}
	
	public JPanel getPanel() {
		return moPanel;
	}
    
	/**
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable oObserver, Object oObj) {
		
	}

}
