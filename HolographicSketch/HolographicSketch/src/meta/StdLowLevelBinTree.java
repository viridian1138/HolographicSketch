



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
 * StdLowLevelBinTree is the standard LowLevelBinTree instance created by LowLevelBinTree.
 * For more information, see {@link LowLevelBinTree}.  This is an internal structure
 * to LowLevelBinTree, and you should not be using it directly unless you are making
 * modifications to {@link LowLevelBinTree} or {@link HighLevelBinTree}.
 * This class is public only to make it serializable (at some future point).
 * @author Thorn Green
 */
public class StdLowLevelBinTree extends LowLevelBinTree {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (StdLowLevelBinTree.class).getName().hashCode() + "v3/98A".hashCode();
	
    /**
     * Returns the object stored in the node.
     */
    public Meta getNode() {
        return (this.data);
    };
    /**
     * Sets the object stored in the node.
     */
    public void setNode(Meta input) {
        this.data = input;
    };
    /**
     * Sets the CopyMode for the node.
     */
    public void setCopyMode(int copy) {
        this.thisCopyMode = copy;
    };
    /**
     * Gets the CopyMode for the node.
     */
    public int getCopyMode() {
        return (this.thisCopyMode);
    };
    /**
     * Sets the CopyInfoMode for the node.
     */
    public void setCopyInfoMode(int copy) { throw( new UndefinedOperation() );
    };
    /**
     * Gets the CopyInfoMode for the node.
     */
    public int getCopyInfoMode() { throw( new UndefinedOperation() );
    };
    /**
     * Copies the node according to the current copy mode.
     */
    public Meta copyNode() {
        StdLowLevelBinTree out = new StdLowLevelBinTree();
        copyDat(out);
        return (out);
    };
    /**
     * Initializes the node.
     */
    public final void iStdLow() {
        this.thisCopyMode = Meta.COPY_DO_NOTHING;
        this.data = null;
    };
    public StdLowLevelBinTree() {
        super();
        this.iStdLow();
    };
    /**
     * Erases the node according to the current delete mode.
     */
    public void dispose() {
        this.eraseDat();
    };
    
    private final void dvSetNode(Meta in) {
        data = in;
    }
    private final void dvSetCopyMode(int in) {
        thisCopyMode = in;
    }
    
    /**
     * The stored data.
     */
    protected Meta data;
    /**
     * The CopyMode.
     */
    protected int thisCopyMode;
    /**
     * Copies the stored data according to the current mode, and places the result
     * in <code>input</code>.
     */
    protected void copyDat(StdLowLevelBinTree input) {
        try {
            if (thisCopyMode != Meta.COPY_DO_NOTHING)
                input.dvSetNode(data.exeCopy(thisCopyMode));
            else
                input.dvSetNode(data);
        } catch (OutOfMemoryError ex) {
            if (getNode() != null)
                eraseDat();
        }
        
        input.dvSetCopyMode(thisCopyMode);
        input.setEraseMode(eraseMode);
    };
    
    /**
     * Reads the node from serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        try {
            VersionBuffer myv = (VersionBuffer) (in.readObject());
            VersionBuffer.chkNul(myv);
            
            data = (Meta) (myv.getProperty("data"));
            thisCopyMode = myv.getInt("ThisCopyMode");
        } catch (ClassCastException e) {
            throw (new DataFormatException(e));
        }
    }
    
    /**
     * Writes the node to serial data.
     * @serialData TBD.
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);
        
        if (data != null)
            myv.setProperty("data", data);
        myv.setInt("ThisCopyMode", thisCopyMode);
        
        super.writeExternal(out);
        out.writeObject(myv);
    }
    
};
