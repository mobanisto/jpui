/*
 * PrefModel
 *
 * $RCSfile: PrefModel.java,v $
 * $Revision: 1.1 $
 * $Date: 2003/10/05 15:03:40 $
 * $Source: /cvsroot/jpui/jpui/src/Attic/PrefModel.java,v $
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
import java.util.prefs.Preferences;

/**
 * @author mark
 *
 */
/**
 * The Preference model
 * 
 * Maintains the currently selected or active
 * preference node.  There is ever only one active
 * node between both the User and System preference
 * trees.
 * 
 * Notifies observers when the model changes
 * 
 * Support still needs to be added for changing the model
 * 
 * TODO changing a preference keys value
 * TODO changing a preference keys name
 * TODO deleting a preference key
 * TODO deleting a preference node
 * TODO adding a preference key
 * TODO adding a preference node
 */
public class PrefModel extends Observable {
	private Preferences moCurrentPrefNode = Preferences.systemRoot();
	private boolean mbCurrentIsUser = false;

	/**
     * One and only ctor to create a preference model
     */
    public PrefModel() {
		super();
	}
	
	/**
     * @return
     */
    public Preferences getCurrentNode() {
		return moCurrentPrefNode;
	}
	/**
     * @return
     */
    public Preferences getSysRootNode() {
		return Preferences.systemRoot();
	}
	/**
     * @return
     */
    public Preferences getUserRootNode() {
		return Preferences.userRoot();
	}

	/**
     * The views will call this method to change
     * the models current preference node.
     * 
     * @param oPref
     */
    public void setCurrentNode(Preferences oPref) {
		if(!moCurrentPrefNode.equals(oPref)) {
			moCurrentPrefNode = oPref;
			if(oPref.isUserNode()) {
				mbCurrentIsUser = true;
			}
			else {
				mbCurrentIsUser = false;
			}
            
            // tell the observers the model has changed
            // so they can render the new current node
			setChanged();
			notifyObservers();
		}
	}
}
