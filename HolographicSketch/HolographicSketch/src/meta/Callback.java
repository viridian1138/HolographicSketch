



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
 * Callback is a base class that provides a framework to implement the
 * "visit" function often found both in data structure theory and practice.  For
 * instance, the inorder traversal of a binary tree is often written in textbooks
 * with a visit() function to denote the visiting of each node.  Callback provides
 * a way for {@link Meta}-compliant classes to implement such a
 * visit() function.<P>
 * Here's an example.  One has a class, which in this example will be
 * called RecClass, which contains a Meta-compliant binary tree.  The goal is for
 * an object of RecClass to perform an inorder traversal on this binary tree with
 * each visit() being passed back to the calling object as a message.  The author
 * of RecClass simply overrides the message to implement the actual way visit().
 * In Java, a base class is needed to implement this kind of behavior.  This is
 * where Callback comes in.  The author of RecClass has it publicly inherit from
 * Callback, and override Callback's methods.  This provides an interface through
 * which the inorder can call an object in RecClass.  Finally, the author of
 * RecClass has the RecClass object calls the inorder traversal routine of the
 * binary tree.  When it does so, the RecClass object passes the "this" reference
 * as one of the parameters.  The inorder traversal doesn't know the type of the
 * object being passed to it.  All it knows it that it's got a reference to a
 * Callback object.  Thus, any class that can publicly inherit from Callback can
 * transparently benefit from the same inorder traversal routine.
 * @author Thorn Green
 */
public class Callback extends Object {
    final public static int CALLBACK_1 = 1;
    public void callback1(Meta in) {
        throwEx();
    };
    final public static int CALLBACK_2 = 2;
    public void callback2(Meta in) {
        throwEx();
    };
    final public static int CALLBACK_3 = 3;
    public void callback3(Meta in) {
        throwEx();
    };
    final public static int CALLBACK_4 = 4;
    public void callback4(Meta in) {
        throwEx();
    };
    void throwEx() { throw( new Meta.UndefinedOperation() );
    }
    
    /**
     * Sends the object <code>in</code> through the Callback
     * <code>Call</code>.
     */
    public void exeCall(int call, Meta in) {
        
        switch (call) {
            case CALLBACK_1 :
                callback1(in);
                break;
            case CALLBACK_2 :
                callback2(in);
                break;
            case CALLBACK_3 :
                callback3(in);
                break;
            case CALLBACK_4 :
                callback4(in);
                break;
        }
    }
    
};
