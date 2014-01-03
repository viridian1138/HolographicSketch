





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
 *    | 10/22/2001            | Thorn Green (viridian_1138@yahoo.com)           | The FlexString copyString operation was very slow for small strings. | Improved the performance.
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
 * StringRec is a node used by {@link FlexString} that holds a small array of
 * characters.  This class is for use by the {@link FlexString} class only.  Do not use it
 * unless you are making direct modifications to {@link FlexString}.
 * @author Thorn Green
 */
class StringRec extends Meta {
    /**
     * The number of characters in the array.
     */
    public static final int BASE_CHARS = 96;
    /**
     * @see meta.Meta
     */
    public Meta copyNode() {
        StringRec temp = new StringRec();
        copyString(temp);
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copySub() {
        StringRec temp = new StringRec();
        copyString(temp);
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyAll() {
        StringRec temp = new StringRec();
        copyString(temp);
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public void copyNodeInfo(Meta in) {
        StringRec temp = (StringRec) in;
        copyString(temp);
    };
    /**
     * @see meta.Meta
     */
    public void copySubInfo(Meta in) {
        StringRec temp = (StringRec) in;
        copyString(temp);
    };
    /**
     * @see meta.Meta
     */
    public void copyAllInfo(Meta in) {
        StringRec temp = (StringRec) in;
        copyString(temp);
    };
    /**
     * @see meta.Meta
     */
    public void eraseNode() {
        deleteString();
    };
    /**
     * @see meta.Meta
     */
    public void eraseSub() {
        deleteString();
    };
    /**
     * @see meta.Meta
     */
    public void eraseAll() {
        deleteString();
    };
    /**
     * @see meta.Meta
     */
    public void wake() {};
    /**
     * Gets the character at a particular "physical" index.  Note: the "+1" is a
     * kluge to support early versions of Apple MRJ (which had bugs).
     */
    public final char getPhysicalChar(int index) {
        char mc[] = myRec;
        char idc = mc[index + 1];
        return (idc);
    };
    /**
     * Sets the character at a particular "physical" index.  Note: the "+1" is a
     * kluge to support early versions of Apple MRJ (which had bugs).
     */
    public final void setPhysicalChar(int index, char inChar) {
        char mrec[] = myRec;
        mrec[index + 1] = inChar;
    };
    /**
     * Exports the contained array.  This is used by {@link FlexString} when it
     * strokes characters to a display device.
     */
    public final char[] exportArray() {
        char mc[] = myRec;
        return (mc);
    };
    /**
     * Deletes the node.
     */
    public final void deleteString() { /* delete( this ); */
    };
    /**
     * Copies the contents of the array to the StringRec <code>out</code>.
     */
    public final void copyString(StringRec out) {
                /*
                 * This is the older, and somewhat inefficient version of copyString().
                 * This will be kept around in case the new version goes haywire.
                 */
                /* int count;
                char[] OutRec = out.exportArray();
                for( count = 0 ; count < ( BaseChars + 1 ) ; ++count )
                        OutRec[ count ] = MyRec[ count ]; */
        
                /*
                 * The line below is more efficient than the code above (commented out) because the copy is
                 * performed in machine language.
                 */
        System.arraycopy(myRec, 0, out.exportArray(), 0, BASE_CHARS + 1);
    };
    
    /**
     * Copies part of the string from one StringRec into another.
     * Don't know why I have to add one to the length in the code for
     * this routine, but it seems to fix obvious bugs.
     */
    public final void copyRegion(int start_loc, int length, StringRec out) {
        System.arraycopy(myRec, start_loc, out.exportArray(), start_loc, length + 1);
    }
    
    private final char myRec[] = new char[BASE_CHARS + 1];
};
