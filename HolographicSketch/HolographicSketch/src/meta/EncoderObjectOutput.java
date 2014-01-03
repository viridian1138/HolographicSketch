


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
import java.io.ObjectOutput;
import java.util.Vector;



/**
 * @author thorngreen
 *
 * A version of ObjectOutput for supporting Encoders such as XMLEncoder.
 */
public class EncoderObjectOutput implements ObjectOutput {
    
    public EncoderObjectOutput() {
    }
    
    protected final Vector objs = new Vector();
    
        /* (non-Javadoc)
         * @see java.io.ObjectOutput#writeObject(java.lang.Object)
         */
    public void writeObject(Object arg0) throws IOException {
        objs.add(arg0);
        
    }
    
    public Object[] encodeObjects(Externalizable in) throws IOException {
        objs.add(in.getClass().getName());
        in.writeExternal(this);
        return (objs.toArray());
    }
    
    protected void throwEx() {
        throw ( new Meta.UndefinedOperation() );
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#write(int)
         */
    public void write(int arg0) throws IOException {
        throwEx();
        
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#write(byte[])
         */
    public void write(byte[] arg0) throws IOException {
        throwEx();
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#write(byte[], int, int)
         */
    public void write(byte[] arg0, int arg1, int arg2) throws IOException {
        throwEx();
    }
    
        /* (non-Javadoc)
         * @see java.io.ObjectOutput#flush()
         */
    public void flush() throws IOException {
        // Does Nothing.
    }
    
        /* (non-Javadoc)
         * @see java.io.ObjectOutput#close()
         */
    public void close() throws IOException {
        // Does Nothing.
        
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeBoolean(boolean)
         */
    public void writeBoolean(boolean arg0) throws IOException {
        writeObject(new Boolean(arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeByte(int)
         */
    public void writeByte(int arg0) throws IOException {
        writeObject(new Byte((byte) arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeShort(int)
         */
    public void writeShort(int arg0) throws IOException {
        writeObject(new Short((short) arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeChar(int)
         */
    public void writeChar(int arg0) throws IOException {
        writeObject(new Character((char) arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeInt(int)
         */
    public void writeInt(int arg0) throws IOException {
        writeObject(new Integer(arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeLong(long)
         */
    public void writeLong(long arg0) throws IOException {
        writeObject(new Long(arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeFloat(float)
         */
    public void writeFloat(float arg0) throws IOException {
        writeObject(new Float(arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeDouble(double)
         */
    public void writeDouble(double arg0) throws IOException {
        writeObject(new Double(arg0));
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeBytes(java.lang.String)
         */
    public void writeBytes(String arg0) throws IOException {
        throwEx();
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeChars(java.lang.String)
         */
    public void writeChars(String arg0) throws IOException {
        throwEx();
    }
    
        /* (non-Javadoc)
         * @see java.io.DataOutput#writeUTF(java.lang.String)
         */
    public void writeUTF(String arg0) throws IOException {
        throwEx();
    }
    
}
