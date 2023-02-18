/*
 * PreferencesNode
 *
 * $RCSfile: PreferencesNode.java,v $
 * $Revision: 1.3 $
 * $Date: 2004/01/10 20:10:46 $
 * $Source: /cvsroot/jpui/jpui/src/PreferencesNode.java,v $
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

/**
 * Subclasses Preferences and overrides toString to
 * cleanup the node names that are displayed in
 * the JTree
 */
public class PreferencesNode extends Preferences {
    private Preferences moPref = null;

    /**
     * private ctor
     */
    private PreferencesNode() {
    }

    /**
     * Public ctor
     *
     * @param oPref node to delegate to
     */
    public PreferencesNode(Preferences oPref) {
        moPref = oPref;
    }

    /**
     * Access for the deligate java.util.prefs.Preferences object
     * Used by java.lang.Object.equals
     *
     * @return java.util.prefs.Preferences
     */
    protected Preferences getPreferences() {
        return moPref;
    }

    /**
     * @return java.util.prefs.Preferences
     * @see java.util.prefs.Preferences.parent
     */
    public Preferences parent() {
        if (moPref.parent() == null) {
            return null;
        } else {
            return new PreferencesNode(moPref.parent());
        }
    }

    /**
     * Returns a friendly string name for a node, suitable
     * for display in the JTree
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (moPref.parent() == null) {
            if (moPref.isUserNode() == true) {
                return "User";
            } else {
                return "System";
            }
        } else {
            return moPref.name();
        }
    }

    /**
     * Custom equals impl to compare the wrapped preferences objects
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        return moPref.equals(((PreferencesNode) obj).getPreferences());
    }

    /* (non-Javadoc)
     * @see java.util.prefs.Preferences#importPreferences(java.io.InputStream)
     */
    public static void importPreferences(InputStream is)
            throws IOException, InvalidPreferencesFormatException {
        Preferences.importPreferences(is);
    }

    /* (non-Javadoc)
     * @see java.util.prefs.Preferences#systemNodeForPackage(java.lang.Class)
     */
    public static Preferences systemNodeForPackage(Class c) {
        return Preferences.systemNodeForPackage(c);
    }

    /* (non-Javadoc)
     * @see java.util.prefs.Preferences#systemRoot()
     */
    public static Preferences systemRoot() {
        return new PreferencesNode(Preferences.systemRoot());
    }

    /* (non-Javadoc)
     * @see java.util.prefs.Preferences#userNodeForPackage(java.lang.Class)
     */
    public static Preferences userNodeForPackage(Class c) {
        return new PreferencesNode(Preferences.userNodeForPackage(c));
    }

    /* (non-Javadoc)
     * @see java.util.prefs.Preferences#userRoot()
     */
    public static Preferences userRoot() {
        return new PreferencesNode(Preferences.userRoot());
    }

    /**
     * @return
     */
    public String absolutePath() {
        return moPref.absolutePath();
    }

    /**
     * @param ncl
     */
    public void addNodeChangeListener(NodeChangeListener ncl) {
        moPref.addNodeChangeListener(ncl);
    }

    /**
     * @param pcl
     */
    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
        moPref.addPreferenceChangeListener(pcl);
    }

    /**
     * @return
     * @throws java.util.prefs.BackingStoreException
     */
    public String[] childrenNames() throws BackingStoreException {
        return moPref.childrenNames();
    }

    /**
     * @throws java.util.prefs.BackingStoreException
     */
    public void clear() throws BackingStoreException {
        moPref.clear();
    }

    /**
     * @param os
     * @throws java.io.IOException
     * @throws java.util.prefs.BackingStoreException
     */
    public void exportNode(OutputStream os)
            throws IOException, BackingStoreException {
        moPref.exportNode(os);
    }

    /**
     * @param os
     * @throws java.io.IOException
     * @throws java.util.prefs.BackingStoreException
     */
    public void exportSubtree(OutputStream os)
            throws IOException, BackingStoreException {
        moPref.exportSubtree(os);
    }

    /**
     * @throws java.util.prefs.BackingStoreException
     */
    public void flush() throws BackingStoreException {
        moPref.flush();
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public String get(String key, String def) {
        return moPref.get(key, def);
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public boolean getBoolean(String key, boolean def) {
        return moPref.getBoolean(key, def);
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public byte[] getByteArray(String key, byte[] def) {
        return moPref.getByteArray(key, def);
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public double getDouble(String key, double def) {
        return moPref.getDouble(key, def);
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public float getFloat(String key, float def) {
        return moPref.getFloat(key, def);
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public int getInt(String key, int def) {
        return moPref.getInt(key, def);
    }

    /**
     * @param key
     * @param def
     * @return
     */
    public long getLong(String key, long def) {
        return moPref.getLong(key, def);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return moPref.hashCode();
    }

    /**
     * @return
     */
    public boolean isUserNode() {
        return moPref.isUserNode();
    }

    /**
     * @return
     * @throws java.util.prefs.BackingStoreException
     */
    public String[] keys() throws BackingStoreException {
        return moPref.keys();
    }

    /**
     * @return
     */
    public String name() {
        return moPref.name();
    }

    /**
     * @param pathName
     * @return
     */
    public Preferences node(String pathName) {
        return new PreferencesNode(moPref.node(pathName));
    }

    /**
     * @param pathName
     * @return
     * @throws java.util.prefs.BackingStoreException
     */
    public boolean nodeExists(String pathName) throws BackingStoreException {
        return moPref.nodeExists(pathName);
    }

    /**
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        moPref.put(key, value);
    }

    /**
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        moPref.putBoolean(key, value);
    }

    /**
     * @param key
     * @param value
     */
    public void putByteArray(String key, byte[] value) {
        moPref.putByteArray(key, value);
    }

    /**
     * @param key
     * @param value
     */
    public void putDouble(String key, double value) {
        moPref.putDouble(key, value);
    }

    /**
     * @param key
     * @param value
     */
    public void putFloat(String key, float value) {
        moPref.putFloat(key, value);
    }

    /**
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        moPref.putInt(key, value);
    }

    /**
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        moPref.putLong(key, value);
    }

    /**
     * @param key
     */
    public void remove(String key) {
        moPref.remove(key);
    }

    /**
     * @throws java.util.prefs.BackingStoreException
     */
    public void removeNode() throws BackingStoreException {
        moPref.removeNode();
    }

    /**
     * @param ncl
     */
    public void removeNodeChangeListener(NodeChangeListener ncl) {
        moPref.removeNodeChangeListener(ncl);
    }

    /**
     * @param pcl
     */
    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
        moPref.removePreferenceChangeListener(pcl);
    }

    /**
     * @throws java.util.prefs.BackingStoreException
     */
    public void sync() throws BackingStoreException {
        moPref.sync();
    }

}
