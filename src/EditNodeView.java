/*
 * EditNodeView
 *
 * $RCSfile: EditNodeView.java,v $
 * $Revision: 1.1 $
 * $Date: 2003/10/05 15:03:40 $
 * $Source: /cvsroot/jpui/jpui/src/EditNodeView.java,v $
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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * Right hand side of UI. Responsible for rendering
 * the current preference node.
 */
public class EditNodeView implements Observer {
	private JPanel moPanel;
	private JTable moTable;
	private JButton moCancel;
	private JButton moSave;
	private PrefModel moPrefModel;
    
	/**
	 * @param oModel
	 */
	public EditNodeView(PrefModel oModel) {
		moPrefModel = JPUI.getPrefModel();
		moPrefModel.addObserver(this);
		
		moPanel = new JPanel();
		moPanel.setLayout(new BorderLayout());
		
		JButton moSave = new JButton("Save");
        moSave.setActionCommand("save");
		JButton moCancel = new JButton("Cancel");
        moCancel.setActionCommand("cancel");

        // TODO save and cancel node functionality
		ActionListener oButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("save")) {
				}
				else if(e.getActionCommand().equals("cancel")) {
				}
			}
		};

		moSave.addActionListener(oButtonListener);
		moCancel.addActionListener(oButtonListener);
		
		moSave.setEnabled(false);
		moCancel.setEnabled(false);
		
		JPanel oBottom = new JPanel();
		oBottom.setLayout(new FlowLayout());
        // TODO put these in when they do something
		// oBottom.add(moCancel);
		// oBottom.add(moSave);
		
		moPanel.add(oBottom, BorderLayout.SOUTH);
		renderTable();
	}

	public JPanel getPanel() {
		return moPanel;
	}
	
	/**
     * Uddate the table with the currently selected
     * preference node.
     * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable oObs, Object oArg) {
		moPanel.remove(moTable.getTableHeader());
		moPanel.remove(moTable);
		renderTable();
	}

	/**
     * Retrieves the current node from the model and
     * renders it via a JTable.
     */
    private void renderTable() {
		Preferences oPref = moPrefModel.getCurrentNode();
		moTable = new JTable(new PrefNodeTable(oPref));
		moTable.setShowGrid(true);
		moTable.setGridColor(Color.LIGHT_GRAY);
		moPanel.add(moTable.getTableHeader(), BorderLayout.NORTH);
		moPanel.add(moTable, BorderLayout.CENTER);		
		moPanel.validate();
	}
}
