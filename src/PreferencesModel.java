/*
 * PreferencesModel
 *
 * $RCSfile: PreferencesModel.java,v $
 * $Revision: 1.2 $
 * $Date: 2004/01/04 18:51:04 $
 * $Source: /cvsroot/jpui/jpui/src/PreferencesModel.java,v $
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

import java.util.Observable;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

/**
 * The model where all modifications to the preferences store are
 * done.  Uses the Observer pattern to notify the views when the
 * model changes.
 * This singleton class effectively adds the idea of a 'current node' to the
 * java preferences node heirarchy.
 */
public class PreferencesModel
    extends Observable
    implements NodeChangeListener, PreferenceChangeListener {
    // singleton instance
    private static PreferencesModel moInstance = new PreferencesModel();
    // reference to the current preferences node
    Preferences moCurrentNode = null;

    /**
     * Private ctor, clients use Instance()
     */
    private PreferencesModel() {
    	Preferences oSystem = Preferences.systemRoot();
    	Preferences oUser = Preferences.userRoot();
    	
    	// listen for preference chance events
    	oSystem.addNodeChangeListener(this);
    	oSystem.addPreferenceChangeListener(this);
    	oUser.addNodeChangeListener(this);
    	oUser.addPreferenceChangeListener(this);
    	
    	// the current node defaults to the system root
        moCurrentNode = oSystem;
    }

    /**
     * Singleton accessor
     * @return PreferencesModel
     */
    public static PreferencesModel Instance() {
        return moInstance;
    }

    /**
     * Gets the current preferences node
     * @return java.util.prefs.Preferences
     */
    public Preferences getCurrentNode() {
        return moCurrentNode;
    }

    /**
     * Sets the current preferences node and notifies
     * observers if the new node is not the same as the
     * previous current node.
     * @param oNode new current node
     */
    public void setCurrentNode(Preferences oNode) {
        if (!oNode.equals(moCurrentNode)) {
            moCurrentNode = oNode;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Sets the attribute sKey of the current node
     * @param sKey node key name
     * @param sValue node key value
     */
    public void setAttribute(String sKey, String sValue) {
        Preferences oCurrentNode = getCurrentNode();
        oCurrentNode.put(sKey, sValue);
        sync(oCurrentNode);
        setChanged();
        notifyObservers();
    }

    /**
     * Removes the attribute sKey of the current node
     * @param sKey node key to remove
     */
    public void removeAttribute(String sKey) {
        Preferences oCurrentNode = getCurrentNode();
        if (oCurrentNode.get(sKey, null) != null) {
            oCurrentNode.remove(sKey);
            sync(oCurrentNode);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Renames the attribute sOldKey of the current node
     * @param sOldKey node key old name
     * @param sNewKey node key new name
     */
    public void renameAttribute(String sOldKey, String sNewKey) {
        Preferences oCurrentNode = getCurrentNode();
        if (oCurrentNode.get(sNewKey, null) == null) {
            String sValue = oCurrentNode.get(sOldKey, null);
            oCurrentNode.put(sNewKey, sValue);
            oCurrentNode.remove(sOldKey);
            sync(oCurrentNode);
            setChanged();
            notifyObservers();
        }
        else {
            // TODO: a key by this name already exists
        }

    }

    /**
     * Creates a new node as a child of the current node
     * @param sNodeName new node name
     * @return java.util.prefs.Preferences the new node
     */
    public Preferences newNode(String sNodeName) {
        Preferences oCurrentNode = getCurrentNode();
        Preferences oNewNode = oCurrentNode.node(sNodeName);
        sync(oCurrentNode);
        setCurrentNode(oNewNode);
        return oNewNode;
    }

    /**
     * Deletes the current node and its children
     * @return java.util.prefs.Preferences the parent of the deleted node
     */
    public Preferences deleteNode() {
        Preferences oCurrentNode = getCurrentNode();
        Preferences oParentNode = oCurrentNode.parent();
        if (oParentNode != null) {
            try {
                oCurrentNode.removeNode();
                sync(oParentNode);
                setCurrentNode(oParentNode);
            }
            catch (BackingStoreException oEx) {
                // TODO: BackingStoreException
            }
        }
        return oParentNode;
    }

    /**
     * @see java.util.prefs.NodeChangeListener#childAdded(java.util.prefs.NodeChangeEvent)
     */
    public void childAdded(NodeChangeEvent evt) {
		System.out.println("childAdded");
    }

    /**
     * @see java.util.prefs.NodeChangeListener#childRemoved(java.util.prefs.NodeChangeEvent)
     */
    public void childRemoved(NodeChangeEvent evt) {
		System.out.println("childRemoved");
    }

    /**
     * @see java.util.prefs.PreferenceChangeListener#preferenceChange(java.util.prefs.PreferenceChangeEvent)
     */
    public void preferenceChange(PreferenceChangeEvent evt) {
		System.out.println("preferenceChange");
    }

    /**
     * Utility method to persist the preferences store after a change
     * @param oPref preferences node to sync from
     */
    private void sync(Preferences oPref) {
        try {
            oPref.sync();
        }
        catch (BackingStoreException oEx) {
            // TODO: BackingStoreException   
        }
    }
}