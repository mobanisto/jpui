/*
 * EditNodeView
 *
 * $RCSfile: EditNodeView.java,v $
 * $Revision: 1.4 $
 * $Date: 2004/01/10 20:10:46 $
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

package org.jpui;

import org.jpui.observable.Observable;
import org.jpui.observable.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Right hand side of UI. Responsible for rendering
 * the current preference node.
 */
public class EditNodeView implements Observer {
    private final JPanel moPanel;
    private JTable moTable;

    /**
     * ctor
     */
    public EditNodeView() {
        moPanel = new JPanel();
        moPanel.setLayout(new BorderLayout());
        moTable = new JTable();

        PreferencesModel.Instance().addObserver(this);
        renderTable();
    }

    /**
     * @return javax.swing.JPanel
     */
    public JPanel getPanel() {
        return moPanel;
    }

    /**
     * Retrieves the current node from the model and
     * renders it via a JTable.
     */
    private void renderTable() {
        Preferences oPref = PreferencesModel.Instance().getCurrentNode();
        try {
            // Make sure to sync from the backing store to see updates made
            // to the store by different applications and don't see just our
            // cached version.
            oPref.sync();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }

        moPanel.remove(moTable.getTableHeader());
        moPanel.remove(moTable);
        moTable = new JTable(new PreferencesNodeTable(oPref));
        moTable.setShowGrid(true);
        moTable.setGridColor(Color.LIGHT_GRAY);
        moPanel.add(moTable.getTableHeader(), BorderLayout.NORTH);
        moPanel.add(moTable, BorderLayout.CENTER);
        moPanel.validate();
    }

    @Override
    public void update(Observable oObject) {
        renderTable();
    }

    /**
     * Prompts for and creates a new attribute key for the
     * currently selected node.
     */
    public void newKey() {
        String sNewKey =
                JOptionPane.showInputDialog(
                        moPanel.getParent(),
                        Resources.getString("new_attr_message"),
                        Resources.getString("new_attr_title"),
                        JOptionPane.QUESTION_MESSAGE);
        if (sNewKey != null) {
            PreferencesModel.Instance().setAttribute(sNewKey, "");
        }
    }

    /**
     * Deletes the currently selected attribute.
     */
    public void deleteKey() {
        int nRow = moTable.getSelectedRow();
        if (nRow >= 0) {
            String sKey = moTable.getValueAt(nRow, 0).toString();
            if (sKey != null) {
                PreferencesModel.Instance().removeAttribute(sKey);
            }
        }
    }
}
