/*
 * MainView
 *
 * $RCSfile: MainView.java,v $
 * $Revision: 1.3 $
 * $Date: 2004/01/04 18:51:04 $
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

package org.jpui;

import org.jpui.about.AboutDialog;
import org.jpui.about.AboutPanel;
import org.jpui.observable.Observable;
import org.jpui.observable.Observer;
import org.jpui.preferences.Theme;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

/**
 * View that is responsible for menu commands.
 * Delegates menu command implementation to the relevant view.
 */
public class MainView implements Observer, ActionListener {
    // menu bar
    private JMenuBar moMenuBar;

    // menu action commands
    private static final String ABOUT_LICENSE = "about_license";
    private static final String EXIT = "exit";
    private static final String VIEW_LIGHT_MODE = "view_light_mode";
    private static final String VIEW_DARK_MODE = "view_dark_mode";
    private static final String NODE_NEW = "node_new";
    private static final String NODE_DELETE = "node_delete";
    private static final String NODE_REFRESH = "node_refresh";
    private static final String KEY_NEW = "key_new";
    private static final String KEY_DELETE = "key_delete";

    // references to views which will perform the work
    // associated with the menu items
    private final JPUI moJpui;
    private final TreeView moTreeView;
    private final EditNodeView moEditNodeView;

    /**
     * ctor
     *
     * @param oTreeView     left side tree view
     * @param oEditNodeView right side edit node view
     */
    public MainView(JPUI oJpui, TreeView oTreeView, EditNodeView oEditNodeView) {
        moJpui = oJpui;
        moTreeView = oTreeView;
        moEditNodeView = oEditNodeView;
        initMenu();
        PreferencesModel.Instance().addObserver(this);
    }

    /**
     * Populate the menu
     */
    protected void initMenu() {
        moMenuBar = new JMenuBar();
        JMenu oMenu = new JMenu(Resources.getString("menu_file"));
        JMenuItem oMenuItem = new JMenuItem(Resources.getString("file_about"));
        oMenuItem.setActionCommand(ABOUT_LICENSE);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        oMenuItem = new JMenuItem(Resources.getString("file_exit"));
        oMenuItem.setActionCommand(EXIT);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        moMenuBar.add(oMenu);

        // set light/dark mode
        oMenu = new JMenu(Resources.getString("menu_view"));
        oMenuItem = new JMenuItem(Resources.getString("view_light_mode"));
        oMenuItem.setActionCommand(VIEW_LIGHT_MODE);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        oMenuItem = new JMenuItem(Resources.getString("view_dark_mode"));
        oMenuItem.setActionCommand(VIEW_DARK_MODE);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        moMenuBar.add(oMenu);

        // add/delete nodes from current node
        oMenu = new JMenu(Resources.getString("menu_node"));
        oMenuItem = new JMenuItem(Resources.getString("node_new"));
        oMenuItem.setActionCommand(NODE_NEW);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        oMenuItem = new JMenuItem(Resources.getString("node_delete"));
        oMenuItem.setActionCommand(NODE_DELETE);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        oMenuItem = new JMenuItem(Resources.getString("node_refresh"));
        oMenuItem.setActionCommand(NODE_REFRESH);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        moMenuBar.add(oMenu);

        // add/delete keys on current node
        oMenu = new JMenu(Resources.getString("menu_attribute"));
        oMenuItem = new JMenuItem(Resources.getString("attribute_new"));
        oMenuItem.setActionCommand(KEY_NEW);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        oMenuItem = new JMenuItem(Resources.getString("attribute_delete"));
        oMenuItem.setActionCommand(KEY_DELETE);
        oMenuItem.addActionListener(this);
        oMenu.add(oMenuItem);
        moMenuBar.add(oMenu);
    }

    /**
     * @return javax.swing.JMenuBar
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
        if (e.getActionCommand().equals(EXIT)) {
            System.exit(0);
        } else if (e.getActionCommand().equals(ABOUT_LICENSE)) {
            AboutDialog.showDialog(AboutDialog.PAGE_ABOUT);
        } else if (e.getActionCommand().equals(NODE_NEW)) {
            moTreeView.newNode();
        } else if (e.getActionCommand().equals(NODE_DELETE)) {
            moTreeView.deleteNode();
        } else if (e.getActionCommand().equals(NODE_REFRESH)) {
            // Perform an update twice with a new PreferenceNode to work around
            // caching quirks.
            Preferences current = PreferencesModel.Instance().getCurrentNode();
            Preferences root = current.isUserNode() ?
                    Preferences.userRoot() : Preferences.systemRoot();
            Preferences copy = root.node(current.absolutePath());
            PreferencesModel.Instance().setCurrentNode(new PreferencesNode(copy));
            moEditNodeView.update(null);
            PreferencesModel.Instance().setCurrentNode(current);
            moEditNodeView.update(null);
        } else if (e.getActionCommand().equals(KEY_NEW)) {
            moEditNodeView.newKey();
        } else if (e.getActionCommand().equals(KEY_DELETE)) {
            moEditNodeView.deleteKey();
        } else if (e.getActionCommand().equals(VIEW_LIGHT_MODE)) {
            moJpui.setTheme(Theme.LIGHT);
            moJpui.updateTheme();
        } else if (e.getActionCommand().equals(VIEW_DARK_MODE)) {
            moJpui.setTheme(Theme.DARK);
            moJpui.updateTheme();
        }
    }

    @Override
    public void update(Observable o) {

    }

}
