/*
 * RootPreferenceNode
 *
 * $RCSfile: RootPreferencesNode.java,v $
 * $Revision: 1.3 $
 * $Date: 2004/01/10 20:10:46 $
 * $Source: /cvsroot/jpui/jpui/src/RootPreferencesNode.java,v $
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

import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Class that helps implement TreeModel for java preferences by
 * creating a virtual root node with the User preference tree
 * and System preference tree is its only two children.
 */
public class RootPreferencesNode extends Preferences {
    // contants
    private static final int USER = 0;
    private static final int SYSTEM = 1;
    
	/**
	 * @see java.util.prefs.Preferences#absolutePath()
	 */
	public String absolutePath() {
		return "";
	}

	/**
	 * @see java.util.prefs.Preferences#addNodeChangeListener(java.util.prefs.NodeChangeListener)
	 */
	public void addNodeChangeListener(NodeChangeListener ncl) {
	}

	/**
	 * @see java.util.prefs.Preferences#addPreferenceChangeListener(java.util.prefs.PreferenceChangeListener)
	 */
	public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
	}

	/**
	 * @see java.util.prefs.Preferences#childrenNames()
	 */
	public String[] childrenNames() throws BackingStoreException {
		return new String[] {
			"User",
			"System"
		};
	}

	/**
	 * @see java.util.prefs.Preferences#clear()
	 */
	public void clear() throws BackingStoreException {
	}

	/**
	 * @see java.util.prefs.Preferences#exportSubtree(java.io.OutputStream)
	 */
	public void exportSubtree(OutputStream os)
		throws IOException, BackingStoreException {
	}

	/**
	 * @see java.util.prefs.Preferences#flush()
	 */
	public void flush() throws BackingStoreException {
	}

	/**
	 * @see java.util.prefs.Preferences#get(java.lang.String, java.lang.String)
	 */
	public String get(String key, String def) {
		return null;
	}

	/**
	 * @see java.util.prefs.Preferences#getBoolean(java.lang.String, boolean)
	 */
	public boolean getBoolean(String key, boolean def) {
		return false;
	}

    /**
	 * @see java.util.prefs.Preferences#getByteArray(java.lang.String, byte[])
	 */
	public byte[] getByteArray(String key, byte[] def) {
		return null;
	}

    /**
	 * @see java.util.prefs.Preferences#getDouble(java.lang.String, double)
	 */
	public double getDouble(String key, double def) {
		return 0;
	}

    /**
	 * @see java.util.prefs.Preferences#getFloat(java.lang.String, float)
	 */
	public float getFloat(String key, float def) {
		return 0;
	}

    /**
	 * @see java.util.prefs.Preferences#getInt(java.lang.String, int)
	 */
	public int getInt(String key, int def) {
		return 0;
	}

    /**
	 * @see java.util.prefs.Preferences#getLong(java.lang.String, long)
	 */
	public long getLong(String key, long def) {
		return 0;
	}

    /**
	 * @see java.util.prefs.Preferences#isUserNode()
	 */
	public boolean isUserNode() {
		return false;
	}

    /**
	 * @see java.util.prefs.Preferences#keys()
	 */
	public String[] keys() throws BackingStoreException {
		return null;
	}

    /**
	 * @see java.util.prefs.Preferences#name()
	 */
	public String name() {
		return "";
	}

    /**
	 * @see java.util.prefs.Preferences#node(java.lang.String)
	 */
	public Preferences node(String pathName) {
		if(pathName.equals("User")) {
			return new PreferencesNode(Preferences.userRoot());
		}
		else if(pathName.equals("System")) {
			return new PreferencesNode(Preferences.systemRoot());
		}
		
		return null;
	}

    /**
	 * @see java.util.prefs.Preferences#nodeExists(java.lang.String)
	 */
	public boolean nodeExists(String pathName) throws BackingStoreException {
		if(pathName.equals("User")) {
			return true;
		}
		else if(pathName.equals("System")) {
			return true;
		}
		return false;
	}

    /**
	 * @see java.util.prefs.Preferences#parent()
	 */
	public Preferences parent() {
		return null;
	}

    /**
	 * @see java.util.prefs.Preferences#put(java.lang.String, java.lang.String)
	 */
	public void put(String key, String value) {
	}

    /**
	 * @see java.util.prefs.Preferences#putBoolean(java.lang.String, boolean)
	 */
	public void putBoolean(String key, boolean value) {
	}

    /**
	 * @see java.util.prefs.Preferences#putByteArray(java.lang.String, byte[])
	 */
	public void putByteArray(String key, byte[] value) {
	}

    /**
	 * @see java.util.prefs.Preferences#putDouble(java.lang.String, double)
	 */
	public void putDouble(String key, double value) {
	}

    /**
	 * @see java.util.prefs.Preferences#putFloat(java.lang.String, float)
	 */
	public void putFloat(String key, float value) {
	}

    /**
	 * @see java.util.prefs.Preferences#putInt(java.lang.String, int)
	 */
	public void putInt(String key, int value) {
	}

    /**
	 * @see java.util.prefs.Preferences#putLong(java.lang.String, long)
	 */
	public void putLong(String key, long value) {
	}

    /**
	 * @see java.util.prefs.Preferences#remove(java.lang.String)
	 */
	public void remove(String key) {
	}

    /**
	 * @see java.util.prefs.Preferences#removeNode()
	 */
	public void removeNode() throws BackingStoreException {
	}

    /**
	 * @see java.util.prefs.Preferences#removeNodeChangeListener(java.util.prefs.NodeChangeListener)
	 */
	public void removeNodeChangeListener(NodeChangeListener ncl) {
	}

    /**
	 * @see java.util.prefs.Preferences#removePreferenceChangeListener(java.util.prefs.PreferenceChangeListener)
	 */
	public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
	}

    /**
	 * @see java.util.prefs.Preferences#sync()
	 */
	public void sync() throws BackingStoreException {
	}

    /**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "";
	}

    /**
	 * @see java.util.prefs.Preferences#exportNode(java.io.OutputStream)
	 */
	public void exportNode(OutputStream os)
		throws IOException, BackingStoreException {
	}

}

