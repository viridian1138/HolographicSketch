




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
 * Staque stands for "stack-queue" or
 * "stack and queue".  The Staque class uses the functionality of
 * {@link HighLevelList} to implement both a linked stack and a linked queue.
 * That is to say, Staque is a single data structure which functions as both a stack and a queue.
 * Typical stack and queue operations (e.g. Push, Pop, Enq, Deq) are supported.  Both standard and
 * Alt-nodes are supported.
 * @author Thorn Green
 */
public class Staque extends Meta {
    /**
     * @see meta.Meta
     */
    public Meta copyNode() {
        Staque temp = new Staque();
        qHighLevelList.copyNodeInfo(temp.dvGetQHighLevelList());
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copySub() {
        Staque temp = new Staque();
        qHighLevelList.copySubInfo(temp.dvGetQHighLevelList());
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyAll() {
        Staque temp = new Staque();
        qHighLevelList.copyAllInfo(temp.dvGetQHighLevelList());
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyData() {
        Staque temp = new Staque();
        qHighLevelList.copyDataInfo(temp.dvGetQHighLevelList());
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public Meta copyDataPlusPtr() {
        Staque temp = new Staque();
        qHighLevelList.copyDataPlusPtrInfo(temp.dvGetQHighLevelList());
        return (temp);
    };
    /**
     * @see meta.Meta
     */
    public void copyNodeInfo(Meta in) {
        Staque temp = (Staque) in;
        qHighLevelList.copyNodeInfo(temp.dvGetQHighLevelList());
    };
    /**
     * @see meta.Meta
     */
    public void copySubInfo(Meta in) {
        Staque temp = (Staque) in;
        qHighLevelList.copySubInfo(temp.dvGetQHighLevelList());
    };
    /**
     * @see meta.Meta
     */
    public void copyAllInfo(Meta in) {
        Staque temp = (Staque) in;
        qHighLevelList.copyAllInfo(temp.dvGetQHighLevelList());
    };
    /**
     * @see meta.Meta
     */
    public void copyDataInfo(Meta in) {
        Staque temp = (Staque) in;
        qHighLevelList.copyDataInfo(temp.dvGetQHighLevelList());
    };
    /**
     * @see meta.Meta
     */
    public void copyDataPlusPtrInfo(Meta in) {
        Staque temp = (Staque) in;
        qHighLevelList.copyDataPlusPtrInfo(temp.dvGetQHighLevelList());
    };
    /**
     * @see meta.Meta
     */
    public void eraseNode() {
        qHighLevelList.eraseNodeInfo();
    };
    /**
     * @see meta.Meta
     */
    public void eraseSub() {
        qHighLevelList.eraseSubInfo();
    };
    /**
     * @see meta.Meta
     */
    public void eraseAll() {
        qHighLevelList.eraseAllInfo();
    };
    /**
     * @see meta.Meta
     */
    public void eraseData() {};
    /**
     * @see meta.Meta
     */
    public void eraseNodeInfo() {
        qHighLevelList.eraseNodeInfo();
    };
    /**
     * @see meta.Meta
     */
    public void eraseSubInfo() {
        qHighLevelList.eraseSubInfo();
    };
    /**
     * @see meta.Meta
     */
    public void eraseAllInfo() {
        qHighLevelList.eraseAllInfo();
    };
    /**
     * @see meta.Meta
     */
    public void erasePtrVal() {
        qHighLevelList.erasePtrVal();
    };
    /**
     * @see meta.Meta
     */
    public void wake() {};
    
    /**
     * Adds an element as if the DS is a queue.
     */
    public final void enq(Meta in) {
        HighLevelList ql = qHighLevelList;
        ql.insertLeft(in);
    };
    /**
     * Pushes an element as if the DS is a stack.
     */
    public final void push(Meta in) {
        HighLevelList ql = qHighLevelList;
        ql.insertLeft(in);
    };
    /**
     * Adds an element as if the DS is a queue.
     */
    public final void importEnq(LowLevelList in) {
        HighLevelList ql = qHighLevelList;
        ql.importInsertLeft(in);
    };
    /**
     * Pushes an element as if the DS is a stack.
     */
    public final void importPush(LowLevelList in) {
        HighLevelList ql = qHighLevelList;
        ql.importInsertLeft(in);
    };
    
    /**
     * Pops a stack node.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B> <BR>
     * <B>Pre:</B> The stack is not empty.  The stack is properly constructed.<BR>
     * <B>Post:</B> The node will be deleted, and its value returned.
     * <P>
     * @return The popped node.
     *
     * @author Thorn Green
     */
    public final Meta pop() {
        HighLevelList ql = qHighLevelList;
        Meta temp = ql.getNode();
        
        ql.eraseNodeInfo();
        return (temp);
    };
    
    /**
     * Deqs a queue node.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B> <BR>
     * <B>Pre:</B> The queue is not empty.  The queue is properly constructed.<BR>
     * <B>Post:</B> The node will be deleted, and its value returned.
     * <P>
     * @return The deq-ed node.
     * @author Thorn Green
     */
    public final Meta deq() {
        Meta temp;
        
        HighLevelList ql = qHighLevelList;
        ql.left();
        temp = ql.getNode();
        ql.eraseNodeInfo();
        return (temp);
    };
    
    /**
     * Returns true if the DS is empty.
     */
    public final boolean empty() {
        HighLevelList ql = qHighLevelList;
        return (ql.empty());
    };
    
    /**
     * Gets the front node of a stack or queue.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B><BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The node value will be returned.
     * <P>
     * @return The front node.
     * @author Thorn Green
     */
    public final Meta getFrontNode() {
        Meta temp;
        
        HighLevelList ql = qHighLevelList;
        ql.left();
        temp = ql.getNode();
        ql.right();
        return (temp);
    };
    
    /**
     * Gets the rear node of a stack or queue.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B><BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The node value will be returned.
     * <P>
     * @return The rear node.
     * @author Thorn Green
     */
    public final Meta getRearNode() {
        HighLevelList ql = qHighLevelList;
        return (ql.getNode());
    };
    
    /**
     * Gets the front copy mode of a stack or queue.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B><BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The copy mode value will be returned.
     * <P>
     * @return The front copy mode.
     * @author Thorn Green
     */
    public final int getFrontCopyMode() {
        int temp;
        
        HighLevelList ql = qHighLevelList;
        ql.left();
        temp = ql.getCopyMode();
        ql.right();
        return (temp);
    };
    
    /**
     * Gets the rear copy mode of a stack or queue.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B><BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The copy mode value will be returned.
     * <P>
     * @return The rear copy mode.
     * @author Thorn Green
     */
    public final int getRearCopyMode() {
        HighLevelList ql = qHighLevelList;
        return (ql.getCopyMode());
    };
    
    /**
     * Gets the front erase mode of a stack or queue.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B><BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The erase mode value will be returned.
     * <P>
     * @return The front erase mode.
     * @author Thorn Green
     */
    public final int getFrontEraseMode() {
        int temp;
        
        HighLevelList ql = qHighLevelList;
        ql.left();
        temp = ql.getEraseMode();
        ql.right();
        return (temp);
    };
    
    /**
     * Gets the rear erase mode of a stack or queue.
     * <P>
     * <B>In:</B> None.<BR>
     * <B>Out:</B><BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The erase mode value will be returned.
     * <P>
     * @return The rear erase mode.
     * @author Thorn Green
     */
    public final int getRearEraseMode() {
        HighLevelList ql = qHighLevelList;
        return (ql.getEraseMode());
    };
    
    /**
     * Sets the front node of a stack or queue.
     * <P>
     * <B>In:</B> The node value to set.<BR>
     * <B>Out:</B> Staque modified.<BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The front node will be set.
     * <P>
     * @author Thorn Green
     */
    public final void setFrontNode(Meta in) {
        HighLevelList ql = qHighLevelList;
        ql.left();
        ql.setNode(in);
        ql.right();
    };
    
    /**
     * Sets the rear node of a stack or queue.
     * <P>
     * <B>In:</B> The node value to set.<BR>
     * <B>Out:</B> Staque modified.<BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The rear node will be set.
     * <P>
     * @author Thorn Green
     */
    public final void setRearNode(Meta in) {
        HighLevelList ql = qHighLevelList;
        ql.setNode(in);
    };
    
    /**
     * Sets the front copy mode of a stack or queue.
     * <P>
     * <B>In:</B> The node value to set.<BR>
     * <B>Out:</B> Staque modified.<BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The copy mode will be set.
     * <P>
     * @author Thorn Green
     */
    public final void setFrontCopyMode(int in) {
        HighLevelList ql = qHighLevelList;
        ql.left();
        ql.setCopyMode(in);
        ql.right();
    };
    
    /**
     * Sets the rear copy mode of a stack or queue.
     * <P>
     * <B>In:</B> The node value to set.<BR>
     * <B>Out:</B> Staque modified.<BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The copy mode will be set.
     * <P>
     * @author Thorn Green
     */
    public final void setRearCopyMode(int in) {
        HighLevelList ql = qHighLevelList;
        ql.setCopyMode(in);
    };
    
    /**
     * Sets the front erase mode of a stack or queue.
     * <P>
     * <B>In:</B> The node value to set.<BR>
     * <B>Out:</B> Staque modified.<BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The erase mode will be set.
     * <P>
     * @author Thorn Green
     */
    public final void setFrontEraseMode(int in) {
        HighLevelList ql = qHighLevelList;
        ql.left();
        ql.setEraseMode(in);
        ql.right();
    };
    
    /**
     * Sets the rear erase mode of a stack or queue.
     * <P>
     * <B>In:</B> The node value to set.<BR>
     * <B>Out:</B> Staque modified.<BR>
     * <B>Pre:</B> The Staque is not empty.  The Staque is properly constructed.<BR>
     * <B>Post:</B> The erase mode will be set.
     * <P>
     * @author Thorn Green
     */
    public final void setRearEraseMode(int in) {
        HighLevelList ql = qHighLevelList;
        ql.setEraseMode(in);
    };
    
    private final HighLevelList dvGetQHighLevelList() {
        return (qHighLevelList);
    }
    public final void iStaque() {
        HighLevelList ql = qHighLevelList;
        ql.iHighLevelList();
    };
    public Staque() {
        this.iStaque();
    };
    private final HighLevelList qHighLevelList = new HighLevelList();
};
