/*
 * JPUI
 *
 * $RCSfile: JPUI.java,v $
 * $Revision: 1.1 $
 * $Date: 2003/10/05 15:03:40 $
 * $Source: /cvsroot/jpui/jpui/src/JPUI.java,v $
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
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 * The one and only main program class
 * 
 * Builds the model, the views, and lays out the GUI
 */
public class JPUI {
	// singleton the app
	private static JPUI moPrefGUI = null;
	
	// data model
	private static PrefModel moPrefModel;
	
	// views
	private MainView moMainView;
	private TreeView moTreeView;
	private EditNodeView moEditNodeView;
	
	// gui parts
	private static JFrame moFrame = null;
	private static JPanel moContentPanel = null;
	
	public JPUI() {
		// the app
		moPrefGUI = this;
		initialize();		
	}
	
	/**
     * Construct and put together the model, views,
     * and UI components
     */
    private void initialize() {
		// the data
		moPrefModel = new PrefModel();
		
		// the views
		moMainView = new MainView(moPrefModel);
		moTreeView = new TreeView(moPrefModel);
		moEditNodeView = new EditNodeView(moPrefModel);
		
		//
		// the gui
		//
		
		// main frame
		moFrame = new JFrame("JPUI");
		moFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		moFrame.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent oEv) {
					System.exit(0);
				}
			});
			        
        // menu
        moFrame.setJMenuBar(moMainView.getMenuBar());
        
		// content panel and tool bar
		moContentPanel = new JPanel();
		moContentPanel.setLayout(new BorderLayout());
		moContentPanel.setPreferredSize(
			new Dimension(600, 500));
			
		moFrame.setContentPane(moContentPanel);
		
		// the main view
		JScrollPane oTreePane = new JScrollPane(
			moTreeView.getPanel());
		JScrollPane oEditNodePane = new JScrollPane(
			moEditNodeView.getPanel());
		JSplitPane oSplitPane = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			oTreePane, oEditNodePane);
        oSplitPane.setDividerLocation(200);
		moContentPanel.add(oSplitPane, BorderLayout.CENTER);
	}
	
	/**
	 * @return
	 */
	public static JPanel getContentPanel() {
		return moContentPanel;
	}

	/**
	 * @return
	 */
	public static JFrame getFrame() {
		return moFrame;
	}

	/**
	 * @return
	 */
	public static JPUI getPrefGUI() {
		return moPrefGUI;
	}

	/**
	 * @return
	 */
	public static PrefModel getPrefModel() {
		return moPrefModel;
	}

	/**
     * @param args
     */
    public static void main(String[] args) {
		final JPUI oPrefGUI = new JPUI();
		JPUI.getFrame().pack();
		JPUI.getFrame().setVisible(true);
	}
}
