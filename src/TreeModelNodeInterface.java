/*
 * TreeModelNodeInterface
 *
 * $RCSfile: TreeModelNodeInterface.java,v $
 * $Revision: 1.1 $
 * $Date: 2004/01/01 17:41:45 $
 * $Source: /cvsroot/jpui/jpui/src/Attic/TreeModelNodeInterface.java,v $
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

import javax.swing.tree.TreePath;

/**
 * Interface defining the methods used to be a node
 * in a TreeModel
 */
public interface TreeModelNodeInterface {

    /**
     * @see javax.swing.tree.TreeModel#getChild(java.lang.Object, int)
     */
    public Object getChild(int nIndex);

    /**
     * @see javax.swing.tree.TreeModel#getChildCount(java.lang.Object)
     */
    public int getChildCount();

    /**
     * @see javax.swing.tree.TreeModel#isLeaf(java.lang.Object)
     */
    public boolean isLeaf();

    /**
     * @see javax.swing.tree.TreeModel#valueForPathChanged(javax.swing.tree.TreePath, java.lang.Object)
     */
    public void valueForPathChanged(TreePath path, Object newValue);

    /**
     * @see javax.swing.tree.TreeModel#getIndexOfChild(java.lang.Object, java.lang.Object)
     */
    public int getIndexOfChild(Object oChild);

}
