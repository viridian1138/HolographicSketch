



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
 *    | 09/06/2002            | Thorn Green (viridian_1138@yahoo.com)           | Needed to add a method to improve the BlueToh solver.                | Added the method.
 *    | 08/07/2004            | Thorn Green (viridian_1138@yahoo.com)           | Establish baseline for all changes in the last year.                 | Establish baseline for all changes in the last year.
 *    | 03/21/2005            | Thorn Green (viridian_1138@yahoo.com)           | Improve Performance.                                                 | Added final keyword to insert methods.
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
 * LowLevelList provides structure and code for the implementation of
 * {@link HighLevelList}.  For more information, see {@link HighLevelList}.  LowLevelList
 * is abstract, but creates instances of {@link StdLowLevelList}.  Other subclasses
 * of LowLevelList can also be used.  The procedure for doing this is beyond the scope
 * of the description here.  One should not be directly creating LowLevelList structures.
 * Instead, use {@link HighLevelList} for typical applications in which a linked list
 * is needed.
 * @author Thorn Green
 */
public abstract class LowLevelList extends LowLevelType implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (LowLevelList.class).getName().hashCode() + "v3/98A".hashCode();
	
    /**
     * @see meta.Meta
     */
    public abstract Meta copyNode();
    /**
     * @see meta.Meta
     */
    public Meta copySub() {
        LowLevelList inCopy = (LowLevelList) this;
        LowLevelList outCopy = null;
        LowLevelList temp = null;
        
        outCopy = (LowLevelList) inCopy.copyNode();
        
        while (!(inCopy.right().getHead())) {
            inCopy = inCopy.right();
            temp = (LowLevelList) inCopy.copyNode();
            temp.setHead(false);
            outCopy.insRight(temp);
            outCopy = outCopy.right();
        }
        
        return (outCopy.right());
    };
    /**
     * @see meta.Meta
     */
    public Meta copyAll() {
        LowLevelList inCopy = (LowLevelList) this;
        LowLevelList stCopy = inCopy.searchHead();
        LowLevelList goCopy = stCopy;
        LowLevelList outCopy = null;
        LowLevelList temp = null;
        
        outCopy = (LowLevelList) goCopy.copyNode();
        
        while (goCopy.right() != stCopy) {
            goCopy = goCopy.right();
            temp = (LowLevelList) goCopy.copyNode();
            temp.setHead(false);
            outCopy.insRight(temp);
            outCopy = outCopy.right();
        }
        
        while (goCopy != inCopy) {
            goCopy = goCopy.right();
            outCopy = outCopy.right();
        }
        
        return (outCopy);
    };
    /**
     * @see meta.Meta
     */
    public void eraseNode() {
        if (this.getHead())
            this.right().setHead(true);
        this.left().dvSetRight(this.right());
        this.right().dvSetLeft(this.left());
        this.dispose();
    };
    /**
     * @see meta.Meta
     */
    public void eraseSub() {
        LowLevelList temp = this;
        LowLevelList next;
        
        if (this.getHead())
            this.eraseAll();
        else {
            while (!temp.getHead()) {
                next = temp.right();
                temp.disconnect();
                temp.dispose();
                temp = next;
            }
        }
    };
    /**
     * @see meta.Meta
     */
    public void eraseAll() {
        LowLevelList next;
        LowLevelList temp = this.right();
        
        while (temp != this) {
            next = temp.right();
            temp.dispose();
            temp = next;
        }
        
        this.dispose();
    };
    /**
     * @see meta.Meta
     */
    public void wake() {};
    /**
     * Returns the node to the right of this node.
     */
    public final LowLevelList right() {
        return (this.right);
    };
    /**
     * Returns the node to the left of this node.
     */
    public final LowLevelList left() {
        return (this.left);
    };
    /**
     * Initializes the list.
     */
    public final void iLowLevelList() {
        this.setHead(true);
        this.dvSetRight(this);
        this.left = this;
    };
    public LowLevelList() {
        super();
        this.iLowLevelList();
    };
    /**
     * Inserts the data <code>in</code> to the left of the node
     * on which this method is called.
     */
    public final void insertLeft(final Meta in) {
        final LowLevelList temp = new StdLowLevelList();
        temp.setHead(this.head);
        this.head = false;
        temp.setNode(in);
        this.insLeft(temp);
    };
    /**
     * Inserts the data <code>in</code> to the right of the node
     * on which this method is called.
     */
    public final void insertRight(final Meta in) {
        final LowLevelList temp = new StdLowLevelList();
        temp.setHead(false);
        temp.setNode(in);
        this.insRight(temp);
    };
    /**
     * Inserts the node <code>in</code> to the left of the node
     * on which this method is called.
     */
    public final void importInsertLeft(final LowLevelList in) {
        final LowLevelList temp = in;
        temp.setHead(this.head);
        this.head = false;
        this.insLeft(temp);
    };
    /**
     * Inserts the node <code>in</code> to the left of the node
     * on which this method is called.
     */
    public final void importInsertRight(final LowLevelList in) {
        final LowLevelList temp = in;
        temp.setHead(false);
        this.insRight(temp);
    };
    /**
     * Disconnects this node from its neighbors.
     */
    public final void disconnect() {
        if (this.getHead())
            this.right().setHead(true);
        this.left().dvSetRight(this.right());
        this.right().dvSetLeft(this.left());
        this.right = this;
        this.left = this;
        this.head = true;
    };
    
    /**
     * Disposes the node.
     */
    public void dispose() {
        this.right = null;
        this.left = null;
    };
    /**
     * Returns the data contained in the node.
     */
    public abstract Meta getNode();
    /**
     * Gets whether this node is the head.
     */
    public final boolean getHead() {
        return (this.head);
    };
    /**
     * Sets whether this node is the head.
     */
    public final void setHead(final boolean inHead) {
        this.head = inHead;
    };
    /**
     * Returns the head of the list.
     */
    public final LowLevelList searchHead() {
        LowLevelList temp = this;
        while (!(temp.getHead())) {
            temp = temp.right();
        }
        return (temp);
    };
    
    /**
     * Returns whether the list is a single-node list.
     */
    public boolean isSingleNode() {
        return (this == this.right());
    }
    
    /**
     * Reads serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        try {
            VersionBuffer myv = (VersionBuffer) (in.readObject());
            VersionBuffer.chkNul(myv);
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
        out.writeObject(myv);
    }
    
    private transient boolean head;
    private transient LowLevelList right;
    private transient LowLevelList left;
    private final void dvSetRight(final LowLevelList in) {
        right = in;
    }
    private final void dvSetLeft(final LowLevelList in) {
        left = in;
    }
    private final void insRight(final LowLevelList temp) {
        temp.dvSetRight(this.right());
        temp.dvSetLeft(this);
        this.right().dvSetLeft(temp);
        this.right = temp;
    };
    private final void insLeft(final LowLevelList temp) {
        temp.dvSetLeft(this.left);
        temp.dvSetRight(this);
        this.left().dvSetRight(temp);
        this.left = temp;
    };
};
