




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
 *    | 03/21/2005            | Thorn Green (viridian_1138@yahoo.com)           | Improve Performance                                                  | Insert final keyword on insert methods.
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
 * The LowLevelType class supplies code for creating linked low level structures to be
 * used in high level structures.  As
 * <A HREF="package.html">An Introduction to Meta</A> states, there are two types of data
 * structures: high level and low level.  The difference is that low level structures do not
 * have a concept of "empty".  One reason why low level structures are needed is to represent
 * the nodes contained in high level structures.  LowLevelType is an abstract class which contains
 * important "glue" code for implementing such nodes.  It is not intended for other kinds of low
 * level data structures.  LowLevelType can be used to support both standard and alt-nodes.<P>
 *
 * Two important classes that use LowLevelType are {@link LowLevelList} and {@link LowLevelBinTree}.
 * @author Thorn Green
 */
public abstract class LowLevelType extends Meta implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (LowLevelType.class).getName().hashCode() + "v3/98A".hashCode();
	
    /**
     * @see meta.Meta
     */
    public abstract void wake();
    /**
     * Returns the data stored in the node.
     */
    public abstract Meta getNode();
    /**
     * Stores data in a particular node.
     */
    public abstract void setNode(final Meta input);
    /**
     * Sets the CopyMode of a particular node, if supported.
     */
    public abstract void setCopyMode(final int copyMode);
    /**
     * Sets the CopyInfoMode of a particular node, if supported.
     */
    public abstract void setCopyInfoMode(final int copyInfoMode);
    /**
     * Sets the EraseMode of a particular node.
     */
    public final void setEraseMode(int eraseMode) {
        this.eraseMode = eraseMode;
    };
    /**
     * Returns the CopyMode of a particular node, if supported.
     */
    public abstract int getCopyMode();
    /**
     * Returns the CopyInfoMode of a particular node, if supported.
     */
    public abstract int getCopyInfoMode();
    /**
     * Returns the EraseMode of a particular node.
     */
    public final int getEraseMode() {
        return (this.eraseMode);
    };
    
    /**
     * Initializes the LowLevelType.
     */
    public final void iLowLevelType() {
        this.eraseMode = Meta.WAKE;
    };
    public LowLevelType() {
        this.iLowLevelType();
    };
    
    /**
     * Reads serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        try {
            VersionBuffer myv = (VersionBuffer) (in.readObject());
            VersionBuffer.chkNul(myv);
            
            eraseMode = myv.getInt("ThisEraseMode");
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
        
        myv.setInt("ThisEraseMode", eraseMode);
        
        out.writeObject(myv);
    }
    
    /**
     * The EraseMode of the node.
     */
    protected int eraseMode;
    
    /**
     * Erases a node's data according to its current EraseMode.
     */
    protected final void eraseDat() {
        Meta data = getNode();
        
        if (eraseMode != Meta.WAKE)
            data.exeErase(eraseMode);
        
    };
    
};
