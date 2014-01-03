


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


// import java.awt.Graphics;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;


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
 *    | 11/12/2000            | Thorn Green (viridian_1138@yahoo.com)           | FlexString was not compatible with TreeSet.                          | Made FlexString implement the Comparable interface.
 *    | 08/12/2001            | Thorn Green (viridian_1138@yahoo.com)           | First-Cut at Error Handling.                                         | First-Cut at Error Handling.
 *    | 10/22/2001            | Thorn Green (viridian_1138@yahoo.com)           | The copyString operation was very slow for small strings.            | Improved the performance.
 *    | 10/23/2001            | Thorn Green (viridian_1138@yahoo.com)           | Performance enahncement.                                             | Improved some methods.
 *    | 12/06/2001            | Thorn Green (viridian_1138@yahoo.com)           | Updated docs.                                                        | Updated docs.
 *    | 01/15/2002            | Thorn Green (viridian_1138@yahoo.com)           | Looking for a bug.                                                   | Made some modifications to FlexString, and then removed them.
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

class Reader extends Object {
    public final char getPhysicalChar(int index) {
        char temp = 0;
        Meta myMeta;
        StringRec myStringRec;
        
        moveToPos(index);
        myMeta = myList.getNode();
        myStringRec = (StringRec) myMeta;
        temp = myStringRec.getPhysicalChar(index - base);
        return (temp);
    };
    public final void moveForward() {
        myList.right();
        base = base + StringRec.BASE_CHARS;
    };
    public final void moveBackward() {
        myList.left();
        base = base - StringRec.BASE_CHARS;
    };
    public final void moveToPos(int pos) {
        while ((pos < base) && (base > 0))
            moveBackward();
        while ((pos >= (base + StringRec.BASE_CHARS)) && ((base + StringRec.BASE_CHARS) < maxbase))
            moveForward();
    };
    public final void setMaxbase(int in) {
        maxbase = in;
    }
    public final void setBase(int in) {
        base = in;
    }
    public final void setMax(int in) {
        max = in;
    }
    public final HighLevelList getMyList() {
        return (myList);
    }
    
    private int max;
    private int maxbase;
    private int base;
    final HighLevelList myList = new HighLevelList();
};

/**
 *    FlexString is a class for implementing variable-length buffer-gapped character strings.  FlexString
 * uses a number of "coordinate systems" to represent its data.  This will be described in more detail later.<P>
 *
 *	FlexString was originally written for a future version of the Viper editor, and like the
 * rest of meta was originally written in C++.  Despite this, it has become indespensible for
 * a number of projects, including GeoPad.  The FlexString's buffer-gap scheme is based on text
 * editor design, related to that for the Viper editor.  Buffer-gapping has a number of performance
 * advantages during certain kinds of operations (particularly mid-insertion), and a number
 * of classes that use FlexString take advantage of this.<P>
 *
 *	The coordinate systems map the logical coordinates seen by the user into the internal
 * representation of the data structure.  The internal representation is essentially a
 * {@link HighLevelList} of StringRec nodes.  To reduce fragmentation and the number of
 * references used in the data structure, each StringRec is held in an Alt-node of type
 * AltLowList_StringRec.  For more information on Alt-nodes see the
 * {@link LowLevelList} documentation.<P>
 *
 * 	FlexString is influenced by the same references as those for the original Viper editor.
 * These references are listed in the "Works Cited" list in the ASU Honors College undergraduate
 * Honors Thesis, "Viper-- A Pascal Editor for VAX".  The Valdes reference in the "Works Cited"
 * section gives a good initial overview of the topic.  The Lancaster reference, despite the title,
 * covers the internals of one particular text editor in detail.  Access to this thesis can be
 * obtained by contacting the ASU Honors College.<P>
 *
 * @author Thorn Green
 */
public class FlexString extends Meta implements Comparable, Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (FlexString.class).getName().hashCode() + "v3/98A".hashCode();
    
    /**
     * @see meta.Meta
     */
    public Meta copyNode() {
        FlexString temp = new FlexString();
        copyString(temp);
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copySub() {
        FlexString temp = new FlexString();
        copyString(temp);
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyAll() {
        FlexString temp = new FlexString();
        copyString(temp);
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public void copyNodeInfo(Meta in) {
        FlexString temp = (FlexString) in;
        copyString(temp);
    };
    /**
     * @see meta.Meta
     */
    public void copySubInfo(Meta in) {
        FlexString temp = (FlexString) in;
        copyString(temp);
    };
    /**
     * @see meta.Meta
     */
    public void copyAllInfo(Meta in) {
        FlexString temp = (FlexString) in;
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
     * Similar to the <code>strcpy(</code> routine in the C language.
     */
    public final void strcpy(FlexString source) {
        source.copyString(this);
    };
    
    /**
     * Copies this to the string in dest.
     * <P>
     * <B>In:</B> Reference to dest string.<BR>
     * <B>Out:</B> Dest string modified.<BR>
     * <B>Pre:</B> This, dest valid.<BR>
     * <B>Post:</B> The string in dest will be replaced by the string in this.<BR>
     * @author Thorn Green
     */
    public final void copyString(FlexString dest) {
                /*
                 * This is the older, and somewhat inefficient version of copyString().
                 * This will be kept around in case the new version goes haywire.
                 */
                /* dest.dvGetMyList().eraseAllInfo();
                MyList.copyAllInfo( ( dest.dvGetMyList() ) );
                dest.dvSetMax( max );
                dest.dvSetMaxbase( maxbase );
                dest.dvSetBase( base );
                dest.dvSetInsertPoint( InsertPoint ); */
        
                /*
                 * This new version of copyString() performs a number of low-level operations
                 * to maximize execution performance.  Handle with care.  Code here partially
                 * taken from the copyAll() method in LowLevelList. If this were C++, one
                 * could allocate several HighLevelList objects on the stack and do the entire
                 * thing at high-level.  However, Java does not have stack allocation and the
                 * author does not want the cost of GC-ing heap-allocated HighLevelLists.  Hence,
                 * the implementation here is low-level instead.
                 */
        dest.dvGetMyList().eraseAllInfo();
        
        LowLevelList inCopy = myList.exportNode();
        LowLevelList stCopy = inCopy.searchHead();
        LowLevelList goCopy = stCopy;
        LowLevelList outCopy = null;
        LowLevelList temp = null;
        
        int tmp_base = 0;
        int tmp_maxbase = StringRec.BASE_CHARS;
        
        outCopy = replicateStringRecNode(goCopy, tmp_base, tmp_maxbase);
        dest.dvGetMyList().importInsertRight(outCopy);
        
        while (goCopy.right() != stCopy) {
            tmp_base += StringRec.BASE_CHARS;
            tmp_maxbase += StringRec.BASE_CHARS;
            
            goCopy = goCopy.right();
            temp = replicateStringRecNode(goCopy, tmp_base, tmp_maxbase);
            outCopy.importInsertRight(temp);
            outCopy = outCopy.right();
        }
        
        goCopy = goCopy.right();
        HighLevelList dlist = dest.dvGetMyList();
        while (goCopy != inCopy) {
            goCopy = goCopy.right();
            dlist.right();
        }
        
        dest.dvSetMax(max);
        dest.dvSetMaxbase(maxbase);
        dest.dvSetBase(base);
        dest.dvSetInsertPoint(insertPoint);
    };
    
    /**
     * Replicates a StringRec for the purposes of supporting the copyString() method.
     * Copies nodes differently depending on where the InsertPoint is.
     */
    protected final LowLevelList replicateStringRecNode(LowLevelList in, int tmp_base, int tmp_maxbase) {
        int rstPoint = insertPoint + gapLength();
        
        if ((insertPoint >= tmp_maxbase) || (rstPoint <= tmp_base)) {
            return ((LowLevelList) (in.copyNode()));
        } else {
            AltLowList_StringRec st = new AltLowList_StringRec();
            st.setCopyInfoMode(Meta.COPY_ALL_INFO);
            st.setEraseMode(Meta.WAKE);
            
            StringRec sti = (StringRec) (in.getNode());
            StringRec sto = (StringRec) (st.getNode());
            
            if ((insertPoint > tmp_base) && (insertPoint <= tmp_maxbase)) {
                sti.copyRegion(0, insertPoint - tmp_base, sto);
            }
            
            if ((rstPoint >= tmp_base) && (rstPoint < tmp_maxbase)) {
                sti.copyRegion(rstPoint - tmp_base, tmp_maxbase - rstPoint, sto);
            }
            
            return (st);
        }
    }
    
    /**
     * Import into this the contents of the Java-style string Instr.
     * <P>
     * <B>In:</B> Reference to Instr.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> The contents of this will be replaced by the contents of Instr.<BR>
     * @author Thorn Green
     */
    public final void importString(String instr) {
        char inChar;
        int count = 0;
        
        clearString();
        for (count = 0; count < (instr.length()); ++count) {
            inChar = instr.charAt(count);
            setPhysicalChar(count, inChar);
        }
        
        max = count;
        insertPoint = count;
    };
    
    /**
     * Exports this string into the C-style string Instr.
     * <P>
     * <B>In:</B> Reference to Instr.<BR>
     * <B>Out:</B> Instr modified.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> The contents of Instr will be replaced by the contents of this.<BR>
     * @author Thorn Green
     */
    public final void exportString(char[] instr) {
        char inChar;
        int count;
        
        for (count = 0; count < max; ++count) {
            inChar = getChar(count);
            instr[count] = inChar;
        }
        
        instr[max] = 0;
    };
    
    /**
     * Exports this string into a Java-style string.
     * <P>
     * <B>In:</B> <BR>
     * <B>Out:</B> Java string returned.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> A Java string with the correct contents will be returned.<BR>
     * @author Thorn Green
     */
    public final String exportString() {
        boolean done = false;
        Meta myMeta;
        int rstPoint = insertPoint + gapLength();
        StringRec myStringRec = (StringRec) myList.getNode();
        String out = "";
        
        if (max > 0) {
            moveToPos(0);
            while (!done) {
                myMeta = myList.getNode();
                done = !((base + StringRec.BASE_CHARS) < insertPoint);
                myStringRec = (StringRec) myMeta;
                if ((base + StringRec.BASE_CHARS) <= insertPoint) {
                    out = out + new String(myStringRec.exportArray(), 1, StringRec.BASE_CHARS);
                } else {
                    out = out + new String(myStringRec.exportArray(), 1, insertPoint - base);
                }
                
                if ((base + StringRec.BASE_CHARS) < insertPoint)
                    moveForward();
            }
            
            if (insertPoint < max) {
                moveToPos(rstPoint);
                myMeta = myList.getNode();
                myStringRec = (StringRec) myMeta;
                out =
                        out
                        + new String(
                        myStringRec.exportArray(),
                        rstPoint - base + 1,
                        StringRec.BASE_CHARS - (rstPoint - base));
                done = !((base + StringRec.BASE_CHARS) < maxbase);
                
                while (!done) {
                    moveForward();
                    myMeta = myList.getNode();
                    myStringRec = (StringRec) myMeta;
                    out = out + new String(myStringRec.exportArray(), 1, StringRec.BASE_CHARS);
                    
                    done = !((base + StringRec.BASE_CHARS) < maxbase);
                }
            }
        }
        
        return (out);
    };
    
    /* void readString( FILE *infile ); */
    
    /**
     * Writes the contents of this string to out.
     * <P>
     * <B>In:</B> <BR>
     * <B>Out:</B> PrintStream out.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> Contents of this string will be printed.<BR>
     * @author Thorn Green
     */
    public final void writeString( PrintStream out ) {
        int count;
        
        for (count = 0; count < max; count++) {
            char temp = getChar(count);
            out.print(temp);
        }
    }
    
    /**
     * Writes the contents of this string to standard out.
     * <P>
     * <B>In:</B> <BR>
     * <B>Out:</B> To standard out<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> Contents of this string will be printed.<BR>
     * @author Thorn Green
     */
    public final void writeString() {
        writeString( System.out );
    }
    
    /**
     * Writes the contents of this string to out.
     * <P>
     * <B>In:</B> <BR>
     * <B>Out:</B> PrintStream out.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> Contents of this string will be printed, followed by a
     * carriage return.<BR>
     * @author Thorn Green
     */
    public final void writelnString( PrintStream out ) {
        writeString( out );
        out.println("");
    };
    
    /**
     * Writes the contents of this string to standard out.
     * <P>
     * <B>In:</B> <BR>
     * <B>Out:</B> To standard out.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> Contents of this string will be printed, followed by a
     * carriage return.<BR>
     * @author Thorn Green
     */
    public final void writelnString() {
        writelnString( System.out );
    }
    
    /**
     * Appends this string to the end of OutStr.
     * <P>
     * <B>In:</B> Reference to OutStr.<BR>
     * <B>Out:</B> OutStr modified.<BR>
     * <B>Pre:</B> This, OutStr valid.<BR>
     * <B>Post:</B> Contents of this will be appended onto the end of OutStr.<BR>
     * @author Thorn Green
     */
    public final void appendString(FlexString outStr) {
        int count;
        int temp = outStr.strlen();
        
        outStr.setInsertPoint(temp);
        for (count = 0; count < max; ++count) {
            char inChar = getChar(count);
            outStr.setPhysicalChar(temp, inChar);
            temp++;
        }
        
        outStr.dvSetMax(temp);
        outStr.dvSetInsertPoint(temp);
    };
    
    /**
     * Copies index number of characters from the left this into Outstr.
     * If index is greater than the length of this, the string repeats itself.
     * <P>
     * <B>In:</B> Index, reference to Outstr.<BR>
     * <B>Out:</B> Outstr modified.<BR>
     * <B>Pre:</B> This, Outstr valid.  If this is empty, index is not greater than zero.<BR>
     * <B>Post:</B> Index number of characters from the left of this are copied into Outstr.<BR>
     * @author Thorn Green
     */
    public final void leftString(int index, FlexString outstr) {
        char inChar;
        int temp;
        int count;
        
        if (Meta.SAFETY) {
            if ((max == 0) && (index > 0)) {
                throw( new UndefinedOperation() );
            }
        }
        
        outstr.clearString();
        temp = 0;
        for (count = 0; count < index; ++count) {
            if (temp >= max)
                temp = 0;
            inChar = getChar(temp);
            outstr.setPhysicalChar(count, inChar);
            temp++;
        }
        
        outstr.dvSetMax(index);
        outstr.dvSetInsertPoint(index);
    };
    
    /**
     * Copies index number of characters from the right of this into Outstr.
     * If index is greater than the length of this, then the string will repeat itself.
     * <P>
     * <B>In:</B> Index, reference to Outstr.<BR>
     * <B>Out:</B> Outstr modified.<BR>
     * <B>Pre:</B> This, Outstr valid.  If this is empty, index is not greater than zero.<BR>
     * <B>Post:</B> Index number of characters from the right of this will be copies into Outstr.<BR>
     * @author Thorn Green
     */
    public final void rightString(int index, FlexString outstr) {
        char inChar;
        int temp;
        int count;
        
        if (Meta.SAFETY) {
            if ((max == 0) && (index > 0)) {
                throw( new UndefinedOperation() );
            }
        }
        
        outstr.clearString();
        if (max >= index)
            temp = max - index;
        else
            temp = (max - 1) - ((index - max - 1) % max);
        
        for (count = 0; count < index; ++count) {
            if (temp >= max)
                temp = 0;
            inChar = getChar(temp);
            outstr.setPhysicalChar(count, inChar);
            temp++;
        }
        
        outstr.dvSetMax(index);
        outstr.dvSetInsertPoint(index);
    };
    
    /**
     * Takes the substring of this from index1 to index2, and places it in OutStr.
     * <P>
     * <B>In:</B> Index1, index2, reference to OutStr.<BR>
     * <B>Out:</B> OutStr modified.<BR>
     * <B>Pre:</B> This, OutStr valid.  Indices are valid on this string.<BR>
     * <B>Post:</B> The string in OutStr will be replaced by the substring of this.<BR>
     * @author Thorn Green
     */
    public final void midString(int index1, int index2, FlexString outStr) {
        int count;
        int temp = 0;
        
        outStr.clearString();
        for (count = index1; count <= index2; count++) {
            char inChar = getChar(count);
            outStr.setPhysicalChar(temp, inChar);
            temp++;
        }
        
        outStr.dvSetMax(temp);
        outStr.dvSetInsertPoint(temp);
    };
    
    /**
     * Returns the character at <code>index</code>.
     */
    public final char getChar(final int index) {
        char temp = 0;
        
        if (index < max) {
            temp = getPhysicalChar(logicalToPhysical(index));
        } else if ((index > max) && (Meta.SAFETY)) {
            throw( new UndefinedOperation() );
        }
        
        return (temp);
    };
    
    /**
     * Sets the character at <code>index</code>.
     */
    public final void setChar(final int index, final char inChar) {
        if (index < max) {
            setPhysicalChar(logicalToPhysical(index), inChar);
        } else {
            int count;
            
            setInsertPoint(max);
            for (count = max; count < index; count++)
                setPhysicalChar(count, ' ');
            setPhysicalChar(index, inChar);
            max = index + 1;
            insertPoint = index + 1;
        }
    };
    
    /**
     * Returns the length of the string.
     */
    public final int strlen() {
        return (max);
    };
    
    /**
     * Compares this to Str2, and returns the index of the first character which
     * doesn't match, or an index past the end of the string.
     * <P>
     * <B>In:</B> Reference to Str2.<BR>
     * <B>Out:</B> Index value returned.<BR>
     * <B>Pre:</B> This, Str2 valid.<BR>
     * <B>Post:</B> If this is not identical to Str2, the index of the first non-identical character
     * will be returned.<BR>
     * @author Thorn Green
     */
    public final int strcmp(FlexString Str2) {
        char inChar1;
        char inChar2;
        int index = 0;
        
        inChar1 = this.getChar(index);
        inChar2 = Str2.getChar(index);
        while ((inChar1 == inChar2) && (inChar1 != 0)) {
            index++;
            inChar1 = this.getChar(index);
            inChar2 = Str2.getChar(index);
        }
        
        return (index);
    };
    
    /**
     * Compares this to Str2, and returns the index of the first character which
     * doesn't match, or an index past the end of the string.
     * <P>
     * <B>In:</B> Reference to Str2.<BR>
     * <B>Out:</B> Index value returned.<BR>
     * <B>Pre:</B> This, Str2 valid.<BR>
     * <B>Post:</B> If this is not identical to Str2, the index of the first non-identical character
     * will be returned.<BR>
     * @author Thorn Green
     */
    public final int istrcmp(char[] str2) {
        char inChar1;
        char inChar2;
        int index = 0;
        
        inChar1 = this.getChar(index);
        inChar2 = str2[index];
        while ((inChar1 == inChar2) && (inChar1 != 0)) {
            index++;
            inChar1 = this.getChar(index);
            inChar2 = str2[index];
        }
        
        return (index);
    };
    
    /**
     * Returns the first character in this which matches InChar.
     * <P>
     * <B>In:</B> InChar.<BR>
     * <B>Out:</B> Index value returned.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> If there is a match, the index of the first match will be returned.
     * Otherwise, an index past the end of the string will be returned.<BR>
     * @author Thorn Green
     */
    public final int strchr(char inChar) {
        char inChar1;
        int index = 0;
        
        inChar1 = this.getChar(index);
        while ((inChar1 != inChar) && (inChar1 != 0)) {
            index++;
            inChar1 = this.getChar(index);
        }
        
        return (index);
    };
    
    /**
     * Compares two strings.
     * <P>
     * <B>In:</B> Strings Str1 and Str2 to compare.<BR>
     * <B>Out:</B> Result of comparison returned.<BR>
     * <B>Pre:</B> Str1 and Str2 must be null-terminated.<BR>
     * <B>Post:</B> If Str1 > Str2 then a 1 is returned.  If Str1 = Str2 then a 0 is returned.
     * If Str1 < Str2 then a -1 is returned.<BR>
     * @author Thorn Green.
     */
    public final int stcmp(FlexString str2) {
        int temp = 0;
        int ret = 0;
        while ((this.getChar(temp) == str2.getChar(temp)) && (this.getChar(temp) != 0) && (str2.getChar(temp) != 0))
            temp++;
        
        char c1 = this.getChar(temp);
        char c2 = str2.getChar(temp);
        
        if (c1 > c2)
            ret = 1;
        if (c1 < c2)
            ret = -1;
        return (ret);
    };
    
    /**
     * Compares this object to the object ob.  Returns same values as stcmp().
     */
    public final int compareTo(Object ob) {
        return (stcmp((FlexString) ob));
    }
    
    /**
     * Compares two strings.
     * <P>
     * <B>In:</B> Strings Str1 and Str2 to compare.<BR>
     * <B>Out:</B> Result of comparison returned.<BR>
     * <B>Pre:</B> Str1 and Str2 must be null-terminated.<BR>
     * <B>Post:</B> If Str1 > Str2 then a 1 is returned.  If Str1 = Str2 then a 0 is returned.
     * If Str1 < Str2 then a -1 is returned.<BR>
     * @author Thorn Green.
     */
    public final int stcmp(String Str2) {
        int temp = 0;
        int ret = 0;
        
        int s1 = this.strlen();
        int s2 = Str2.length();
        
        if (s2 != 0) {
            while ((this.getChar(temp) == Str2.charAt(temp)) && (this.getChar(temp) != 0) && (temp < (s2 - 1)))
                temp++;
            
            char c1 = this.getChar(temp);
            char c2 = Str2.charAt(temp);
            
            if ((c1 > c2))
                ret = 1;
            
            if ((c1 < c2))
                ret = -1;
            
            if ((c1 == c2) && (s1 > s2))
                ret = 1;
            
            if ((c1 == c2) && (s1 < s2))
                ret = -1;
            
        } else {
            if (s1 > 0)
                ret = 1;
        }
        
        return (ret);
    };
    
    /**
     * Compares two strings.
     * <P>
     * <B>In:</B> Strings Str1 and Str2 to compare.<BR>
     * <B>Out:</B> Result of comparison returned.<BR>
     * <B>Pre:</B> Str1 and Str2 must be null-terminated.<BR>
     * <B>Post:</B> If Str1 > Str2 then a 1 is returned.  If Str1 = Str2 then a 0 is returned.
     * If Str1 < Str2 then a -1 is returned.<BR>
     * @author Thorn Green.
     */
    public final int istcmp(char[] str2) {
        int temp = 0;
        int ret = 0;
        while ((this.getChar(temp) == str2[temp]) && (this.getChar(temp) != 0) && (str2[temp] != 0))
            temp++;
        
        if (this.getChar(temp) > str2[temp])
            ret = 1;
        if (this.getChar(temp) < str2[temp])
            ret = -1;
        return (ret);
    };
    
    /**
     * Returns the hash code of the string.
     * <P>
     * <B>In:</B> Object to take hash code of.<BR>
     * <B>Out:</B> Hash code.<BR>
     * <B>Pre:</B> String must be null-terminated.<BR>
     * <B>Post:</B> Hash code for string will be returned.<BR>
     * @author Thorn Green.
     */
    public int hashCode() {
        int prime = 211;
        int h = 0;
        int g;
        int cnt;
        for (cnt = 0; cnt < strlen(); ++cnt) {
            h = (h << 4) + (int) (getChar(cnt));
            g = h & 0xf0000000;
            if (g != 0) {
                h = h ^ (g >> 24);
                h = h ^ g;
            }
        }
        
        int ret = (java.lang.Math.abs(h)) % prime;
        
        return (ret);
    };
    
    /**
     * Returns whether the FlexString is equal to some other FlexString.
     */
    public boolean equals(Object in) {
        boolean eq = false;
        if (in instanceof FlexString) {
            eq = this.stcmp((FlexString) in) == 0;
        }
        
        return (eq);
    }
    
    public final int baseLen(int base) {
        return (max - base);
    };
    public final char getBaseChar(int base, int index) {
        return (getChar(index + base));
    };
    
    /**
     * Determines whether the substring of this starting at base and having length less than
     * or equal to MatchStr is equal to MatchStr.
     * <P>
     * <B>In:</B> MatchStr, base.<BR>
     * <B>Out:</B> Boolean value returned.<BR>
     * <B>Pre:</B> This, MatchStr valid.  Base is less than or equal to the length of this.<BR>
     * <B>Post:</B> If the given substring of this is equal to MatchStr, a 1 is returned.
     * Otherwise, zero is returned.<BR>
     * @author Thorn Green
     */
    public final boolean baseCmp(FlexString matchStr, int base) {
        int index = 0;
        boolean done = false;
        
        while ((matchStr.getChar(index) != 0) && (!done)) {
            if (matchStr.getChar(index) != this.getBaseChar(base, index))
                done = true;
            index++;
        }
        return (!done);
    };
    
    /**
     * Determines whether the substring of this starting at base and having length less than
     * or equal to MatchStr is equal to MatchStr.
     * <P>
     * <B>In:</B> MatchStr, base.<BR>
     * <B>Out:</B> Boolean value returned.<BR>
     * <B>Pre:</B> This, MatchStr valid.  Base is less than or equal to the length of this.<BR>
     * <B>Post:</B> If the given substring of this is equal to MatchStr, a 1 is returned.
     * Otherwise, zero is returned.<BR>
     * @author Thorn Green
     */
    public final boolean iBaseCmp(String matchStr, int base) {
        int index = 0;
        boolean done = false;
        
        while ((index < matchStr.length()) && (!done)) {
            if (matchStr.charAt(index) != this.getBaseChar(base, index))
                done = true;
            index++;
        }
        return (!done);
    };
    
    public FlexString() {
        LowLevelList temp = new AltLowList_StringRec();
        myList.importInsertRight(temp);
        temp.setCopyInfoMode(Meta.COPY_ALL_INFO);
        temp.setEraseMode(Meta.WAKE);
        max = 0;
        base = 0;
        maxbase = StringRec.BASE_CHARS;
        insertPoint = 0;
    };
    public FlexString(String in) {
        LowLevelList temp = new AltLowList_StringRec();
        myList.importInsertRight(temp);
        temp.setCopyInfoMode(Meta.COPY_ALL_INFO);
        temp.setEraseMode(Meta.WAKE);
        max = 0;
        base = 0;
        maxbase = StringRec.BASE_CHARS;
        insertPoint = 0;
        importString(in);
    };
    
    /**
     * Writes serial data.
     * @serialData TBD.
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(exportString());
        out.writeInt(getInsertPoint());
    }
    
    /**
     * Reads serial data.
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        try {
            String str = (String) (in.readObject());
            VersionBuffer.chkNul(str);
            importString(str);
            setInsertPoint(in.readInt());
        } catch (ClassCastException e) {
            throw (new DataFormatException(e));
        }
    }
    
    /**
     * Disposes the string.
     */
    public void dispose() {
        myList.eraseAllInfo();
    };
    
    /**
     * Clears the current string to the empty string.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B> String modified.<BR>
     * <B>Pre:</B> This string valid.<BR>
     * <B>Post:</B> String will be set to the empty string.<BR>
     * @author Thorn Green
     */
    public final void clearString() {
        myList.searchHead();
        myList.right();
        if (!(myList.getHead()))
            myList.eraseSubInfo();
        max = 0;
        base = 0;
        maxbase = StringRec.BASE_CHARS;
        insertPoint = 0;
    };
    
    /**
     * Disposes the string.
     */
    public final void deleteString() {
        dispose();
    };
    
    /**
     * Returns the insertion point of the string.
     */
    public final int getInsertPoint() {
        return (insertPoint);
    };
    
    /**
     * Sets the insertion point of this string to index.
     * <P>
     * <B>In:</B> Index.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.  Index is less than or equal to the length of the string.<BR>
     * <B>Post:</B> The insertion point of the string will be set to index.<BR>
     * @author Thorn Green
     */
    public final void setInsertPoint(int index) {
        if (Meta.SAFETY) {
            if (index > max) {
                throw( new UndefinedOperation() );
            }
        }
        
        int inspt = insertPoint;
        if (index > inspt)
            setInsertPointUp(index);
        else if (index < inspt)
            setInsertPointDn(index);
    };
    
    /**
     * Inserts this string at the insertion point of out.
     * <P>
     * <B>In:</B> Reference to out.<BR>
     * <B>Out:</B> Out modified.<BR>
     * <B>Pre:</B> This, out valid.<BR>
     * <B>Post:</B> This string will be inserted into out.  It will be inserted at out's insertion point.<BR>
     * @author Thorn Green
     */
    public final void insertString(FlexString out) {
        char inChar;
        int cnt;
        
        for (cnt = 0; cnt < max; ++cnt) {
            inChar = getChar(cnt);
            out.insertChar(inChar);
        }
    };
    
    /**
     * Inserts a string at the current insertion point.
     * <P>
     * <B>In:</B> Reference to input string.<BR>
     * <B>Out:</B> This string modified.<BR>
     * <B>Pre:</B> This, in valid.<BR>
     * <B>Post:</B> In string will be inserted into this string.  It will be inserted at the current insertion point.<BR>
     * @author Thorn Green
     */
    public final void insertJavaString(String in) {
        char inChar;
        int cnt;
        int maxStr = in.length();
        
        for (cnt = 0; cnt < maxStr; ++cnt) {
            inChar = in.charAt(cnt);
            insertChar(inChar);
        }
    };
    
    /**
     * Deletes a number of characters after the insertion point.
     * <P>
     * <B>In:</B> Length.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.  Length is less than or equal to the number of characters after the
     * insertion point.<BR>
     * <B>Post:</B> A number of characters after the insertion point, equal to length,
     * will be deleted.<BR>
     * @author Thorn Green
     */
    public final void deleteSectionAfter(int length) {
        if (Meta.SAFETY) {
            if (length > (max - insertPoint)) {
                throw( new UndefinedOperation() );
            }
        }
        
        max = max - length;
        collectGarbage();
    };
    
    /**
     * Deletes a given number of characters before the insertion point.
     * <P>
     * <B>In:</B> Length.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.  Length is less than or equal to the position of the insertion point.<BR>
     * <B>Post:</B> Deletes a number of characters before the insertion point equal to length.<BR>
     * @author Thorn Green
     */
    public final void deleteSectionBefore(int length) {
        if (Meta.SAFETY) {
            if (length > insertPoint) {
                throw( new UndefinedOperation() );
            }
        }
        
        insertPoint = insertPoint - length;
        max = max - length;
        collectGarbage();
    };
    
    /**
     * Finds the first place in this string on or after base which matches the string
     * in Match.
     * <P>
     * <B>In:</B> Reference to Match, base.<BR>
     * <B>Out:</B> Index value returned.<BR>
     * <B>Pre:</B> This, Match valid.  Base is less than or equal to the length of this.<BR>
     * <B>Post:</B> Returns the offset index of the first match of Match on or after base.<BR>
     * @author Thorn Green
     */
    public final int findMatch(FlexString match, int base) {
        int temp = base;
        
        if ((match.strlen() > 0) && (strlen() > 0)) {
            boolean done = false;
            boolean found = false;
            while (!done) {
                if (getChar(temp) != match.getChar(0))
                    temp++;
                else {
                    found = baseCmp(match, temp);
                    if (!found)
                        temp++;
                }
                done = found || (temp >= max);
            }
        }
        
        return (temp - base);
    };
    
    /**
     * Inserts InChar at the insertion point of this string.
     * <P>
     * <B>In:</B> InChar.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> InChar will be inserted at the insertion point of this string.<BR>
     * @author Thorn Green
     */
    public final void insertChar(char inChar) {
        if (gapLength() != 0) {
            setPhysicalChar(insertPoint, inChar);
            insertPoint++;
            max++;
        } else {
            int tempStart;
            int tempBase;
            int count;
            Reader myRead = new Reader();
            
            if (insertPoint > 0)
                moveToPos(insertPoint - 1);
            else
                moveToPos(insertPoint);
            LowLevelList Temp = new AltLowList_StringRec();
            myList.importInsertRight(Temp);
            Temp.setCopyInfoMode(Meta.COPY_ALL_INFO);
            Temp.setEraseMode(Meta.WAKE);
            base = base + StringRec.BASE_CHARS;
            maxbase = maxbase + StringRec.BASE_CHARS;
            
            myRead.setMax(max);
            myRead.setMaxbase(maxbase);
            myRead.setBase(base);
            myList.copyDataPlusPtrInfo((myRead.getMyList()));
            
            tempStart = insertPoint;
            tempBase = base;
            for (count = tempStart; count < tempBase; ++count) {
                char InChar2 = myRead.getPhysicalChar(count);
                setPhysicalChar(count + StringRec.BASE_CHARS, InChar2);
            }
            
            setPhysicalChar(insertPoint, inChar);
            insertPoint++;
            max++;
        }
    };
    
    /**
     * Deletes a range of characters from this string.
     * <P>
     * <B>In:</B> Start, end of range.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.  Start, end are less than the string length.<BR>
     * <B>Post:</B> The characters from start to end, inclusive, will be deleted.<BR>
     * @author Thorn Green
     */
    public final void deleteSection(int start, int end) {
        if (end > start) {
            int point = getInsertPoint();
            int delta = end - start + 1;
            
            if ((point >= start) && (point <= (end + 1))) {
                int length = point - start;
                insertPoint = insertPoint - length;
            } else {
                int count;
                Reader myRead = new Reader();
                
                myRead.setMax(max);
                myRead.setMaxbase(maxbase);
                myRead.setBase(base);
                myList.copyDataPlusPtrInfo((myRead.getMyList()));
                
                if (point < start) {
                    for (count = start - 1; count >= point; count--)
                        setPhysicalChar(count + delta + gapLength(), myRead.getPhysicalChar(count + gapLength()));
                } else {
                    for (count = start; count < (point - delta); count++)
                        setPhysicalChar(count, myRead.getPhysicalChar(count + delta));
                    insertPoint = insertPoint - delta;
                }
            }
            
            max = max - delta;
            collectGarbage();
        }
    };
    
    /**
     * Draws the string to the current AWT graphics context.
     * <P>
     * <B>In:</B> The GC in which to draw, the position at which to draw.<BR>
     * <B>Out:</B> To the GC.<BR>
     * <B>Pre:</B> String correctly initialized.<BR>
     * <B>Post:</B> The string will be written to the GC with the current fontsize, style,
     * and color.<BR>
     * @author Thorn Green
     */
 /*   public final void drawString(Graphics g, int x, int y) {
        boolean done = false;
        Meta myMeta;
        int rstPoint = insertPoint + gapLength();
        StringRec myStringRec = (StringRec) myList.getNode();
        FontMetrics myM = g.getFontMetrics();
        
        if (max > 0) {
            moveToPos(0);
            while (!done) {
                myMeta = myList.getNode();
                done = !((base + StringRec.BASE_CHARS) < insertPoint);
                myStringRec = (StringRec) myMeta;
                
                if ((base + StringRec.BASE_CHARS) <= insertPoint) {
                    g.drawChars(myStringRec.exportArray(), 1, StringRec.BASE_CHARS, x, y);
                    x = x + myM.charsWidth(myStringRec.exportArray(), 1, StringRec.BASE_CHARS);
                } else {
                    g.drawChars(myStringRec.exportArray(), 1, insertPoint - base, x, y);
                    x = x + myM.charsWidth(myStringRec.exportArray(), 1, insertPoint - base);
                }
                
                if ((base + StringRec.BASE_CHARS) < insertPoint)
                    moveForward();
            }
            
            if (insertPoint < max) {
                moveToPos(rstPoint);
                myMeta = myList.getNode();
                myStringRec = (StringRec) myMeta;
                g.drawChars(
                        myStringRec.exportArray(),
                        rstPoint - base + 1,
                        StringRec.BASE_CHARS - (rstPoint - base),
                        x,
                        y);
                x =
                        x
                        + myM.charsWidth(
                        myStringRec.exportArray(),
                        rstPoint - base + 1,
                        StringRec.BASE_CHARS - (rstPoint - base));
                done = !((base + StringRec.BASE_CHARS) < maxbase);
                
                while (!done) {
                    moveForward();
                    myMeta = myList.getNode();
                    myStringRec = (StringRec) myMeta;
                    g.drawChars(myStringRec.exportArray(), 1, StringRec.BASE_CHARS, x, y);
                    x = x + myM.charsWidth(myStringRec.exportArray(), 1, StringRec.BASE_CHARS);
                    
                    done = !((base + StringRec.BASE_CHARS) < maxbase);
                }
            }
        }
    }; */
    
    /**
     * Draws the string to the current AWT graphics context.
     * <P>
     * <B>In:</B> The GC in which to draw, the position at which to draw.<BR>
     * <B>Out:</B> To the GC.<BR>
     * <B>Pre:</B> String correctly initialized.<BR>
     * <B>Post:</B> The string will be written to the GC with the current fontsize, style,
     * and color.<BR>
     * @author Thorn Green
     */
/*    public final int charsWidth(Graphics g) {
        boolean done = false;
        Meta myMeta;
        int rstPoint = insertPoint + gapLength();
        StringRec myStringRec = (StringRec) myList.getNode();
        FontMetrics myM = g.getFontMetrics();
        int x = 0;
        
        if (max > 0) {
            moveToPos(0);
            while (!done) {
                myMeta = myList.getNode();
                done = !((base + StringRec.BASE_CHARS) < insertPoint);
                myStringRec = (StringRec) myMeta;
                if ((base + StringRec.BASE_CHARS) <= insertPoint) {
                    x = x + myM.charsWidth(myStringRec.exportArray(), 1, StringRec.BASE_CHARS);
                } else {
                    x = x + myM.charsWidth(myStringRec.exportArray(), 1, insertPoint - base);
                }
                
                if ((base + StringRec.BASE_CHARS) < insertPoint)
                    moveForward();
            }
            
            if (insertPoint < max) {
                moveToPos(rstPoint);
                myMeta = myList.getNode();
                myStringRec = (StringRec) myMeta;
                x =
                        x
                        + myM.charsWidth(
                        myStringRec.exportArray(),
                        rstPoint - base + 1,
                        StringRec.BASE_CHARS - (rstPoint - base));
                done = !((base + StringRec.BASE_CHARS) < maxbase);
                
                while (!done) {
                    moveForward();
                    myMeta = myList.getNode();
                    myStringRec = (StringRec) myMeta;
                    x = x + myM.charsWidth(myStringRec.exportArray(), 1, StringRec.BASE_CHARS);
                    
                    done = !((base + StringRec.BASE_CHARS) < maxbase);
                }
            }
        }
        
        return (x);
    }; */
    
    /**
     * Deletes any unused memory allocated for this string.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.<BR>
     * <B>Post:</B> If there is more than 2 * BaseChars of memory unused,
     * some of it will be freed.<BR>
     * @author Thorn Green
     */
    private final void collectGarbage() {
        if (max > 0) {
            if (gapLength() >= 2 * StringRec.BASE_CHARS) {
                moveToPos(insertPoint);
                
                while (base < (insertPoint + gapLength())) {
                    if (((base + StringRec.BASE_CHARS) <= (insertPoint + gapLength())) && (base >= insertPoint)) {
                        myList.eraseNodeInfo();
                        maxbase = maxbase - StringRec.BASE_CHARS;
                    } else {
                        moveForward();
                    }
                }
                
                if (myList.getHead())
                    base = 0;
            }
        } else
            clearString();
    };
    
    /**
     * Sets the insertion point up to index.
     * <P>
     * <B>In:</B> Index.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.  Index is greater than the current value of the insertion point.<BR>
     * <B>Post:</B> The insertion point will be set to index.<BR>
     * @author Thorn Green
     */
    private final void setInsertPointUp(int index) {
        char inChar;
        int cnt;
        int ip1 = insertPoint + gapLength();
        int ip2 = insertPoint;
        Reader myRead = new Reader();
        
        myRead.setMax(max);
        myRead.setMaxbase(maxbase);
        myRead.setBase(base);
        myList.copyDataPlusPtrInfo((myRead.getMyList()));
        
        for (cnt = 0; cnt < (index - insertPoint); ++cnt) {
            inChar = myRead.getPhysicalChar(ip1 + cnt);
            setPhysicalChar(ip2 + cnt, inChar);
        }
        
        insertPoint = index;
    };
    
    /**
     * Sets the insertion point down to index.
     * <P>
     * <B>In:</B> Index.<BR>
     * <B>Out:</B> This modified.<BR>
     * <B>Pre:</B> This valid.  Index is less than the current value of the insertion point.<BR>
     * <B>Post:</B> The insertion point will be set to index.<BR>
     * @author Thorn Green
     */
    private final void setInsertPointDn(int index) {
        char inChar;
        int cnt;
        int ip1 = index;
        int ip2 = (insertPoint + gapLength()) - (insertPoint - index);
        Reader myRead = new Reader();
        
        myRead.setMax(max);
        myRead.setMaxbase(maxbase);
        myRead.setBase(base);
        myList.copyDataPlusPtrInfo((myRead.getMyList()));
        
        cnt = insertPoint - index;
        while (cnt != 0) {
            cnt--;
            inChar = myRead.getPhysicalChar(ip1 + cnt);
            setPhysicalChar(ip2 + cnt, inChar);
        }
        
        insertPoint = index;
    };
    private final char getPhysicalChar(int index) {
        char temp = 0;
        Meta myMeta;
        StringRec myStringRec;
        
        moveToPos(index);
        myMeta = myList.getNode();
        myStringRec = (StringRec) myMeta;
        temp = myStringRec.getPhysicalChar(index - base);
        return (temp);
    };
    private final void setPhysicalChar(int index, char inChar) {
        Meta myMeta;
        StringRec myStringRec;
        
        moveToPos(index);
        
        if (index >= (base + StringRec.BASE_CHARS)) {
            LowLevelList myNode = new AltLowList_StringRec();
            myList.importInsertRight(myNode);
            myNode.setCopyInfoMode(Meta.COPY_ALL_INFO);
            myNode.setEraseMode(Meta.WAKE);
            base = base + StringRec.BASE_CHARS;
            maxbase = maxbase + StringRec.BASE_CHARS;
        }
        
        myMeta = myList.getNode();
        myStringRec = (StringRec) myMeta;
        myStringRec.setPhysicalChar(index - base, inChar);
    };
    private final void moveForward() {
        myList.right();
        base = base + StringRec.BASE_CHARS;
    };
    private final void moveBackward() {
        myList.left();
        base = base - StringRec.BASE_CHARS;
    };
    private final void moveToPos(int pos) {
        while ((pos < base) && (base > 0))
            moveBackward();
        while ((pos >= (base + StringRec.BASE_CHARS)) && ((base + StringRec.BASE_CHARS) < maxbase))
            moveForward();
    };
    private final int gapLength() {
        return (maxbase - max);
    };
    private final int logicalToPhysical(int index) {
        int temp = index;
        if (temp >= insertPoint)
            temp = temp + gapLength();
        return (temp);
    };
    private final void dvSetMaxbase(int in) {
        maxbase = in;
    }
    private final void dvSetBase(int in) {
        base = in;
    }
    private final void dvSetMax(int in) {
        max = in;
    }
    private final void dvSetInsertPoint(int in) {
        insertPoint = in;
    }
    private final HighLevelList dvGetMyList() {
        return (myList);
    }
    
    private int max;
    private int maxbase;
    private int base;
    private int insertPoint;
    private final HighLevelList myList = new HighLevelList();
};
