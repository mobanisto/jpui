/*
 * MainView
 *
 * $RCSfile: MainView.java,v $
 * $Revision: 1.1 $
 * $Date: 2003/10/05 15:03:40 $
 * $Source: /cvsroot/jpui/jpui/src/MainView.java,v $
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
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * view that is responsible for menu commands
 */
public class MainView implements Observer, ActionListener {
	private PrefModel moPrefModel;
    private JMenuBar moMenuBar;
    
	/**
	 * @param oModel
	 */
	public MainView(PrefModel oModel) {
		moPrefModel = JPUI.getPrefModel();
		moPrefModel.addObserver(this);
        
        initMenu();
	}

    protected void initMenu() {
        moMenuBar = new JMenuBar(); 
        JMenu oMenu = new JMenu("File");
        JMenuItem oMenuItem = new JMenuItem("Exit");
        oMenuItem.setActionCommand("exit");
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        
        moMenuBar.add(oMenu);
    }
    
	/**
     * The model has changed, update the view, this may
     * enable/disable menu items at some point
     * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {

	}

    /**
     * @return
     */
    public JMenuBar getMenuBar() {
        return moMenuBar;
    }

    /**
     * Process menu selection
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("exit")) {
            System.exit(0);
        }
    }
}
