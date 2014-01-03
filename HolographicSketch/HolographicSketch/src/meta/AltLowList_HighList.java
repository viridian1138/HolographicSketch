



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
import java.io.Externalizable;

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
 * This is a {@link LowLevelList} node containing a {@link HighLevelList}.  This is useful if one
 * wishes to create a list of lists (perhaps for a simple sparse matrix).
 * @author Thorn Green
 */
public class AltLowList_HighList extends LowLevelList implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (AltLowList_HighList.class).getName().hashCode() + "v3/98A".hashCode();
	
    /**
     * Returns the HighLevelList.
     */
    public Meta getNode() {
        return (myRec);
    };
    /**
     * This is an undefined operation.  Do not use.
     */
    public void setNode(Meta input) { throw( new UndefinedOperation() );
    };
    /**
     * This is an undefined operation.  Do not use.
     */
    public void setCopyMode(int copy) { throw( new UndefinedOperation() );
    };
    /**
     * This is an undefined operation.  Do not use.
     */
    public int getCopyMode() { throw( new UndefinedOperation() );
    };
    /**
     * Sets the CopyInfoMode for this node.
     */
    public void setCopyInfoMode(int copy) {
        this.copyInfoMode = copy;
    };
    /**
     * Gets the CopyInfoMode for this node.
     */
    public int getCopyInfoMode() {
        return (this.copyInfoMode);
    };
    /**
     * Copies the node according to the current CopyInfoMode.
     * @return The copy.
     */
    public Meta copyNode() {
        AltLowList_HighList temp = new AltLowList_HighList();
        this.copyDat(temp);
        temp.setHead(true);
        return (temp);
    };
    /**
     * Initializes the structure.
     */
    public void iAltHigh() {
        this.copyInfoMode = Meta.COPY_DATA_INFO;
    };
    public AltLowList_HighList() {
        super();
        this.iAltHigh();
    };
    /**
     * Disposes the structure according to the current EraseMode.
     */
    public void dispose() {
        this.eraseDat();
    };
    
    /**
     * Reads serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        try {
            VersionBuffer myv = (VersionBuffer) (in.readObject());
            VersionBuffer.chkNul(myv);
            
            myRec = (HighLevelList) (myv.getProperty("MyRec"));
            VersionBuffer.chkNul(myRec);
            copyInfoMode = myv.getInt("ThisCopyInfoMode");
        } catch (ClassCastException e) {
            throw (new DataFormatException(e));
        }
    }
    
    /**
     * Writes serial data.
     * <P>
     * @serialData TBD.
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);
        
        myv.setProperty("MyRec", myRec);
        myv.setInt("ThisCopyInfoMode", copyInfoMode);
        
        super.writeExternal(out);
        out.writeObject(myv);
    }
    
    /**
     * The HighLevelList stored in the node.
     */
    protected HighLevelList myRec = new HighLevelList();
    /**
     * The CopyInfoMode for the node.
     */
    protected int copyInfoMode;
    /**
     * Copies to the parameter <code>input</code> using the current CopyInfoMode.
     */
    protected void copyDat(AltLowList_HighList input) {
        if (copyInfoMode != Meta.COPY_DATA_INFO)
            myRec.exeCopyInfo(copyInfoMode, input.dvGetMyRec());
        
                /* For future exception handling purposes, it's very important that things happen
                        in this order. */
        
        input.dvSetCopyInfoMode(copyInfoMode);
        input.dvSetEraseMode(eraseMode);
    };
    
    private final HighLevelList dvGetMyRec() {
        return (myRec);
    }
    private final void dvSetCopyInfoMode(int in) {
        copyInfoMode = in;
    }
    private final void dvSetEraseMode(int in) {
        eraseMode = in;
    }
    
};
