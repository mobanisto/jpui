/*
 * JPUI
 *
 * $RCSfile: JPUI.java,v $
 * $Revision: 1.3 $
 * $Date: 2004/01/04 18:51:04 $
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

package org.jpui;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import de.topobyte.shared.preferences.SharedPreferences;
import de.topobyte.swing.util.SwingUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The one and only main program class.
 * <p>
 * Builds the model, the views, and lays out the GUI.
 */
public class JPUI {
    // views
    private MainView moMainView;
    private TreeView moTreeView;
    private EditNodeView moEditNodeView;

    // gui parts
    private JFrame moFrame = null;
    private JPanel moContentPanel = null;

    public JPUI() {
        initialize();
    }

    /**
     * Construct and put together the model, views,
     * and UI components.
     */
    private void initialize() {
        // the views
        moTreeView = new TreeView();
        moEditNodeView = new EditNodeView();
        moMainView = new MainView(moTreeView, moEditNodeView);

        //
        // the gui
        //

        // main frame
        moFrame = new JFrame(Resources.getString("title_bar"));
        moFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        moFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent oEv) {
                System.exit(0);
            }
        });

        // menu
        moFrame.setJMenuBar(moMainView.getMenuBar());

        // content panel and tool bar
        moContentPanel = new JPanel();
        moContentPanel.setLayout(new BorderLayout());
        moContentPanel.setPreferredSize(new Dimension(600, 500));

        moFrame.setContentPane(moContentPanel);

        // the main view
        JScrollPane oTreePane = new JScrollPane(moTreeView.getPanel());
        JScrollPane oEditNodePane = new JScrollPane(moEditNodeView.getPanel());
        JSplitPane oSplitPane =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        oTreePane,
                        oEditNodePane);
        oSplitPane.setDividerLocation(200);
        moContentPanel.add(oSplitPane, BorderLayout.CENTER);
    }

    /**
     * @return javax.swing.JPanel
     */
    public JPanel getContentPanel() {
        return moContentPanel;
    }

    /**
     * @return javax.swing.JFrame
     */
    public JFrame getFrame() {
        return moFrame;
    }

    /**
     * @param oArgs arguments to main
     */
    public static void main(String[] oArgs) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        if (SharedPreferences.isUIScalePresent()) {
            SwingUtils.setUiScale(SharedPreferences.getUIScale());
        }

        final JPUI oPrefGUI = new JPUI();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("icon.png")) {
            BufferedImage image = ImageIO.read(input);
            oPrefGUI.getFrame().setIconImage(image);
        } catch (IOException e) {
            // ignore, continue without icon
        }
        oPrefGUI.getFrame().pack();
        oPrefGUI.getFrame().setVisible(true);
    }
}
