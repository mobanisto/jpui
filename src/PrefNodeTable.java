/*
 * PrefNodeTable
 *
 * $RCSfile: PrefNodeTable.java,v $
 * $Revision: 1.1 $
 * $Date: 2003/10/05 15:03:40 $
 * $Source: /cvsroot/jpui/jpui/src/Attic/PrefNodeTable.java,v $
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
 
import java.util.prefs.Preferences;

import javax.swing.table.AbstractTableModel;

/**
 * Table of a preference nodes keys and values
 */
public class PrefNodeTable extends AbstractTableModel {
	private Preferences moPref;

	private static String[] msColumns = { "Key", "Value" };

	private PrefNodeTable(){}
	public PrefNodeTable(Preferences oPref) {
		moPref = oPref;
	}
    
	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		int nRows = 0;
		try {
			nRows = moPref.keys().length;
		}
		catch (Exception oEx) {
			oEx.printStackTrace();
		}
		return nRows;
	}

	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int nRow, int nColumn) {
		String sReturn = null;
		try {
			String[] sKeys = moPref.keys();
			if(nColumn == 0) {
				sReturn = sKeys[nRow];
			}
			else {
				sReturn = moPref.get(sKeys[nRow], null);
			}
		}
		catch(Exception oEx) {
			oEx.printStackTrace();
		}
		return sReturn;
	}
    
	/**
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int nCol) {
		return msColumns[nCol];
	}

	/**
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int nRow, int nColumn) {
		return (nColumn == 1);
	}

}

