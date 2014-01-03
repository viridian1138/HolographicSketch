



//$$strtCprt
/**
* Holographic Sketch -- Stereoscopic 3-D Sketch Program for Android
* 
* Copyright (C) 1993-2012 Thornton Green
* 
* This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
* published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
* of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with this program; if not, 
* see <http://www.gnu.org/licenses>.
* Additional permission under GNU GPL version 3 section 7
*
* If you modify this Program, or any covered work, by linking or combining it with Android 
* (or a modified version of that library), containing parts covered by the terms of the Android licenses, 
* the licensors of this Program grant you additional permission to convey the resulting work. {Corresponding Source for
* a non-source form of such a combination shall include the source code for the parts of Android used as well 
* as that of the covered work.}
* 
* If you modify this Program, or any covered work, by linking or combining it with HTC OpenSense 
* (or a modified version of that library), containing parts covered by the terms of HTC OpenSense Licenses, 
* the licensors of this Program grant you additional permission to convey the resulting work. {Corresponding Source for
* a non-source form of such a combination shall include the source code for the parts of HTC OpenSense used as well 
* as that of the covered work.}
* 
* If you modify this Program, or any covered work, by linking or combining it with HTC OpenSense Demo Code 
* (or a modified version of that library), containing parts covered by the terms of the Apache License, 
* the licensors of this Program grant you additional permission to convey the resulting work. {Corresponding Source for
* a non-source form of such a combination shall include the source code for the parts of the OpenSense Demo Code as well 
* as that of the covered work.}
* 
*
*/
//$$endCprt




package meta;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * --- SOURCE MODIFICATION LIST ---
 *
 * Please document all changes to this source file here.
 * Feel free to add rows if needed.
 *
 *
 *    |-----------------------|-------------------------------------------------|----------------------------------------------------------------------|---------------------------------------------------------------...
 *    | Date of Modification  |    Author of Modification                       |    Reason for Modification                                           |    Description of Modification (use multiple rows if needed)  ...
 *    |-----------------------|-------------------------------------------------|----------------------------------------------------------------------|---------------------------------------------------------------...
 *    |                       |                                                 |                                                                      |
 *    | 9/24/2000             | Thorn Green (viridian_1138@yahoo.com)           | Needed to provide a standard way to document source file changes.    | Added a souce modification list to the documentation so that changes to the souce could be recorded.
 *    | 10/22/2000            | Thorn Green (viridian_1138@yahoo.com)           | Methods did not have names that followed standard Java conventions.  | Performed a global modification to bring the names within spec.
 *    | 10/29/2000            | Thorn Green (viridian_1138@yahoo.com)           | Classes did not have names that followed standard Java conventions.  | Performed a global modification to bring the names within spec.
 *    | 08/12/2001            | Thorn Green (viridian_1138@yahoo.com)           | First-Cut at Error Handling.                                         | First-Cut at Error Handling.
 *    | 09/29/2001            | Thorn Green (viridian_1138@yahoo.com)           | Meta contained anachronisms from C++ that could hurt performance.    | Removed a number of the anachronisms.
 *    | 05/10/2002            | Thorn Green (viridian_1138@yahoo.com)           | Redundant information in persistent storage.                         | Made numerous persistence and packaging changes.
 *    | 08/07/2004            | Thorn Green (viridian_1138@yahoo.com)           | Establish baseline for all changes in the last year.                 | Establish baseline for all changes in the last year.
 *    | 10/13/2005            | Thorn Green (viridian_1138@yahoo.com)           | Update copyright.                                                    | Update copyright.
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *    |                       |                                                 |                                                                      |
 *
 *
 */

/**
 * HighLevelBinTree implements abstract linked Binary Trees which can be used
 * with any Meta-compliant datatype.  Both standard and Alt nodes are supported
 * by the data structure.  The information in each node is treated as a reference
 * to Meta, and to get it back to its original type one must use the typecast or
 * instanceof operators.<P>
 *	HighLevelBinTree structures are built out of linked collections of
 * {@link LowLevelBinTree} nodes.  The
 * {@link LowLevelBinTree} nodes implement many of the
 * features supported by HighLevelBinTree.<P>
 * @author Thorn Green
 */
public class HighLevelBinTree extends Meta implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (HighLevelBinTree.class).getName().hashCode() + "v3/98A".hashCode();
	
    /**
     * @see meta.Meta
     */
    public Meta copyNode() {
        HighLevelBinTree temp = new HighLevelBinTree();
        
        if (this.myTree != null) {
            temp.dvSetMyTree((LowLevelBinTree) myTree.copyNode());
        } else {
            temp.dvSetMyTree(null);
        }
        
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copySub() {
        HighLevelBinTree temp = new HighLevelBinTree();
        
        if (this.myTree != null) {
            temp.dvSetMyTree((LowLevelBinTree) myTree.copySub());
        } else {
            temp.dvSetMyTree(null);
        }
        
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyAll() {
        HighLevelBinTree temp = new HighLevelBinTree();
        
        if (this.myTree != null) {
            temp.dvSetMyTree((LowLevelBinTree) myTree.copyAll());
        } else {
            temp.dvSetMyTree(null);
        }
        
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyData() {
        HighLevelBinTree temp = new HighLevelBinTree();
        
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyDataPlusPtr() {
        HighLevelBinTree temp = new HighLevelBinTree();
        temp.dvSetMyTree(myTree);
        
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public void copyNodeInfo(Meta in) {
        HighLevelBinTree temp = (HighLevelBinTree) in;
        
        if (this.myTree != null) {
            temp.dvSetMyTree((LowLevelBinTree) myTree.copyNode());
        } else {
            temp.dvSetMyTree(null);
        }
    };
    /**
     * @see meta.Meta
     */
    public void copySubInfo(Meta in) {
        HighLevelBinTree temp = (HighLevelBinTree) in;
        
        if (this.myTree != null) {
            temp.dvSetMyTree((LowLevelBinTree) myTree.copySub());
        } else {
            temp.dvSetMyTree(null);
        }
    };
    /**
     * @see meta.Meta
     */
    public void copyAllInfo(Meta in) {
        HighLevelBinTree temp = (HighLevelBinTree) in;
        
        if (this.myTree != null) {
            temp.dvSetMyTree((LowLevelBinTree) myTree.copyAll());
        } else {
            temp.dvSetMyTree(null);
        }
    };
    /**
     * @see meta.Meta
     */
    public void copyDataInfo(Meta in) {};
    /**
     * @see meta.Meta
     */
    public void copyDataPlusPtrInfo(Meta in) {
        HighLevelBinTree temp = (HighLevelBinTree) in;
        temp.dvSetMyTree(myTree);
    };
    public void eraseNode() {
        if (this.myTree != null) {
            myTree.eraseNode();
        }
        
    };
    /**
     * @see meta.Meta
     */
    public void eraseSub() {
        if (this.myTree != null) {
            myTree.eraseSub();
        }
        
    };
    /**
     * @see meta.Meta
     */
    public void eraseAll() {
        if (this.myTree != null) {
            myTree.eraseAll();
        }
        
    };
    /**
     * @see meta.Meta
     */
    public void eraseData() {};
    /**
     * @see meta.Meta
     */
    public void eraseNodeInfo() {
        LowLevelBinTree temp;
        
        if (this.myTree != null) {
            if (myTree.right() != myTree)
                temp = myTree.right();
            else
                temp = null;
            myTree.eraseNode();
            myTree = temp;
        }
    };
    /**
     * @see meta.Meta
     */
    public void eraseSubInfo() {
        LowLevelBinTree temp;
        
        if (this.myTree != null) {
            temp = null;
            myTree.eraseSub();
            myTree = temp;
        }
    };
    /**
     * @see meta.Meta
     */
    public void eraseAllInfo() {
        if (this.myTree != null) {
            myTree.eraseAll();
        }
        
        myTree = null;
    };
    /**
     * @see meta.Meta
     */
    public void erasePtrVal() {
        myTree = null;
    };
    /**
     * @see meta.Meta
     */
    public void wake() {};
    /**
     * Initializes the binary tree.
     */
    public final void iHighLevelBinTree() {
        this.myTree = null;
    };
    public HighLevelBinTree() {
        this.iHighLevelBinTree();
    };
    /**
     * Moves the current node one node to the right.
     */
    public final void right() {
        myTree = myTree.right();
    };
    /**
     * Moves the current node one node to the left.
     */
    public final void left() {
        myTree = myTree.left();
    };
    /**
     * Returns true iff. the left link of the current node is threaded.
     */
    public final boolean lThread() {
        return (myTree.lThread());
    };
    /**
     * Returns true iff. the right link of the current node is threaded.
     */
    public final boolean rThread() {
        return (myTree.rThread());
    };
    /**
     * Returns the data in the current node.
     */
    public final Meta getNode() {
        return (myTree.getNode());
    };
    /**
     * Sets the data in the current node to <code>in</code>.
     */
    public final void setNode(Meta in) {
        myTree.setNode(in);
    };
    /**
     * Sets the CopyMode of the current node.
     */
    public final void setCopyMode(int copy) {
        myTree.setCopyMode(copy);
    };
    /**
     * Sets the CopyInfoMode of the current node.
     */
    public final void setCopyInfoMode(int copy) {
        myTree.setCopyInfoMode(copy);
    };
    /**
     * Sets the EraseMode of the current node.
     */
    public final void setEraseMode(int erase) {
        myTree.setEraseMode(erase);
    };
    /**
     * Returns the CopyMode of the current node.
     */
    public final int getCopyMode() {
        return (myTree.getCopyMode());
    };
    /**
     * Returns the CopyInfoMode of the current node.
     */
    public final int getCopyInfoMode() {
        return (myTree.getCopyInfoMode());
    };
    /**
     * Returns the EraseMode of the current node.
     */
    public final int getEraseMode() {
        return (myTree.getEraseMode());
    };
    /**
     * Returns true iff. the tree is empty.
     */
    public final boolean empty() {
        return (myTree == null);
    };
    /**
     * Returns true iff. the tree nodes are identical.
     */
    public final boolean equal(HighLevelBinTree compTree) {
        return (myTree == compTree.dvGetMyTree());
    };
    /**
     * Inserts the data <code>in</code> to the left of the current node.
     */
    public final void addLeft(Meta in) {
        if (this.myTree != null) {
            myTree.addLeft(in);
            myTree = myTree.left();
        } else {
            myTree = new StdLowLevelBinTree();
            myTree.setNode(in);
        }
    };
    /**
     * Inserts the data <code>in</code> to the right of the current node.
     */
    public final void addRight(Meta in) {
        if (this.myTree != null) {
            myTree.addRight(in);
            myTree = myTree.right();
        } else {
            myTree = new StdLowLevelBinTree();
            myTree.setNode(in);
        }
    };
    /**
     * Inserts the node <code>in</code> to the left of the current node.
     */
    public final void importAddLeft(LowLevelBinTree in) {
        if (this.myTree != null) {
            myTree.importAddLeft(in);
            myTree = myTree.left();
        } else {
            myTree = in;
        }
    };
    /**
     * Inserts the node <code>in</code> to the right of the current node.
     */
    public final void importAddRight(LowLevelBinTree in) {
        if (this.myTree != null) {
            myTree.importAddRight(in);
            myTree = myTree.right();
        } else {
            myTree = in;
        }
    };
    /**
     * Prunes everything to the left of the current node, using the EraseModes provided.
     */
    public final void pruneLeft() {
        if (myTree != null)
            myTree.pruneLeft();
        else {
            /* exit( 1 ); */
            /* throw( UndefinedOperation ); */
        }
    };
    /**
     * Places a copy of the left subtree of the current node in <code>out</code>.
     */
    public final void copyLeft(HighLevelBinTree out) {
        if (myTree != null) {
            if (out.dvGetMyTree() != null)
                myTree.copyLeft(out.dvGetMyTree());
            else {
                /* Do Something Here. */
            }
        }
    };
    /**
     * Performs an inorder traversal, executing the Callback with each node visited.
     */
    public final void inOrder(HighLevelBinTree TStop, Callback InClass, int in) {
        if (myTree != null) {
            myTree.inOrder(TStop.dvGetMyTree(), InClass, in);
        }
    };
    /**
     * Performs an preorder traversal, executing the Callback with each node visited.
     */
    public final void preOrder(HighLevelBinTree TStop, Callback InClass, int in) {
        if (myTree != null) {
            myTree.preOrder(TStop.dvGetMyTree(), InClass, in);
        }
    };
    /**
     * Performs an postorder traversal, executing the Callback with each node visited.
     */
    public final void postOrder(HighLevelBinTree TStop, Callback InClass, int in) {
        if (myTree != null) {
            myTree.postOrder(TStop.dvGetMyTree(), InClass, in);
        }
    };
    /**
     * Copies this tree to the right of <code>out</code>.
     */
    public final void pasteRight(HighLevelBinTree out) {
        if (myTree != null) {
            if (out.dvGetMyTree() != null) {
                myTree.pasteRight(out.dvGetMyTree());
            } else {
                /* Do Something Here. */
            }
        }
    };
    /**
     * Copies this tree to the left of <code>out</code>.
     */
    public final void pasteLeft(HighLevelBinTree out) {
        if (myTree != null) {
            if (out.dvGetMyTree() != null) {
                myTree.pasteLeft(out.dvGetMyTree());
            } else {
                /* Do Something Here. */
            }
        }
    };
    /**
     * Connects this tree to the right of <code>out</code>.
     */
    public final void connectRight(HighLevelBinTree out) {
        if (myTree != null) {
            if (out.dvGetMyTree() != null) {
                myTree.connectRight(out.dvGetMyTree());
            } else {
                /* Do Something Here. */
            }
        }
    };
    /**
     * Connects this tree to the left of <code>out</code>.
     */
    public final void connectLeft(HighLevelBinTree out) {
        if (myTree != null) {
            if (out.dvGetMyTree() != null) {
                myTree.connectLeft(out.dvGetMyTree());
            } else {
                /* Do Something Here. */
            }
        }
    };
    /**
     * Traverses to the right until a right-thread is found.  Then sets the current node
     * to that node.
     */
    public final void findEnd() {
        if (myTree != null)
            myTree = myTree.findEnd();
        else {
            /* Do Something Here. */
        }
    };
    /**
     * As if the binary tree is a representation of a generalized list, finds
     * the "parent" of the current node and sets the current node to that node.
     */
    public final void listParent() {
        if (myTree != null)
            myTree = myTree.listParent();
        else {
            /* Do Something Here. */
        }
    };
    
    /**
     * Writes serial data.
     * <P>
     * @serialData TBD.
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        LowLevelBinTree tmp = myTree;
        
        if (tmp == null) {} else {
            out.writeObject(myTree);
        }
        
        out.writeObject("s"); /* Stop */
    }
    
    /**
     * Reads serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        try {
            myTree = null;
            Object myo = in.readObject();
            
            while (myo instanceof LowLevelBinTree) {
                LowLevelBinTree myl = (LowLevelBinTree) myo;
                VersionBuffer.chkNul(myl);
                
                if (myTree == null) {
                    myTree = myl;
                }
                
                myo = in.readObject();
            }
        } catch (ClassCastException e) {
            throw (new DataFormatException(e));
        }
        
    }
    
    private final void dvSetMyTree(LowLevelBinTree in) {
        myTree = in;
    }
    private final LowLevelBinTree dvGetMyTree() {
        return (myTree);
    }
    
    private LowLevelBinTree myTree;
};
