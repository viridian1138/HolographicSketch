


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
 * LowLevelBinTree provides structure and code for the implementation of
 * {@link HighLevelBinTree}.  For more information, see {@link HighLevelBinTree}.  LowLevelBinTree
 * is abstract, but creates instances of {@link StdLowLevelBinTree}.  Other subclasses
 * of LowLevelBinTree can also be used.  The procedure for doing this is beyond the scope
 * of the description here.  One should not be directly creating LowLevelBinTree structures.
 * Instead, use {@link HighLevelBinTree} for typical applications in which a binary tree
 * is needed.
 * @author Thorn Green
 */
public abstract class LowLevelBinTree extends LowLevelType implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (LowLevelBinTree.class).getName().hashCode() + "v3/98A".hashCode();
	
    /**
     * @see meta.Meta
     */
    public abstract Meta copyNode();
    /**
     * @see meta.Meta
     */
    public Meta copySub() {
        LowLevelBinTree ptr = (LowLevelBinTree) this;
        LowLevelBinTree end = listParent();
        LowLevelBinTree out;
        
        if (end == this) { throw( new UndefinedOperation() );
        }
        
        out = (LowLevelBinTree) ptr.copyNode();
        ptr.copyLeft(out);
        ptr = ptr.right();
        
        while (ptr != end) {
            out.importAddRight((LowLevelBinTree) ptr.copyNode());
            out = out.right();
            ptr.copyLeft(out);
            ptr = ptr.right();
        }
        
        out = out.right();
        return (out);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyAll() {
        LowLevelBinTree ptr = (LowLevelBinTree) this;
        LowLevelBinTree temp = ptr.listParent();
        LowLevelBinTree out;
        
        while (temp != ptr) {
            ptr = temp;
            temp = ptr.listParent();
        }
        
        out = (LowLevelBinTree) temp.copyNode();
        temp.copyLeft(out);
        temp = temp.right();
        
        while (temp != ptr) {
            out.importAddRight((LowLevelBinTree) temp.copyNode());
            out = out.right();
            temp.copyLeft(out);
            temp = temp.right();
        }
        
        return (out);
    };
    /**
     * @see meta.Meta
     */
    public void eraseNode() {
        if (lThread() == true) {
            LowLevelBinTree temp;
            LowLevelBinTree parent = listParent();
            LowLevelBinTree t = this;
            if (parent.left() == this) {
                parent.dvSetLeft(t.right());
                temp = t.right();
                while (temp.lThread() == false)
                    temp = temp.left();
                temp.dvSetLeft(t.left());
            } else {
                if (rThread() == false) {
                    t.left().dvSetRight(t.right());
                    t = t.right();
                    while (t.lThread() == false)
                        t = t.left();
                    t.dvSetLeft(this.left());
                } else {
                    t.left().right().dvSetRight(t.right());
                    t.left().dvSetRThread(true);
                }
            }
            
            this.dispose();
        } else {
            throw( new UndefinedOperation() );
        }
    };
    /**
     * @see meta.Meta
     */
    public void eraseSub() {
        LowLevelBinTree ptr;
        LowLevelBinTree end = findEnd();
        LowLevelBinTree delTemp;
        
        if (end != this)
            end = end.right();
        else { throw( new UndefinedOperation() );
        }
        
        if (end.left() == this)
            end.pruneLeftComp();
        else {
            ptr = this;
            while (!(ptr.lThread()))
                ptr = ptr.left();
            ptr = ptr.left();
            ptr.dvSetRight(end);
            ptr.dvSetRThread(true);
            
            ptr = this;
            while (ptr != end) {
                ptr.pruneLeftComp();
                delTemp = ptr;
                ptr = ptr.right();
                delTemp.dispose();
            }
        }
    };
    /**
     * @see meta.Meta
     */
    public void eraseAll() {
        LowLevelBinTree ptr = this;
        LowLevelBinTree temp = ptr.listParent();
        LowLevelBinTree delTemp;
        
        while (temp != ptr) {
            ptr = temp;
            temp = ptr.listParent();
        }
        
        temp = temp.right();
        while (temp != ptr) {
            temp.pruneLeft();
            delTemp = temp;
            temp = temp.right();
            delTemp.dispose();
        }
        
        ptr.pruneLeft();
        ptr.dispose();
    };
    /**
     * @see meta.Meta
     */
    public void wake() {};
    /**
     * Returns the node to the right of this node.
     */
    public final LowLevelBinTree right() {
        return (right);
    };
    /**
     * Returns the node to the left of this node.
     */
    public final LowLevelBinTree left() {
        return (left);
    };
    /**
     * Returns true iff. the left-link is threaded.
     */
    public final boolean lThread() {
        return (lthread);
    };
    /**
     * Returns true iff. the right-link is threaded.
     */
    public final boolean rThread() {
        return (rthread);
    };
    /**
     * Initializes the binary tree.
     */
    public final void iLowLevelBinTree() {
        left = this;
        right = this;
        lthread = true;
        rthread = false;
    };
    public LowLevelBinTree() {
        super();
        this.iLowLevelBinTree();
    };
    /**
     * Prunes everything to the left of this node, using the EraseModes provided.
     */
    public final void pruneLeft() {
        if (!lthread)
            pruneLeftComp();
    };
    /**
     * Adds a node to the right of this one.
     */
    public final void addRight(Meta in) {
        if (rthread)
            addRightLeaf();
        else
            addRightComp();
        right.setNode(in);
    };
    /**
     * Adds a node to the left of this one.
     */
    public final void addLeft(Meta in) {
        if (lthread)
            addLeftLeaf();
        else
            addLeftComp();
        left.setNode(in);
    };
    /**
     * Adds a node to the right of this one.
     */
    public final void importAddRight(LowLevelBinTree in) {
        if (rthread)
            importAddRightLeaf(in);
        else
            importAddRightComp(in);
    };
    /**
     * Adds a node to the left of this one.
     */
    public final void importAddLeft(LowLevelBinTree in) {
        if (lthread)
            importAddLeftLeaf(in);
        else
            importAddLeftComp(in);
    };
    /**
     * Places a copy of the left subtree of this node in <code>out</code>.
     */
    public final void copyLeft(LowLevelBinTree out) {
        if (!lthread)
            copyLeftComp(out);
    };
    /**
     * Returns the data in this node.
     */
    public abstract Meta getNode();
    /**
     * Performs an inorder traversal, executing the Callback with each node visited.
     */
    public final void inOrder(LowLevelBinTree tStop, Callback inClass, int in) {
        LowLevelBinTree t = this;
        boolean done = false;
        
        while (!done)
            if (!(t.lThread())) {
            t = t.left();
            } else {
            inClass.exeCall(in, t.getNode());
            while ((t.rThread()) && (!done)) {
                t = t.right();
                inClass.exeCall(in, t.getNode());
                if (t == tStop)
                    done = true;
            }
            if (t == tStop)
                done = true;
            t = t.right();
            }
    };
    /**
     * Performs a preorder traversal, executing the Callback with each node visited.
     */
    public final void preOrder(LowLevelBinTree tStop, Callback inClass, int in) {
        LowLevelBinTree t = this;
        
        while (tStop.rThread())
            tStop = tStop.right();
        tStop = tStop.right();
        while (t != tStop) {
            inClass.exeCall(in, t.getNode());
            if (t.lThread() == false)
                t = t.left();
            else {
                while (t.rThread())
                    t = t.right();
                t = t.right();
            }
        }
    };
    
    /**
     * Performs a postorder traversal, executing the Callback with each node visited.
     */
    public final void postOrder(LowLevelBinTree tStop, Callback inClass, int in) {
        /* Not Implemented Yet. */
    };
    /**
     * Copies this tree to the right of <code>out</code>.
     */
    public final void pasteRight(LowLevelBinTree out) {
        LowLevelBinTree src = this;
        LowLevelBinTree dest = out;
        boolean done = false;
        
        while (!done) {
            dest.importAddRight((LowLevelBinTree) src.copyNode());
            dest = dest.right();
            src.copyLeft(dest);
            src = src.right();
            done = (src == this);
        }
    };
    /**
     * Copies this tree to the left of <code>out</code>.
     */
    public final void pasteLeft(LowLevelBinTree out) {
        LowLevelBinTree temp = this;
        LowLevelBinTree ptr = out;
        ptr.importAddLeft((LowLevelBinTree) copyNode());
        ptr = ptr.left();
        copyLeft(ptr);
        
        temp = temp.right();
        while (temp != this) {
            ptr.importAddRight((LowLevelBinTree) temp.copyNode());
            ptr = ptr.right();
            temp.copyLeft(ptr);
            temp = temp.right();
        }
        
    };
    
    /**
     * Traverses to the right until a right-thread is found.  Then returns that node.
     */
    public final LowLevelBinTree findEnd() {
        LowLevelBinTree ptr = this;
        
        if (!(ptr.rThread()))
            ptr = ptr.right();
        
        while ((!(ptr.rThread())) && (ptr != this))
            ptr = ptr.right();
        
        return (ptr);
    };
    /**
     * Disposes the current node.
     */
    public void dispose() {
        this.right = null;
        this.left = null;
    };
    /**
     * As if the binary tree is a representation of a generalized list, finds
     * the "parent" of the current node and returns it.
     */
    public final LowLevelBinTree listParent() {
        LowLevelBinTree ptr = (LowLevelBinTree) this;
        
        if (!(ptr.rThread())) {
            ptr = ptr.right();
            while ((!(ptr.rThread())) && (ptr != this))
                ptr = ptr.right();
            
            if (ptr != this)
                ptr = ptr.right();
        } else
            ptr = ptr.right();
        
        return (ptr);
    };
    
    /**
     * Connects this tree to the right of <code>out</code>.
     */
    public final void connectRight(LowLevelBinTree out) {
        LowLevelBinTree m = out;
        LowLevelBinTree s = this;
        
        LowLevelBinTree sl = s;
        while (!(sl.lThread()))
            sl = sl.left();
        
        LowLevelBinTree sr = sl.left();
        
        sr.dvSetRight(m.right());
        sr.dvSetRThread(m.rThread());
        sl.dvSetLeft(m);
        sl.dvSetLThread(true);
        
        m.dvSetRight(s);
        m.dvSetRThread(false);
        
        if (!(sr.rThread())) {
            LowLevelBinTree temp = sr.right();
            
            while (!(temp.lThread()))
                temp = temp.left();
            
            temp.dvSetLeft(sr);
        }
    }
    
    /**
     * Connects this tree to the left of <code>out</code>.
     */
    public final void connectLeft(LowLevelBinTree out) {
        LowLevelBinTree m = out;
        LowLevelBinTree s = this;
        
        LowLevelBinTree sl = s;
        while (!(sl.lThread()))
            sl = sl.left();
        
        LowLevelBinTree sr = sl.left();
        
        sr.dvSetRight(m);
        sr.dvSetRThread(true);
        sl.dvSetLeft(m.left());
        sl.dvSetLThread(m.lThread());
        
        m.dvSetLeft(s);
        m.dvSetLThread(false);
        
        if (!(sl.lThread())) {
            LowLevelBinTree temp = sl.left();
            
            while (!(temp.rThread()))
                temp = temp.right();
            
            temp.dvSetRight(sl);
        }
    }
    
    private LowLevelBinTree right;
    private LowLevelBinTree left;
    private boolean lthread;
    private boolean rthread;
    private final void dvSetRight(LowLevelBinTree in) {
        right = in;
    }
    private final void dvSetLeft(LowLevelBinTree in) {
        left = in;
    }
    private final void dvSetLThread(boolean in) {
        lthread = in;
    }
    private final void dvSetRThread(boolean in) {
        rthread = in;
    }
    private final void addRightLeaf() {
        LowLevelBinTree ptr = new StdLowLevelBinTree();
        ptr.dvSetLeft(this);
        ptr.dvSetLThread(true);
        ptr.dvSetRight(right);
        ptr.dvSetRThread(rthread);
        right = ptr;
        rthread = false;
    };
    private final void addLeftLeaf() {
        LowLevelBinTree ptr = new StdLowLevelBinTree();
        ptr.dvSetRight(this);
        ptr.dvSetRThread(true);
        ptr.dvSetLeft(left);
        ptr.dvSetLThread(lthread);
        left = ptr;
        lthread = false;
    };
    private final void addRightComp() {
        LowLevelBinTree nextT;
        LowLevelBinTree newT;
        
        nextT = right;
        while (nextT.lThread() != true)
            nextT = nextT.left();
        newT = new StdLowLevelBinTree();
        newT.dvSetRight(right);
        newT.dvSetRThread(rthread);
        right = newT;
        newT.dvSetLThread(true);
        newT.dvSetLeft(nextT.left());
        nextT.dvSetLeft(newT);
    };
    private final void addLeftComp() {
        LowLevelBinTree ptr = new StdLowLevelBinTree();
        LowLevelBinTree temp = this.left();
        while (!(temp.rThread()))
            temp = temp.right();
        ptr.dvSetLeft(this.left);
        ptr.dvSetLThread(false);
        ptr.dvSetRight(temp.right());
        ptr.dvSetRThread(true);
        temp.dvSetRight(ptr);
        this.left = ptr;
    };
    private final void importAddRightLeaf(LowLevelBinTree in) {
        LowLevelBinTree ptr = in;
        ptr.dvSetLeft(this);
        ptr.dvSetLThread(true);
        ptr.dvSetRight(right);
        ptr.dvSetRThread(rthread);
        right = ptr;
        rthread = false;
    };
    private final void importAddLeftLeaf(LowLevelBinTree in) {
        LowLevelBinTree ptr = in;
        ptr.dvSetRight(this);
        ptr.dvSetRThread(true);
        ptr.dvSetLeft(left);
        ptr.dvSetLThread(lthread);
        left = ptr;
        lthread = false;
    };
    private final void importAddRightComp(LowLevelBinTree in) {
        LowLevelBinTree nextT;
        LowLevelBinTree newT;
        
        nextT = right;
        while (nextT.lThread() != true)
            nextT = nextT.left();
        newT = in;
        newT.dvSetRight(right);
        newT.dvSetRThread(rthread);
        right = newT;
        newT.dvSetLThread(true);
        newT.dvSetLeft(nextT.left());
        nextT.dvSetLeft(newT);
    };
    private final void importAddLeftComp(LowLevelBinTree in) {
        LowLevelBinTree ptr = in;
        LowLevelBinTree temp = this.left;
        while (!(temp.rThread()))
            temp = temp.right();
        ptr.dvSetLeft(this.left);
        ptr.dvSetLThread(false);
        ptr.dvSetRight(temp.right());
        ptr.dvSetRThread(true);
        temp.dvSetRight(ptr);
        this.left = ptr;
    };
    private final void pruneLeftComp() {
        LowLevelBinTree temp;
        LowLevelBinTree ptr;
        LowLevelBinTree delTemp;
        
        temp = this;
        while (!(temp.lThread()))
            temp = temp.left();
        temp = temp.left();
        
        ptr = this.left;
        while (ptr != this) {
            while (!(ptr.lThread()))
                ptr = ptr.left();
            
            while ((ptr.rThread()) && (ptr.right() != this)) {
                delTemp = ptr;
                ptr = ptr.right();
                delTemp.dispose();
            }
            
            delTemp = ptr;
            ptr = ptr.right();
            delTemp.dispose();
        }
        
        lthread = true;
        left = temp;
    };
    private final void copyLeftComp(LowLevelBinTree dest) {
        LowLevelBinTree t = this.left;
        dest.importAddLeft((LowLevelBinTree) t.copyNode());
        dest = dest.left();
        while (t != this) {
            while (!(t.lThread())) {
                t = t.left();
                dest.importAddLeft((LowLevelBinTree) t.copyNode());
                dest = dest.left();
            }
            while ((t.rThread()) && (t.right() != this)) {
                t = t.right();
                dest = dest.right();
            }
            t = t.right();
            if (t != this) {
                dest.importAddRight((LowLevelBinTree) t.copyNode());
                dest = dest.right();
            }
        }
    };
    
    /**
     * Reads serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        try {
            VersionBuffer myv = (VersionBuffer) (in.readObject());
            VersionBuffer.chkNul(myv);
            left = (LowLevelBinTree) (myv.getProperty("Left"));
            right = (LowLevelBinTree) (myv.getProperty("Right"));
            lthread = myv.getBoolean("lthread");
            rthread = myv.getBoolean("rthread");
        } catch (ClassCastException e) {
            throw (new DataFormatException(e));
        }
    }
    
    /**
     * Writes serial data.
     * @serialData TBD.
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);
        
        super.writeExternal(out);
        if (left != null)
            myv.setProperty("Left", left);
        if (right != null)
            myv.setProperty("Right", right);
        myv.setBoolean("lthread", lthread);
        myv.setBoolean("rthread", rthread);
        out.writeObject(myv);
    }
    
};
