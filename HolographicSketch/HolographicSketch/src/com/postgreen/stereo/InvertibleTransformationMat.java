




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





package com.postgreen.stereo;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.microedition.khronos.opengles.GL10;

import meta.DataFormatException;
import meta.VersionBuffer;

public class InvertibleTransformationMat implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (InvertibleTransformationMat.class).getName().hashCode() + "v3/98A".hashCode();
	
	
	protected TransformationMat trans;
	protected TransformationMat inverse;
	
	public InvertibleTransformationMat()
	{
		trans = new TransformationMat();
		inverse = new TransformationMat();
	}
	
	public InvertibleTransformationMat( final double angDegrees , final double x , final double y , final double z )
	{
		trans = new TransformationMat( angDegrees , x , y , z );
		inverse = new TransformationMat( - angDegrees , x , y , z );
	}
	
	public InvertibleTransformationMat( double zTranslate )
	{
		trans = new TransformationMat( zTranslate );
		inverse = new TransformationMat( -zTranslate );
	}
	
	public void multVect( double[] in , double[] out )
	{
		trans.multVect( in , out );
	}
	
	public void multVectInverse( double[] in , double[] out )
	{
		inverse.multVect( in , out );
	}
	
	public void multMat( InvertibleTransformationMat in , InvertibleTransformationMat out )
	{
		trans.multMat( in.trans , out.trans );
		in.inverse.multMat( inverse , out.inverse );
	}
	
	public void applyToGl( GL10 gl )
	{
		final double[][] mat = trans.mat;
		final float[] vals = new float[ 16 ];
		
		for( int j = 0 ; j < 4 ; j++ )
		{
			for( int i = 0 ; i < 4 ; i++ )
			{
				vals[ 4 * j + i ] = (float)( mat[ i ][ j ] );
			}
		}
		
		gl.glLoadMatrixf( vals , 0 );
	}
	
	public static InvertibleTransformationMat buildRotationMat( double xrot , double yrot , double zrot )
	{
        final InvertibleTransformationMat rx = new InvertibleTransformationMat( xrot , 0 , 0 , 1 );
        final InvertibleTransformationMat ry = new InvertibleTransformationMat( yrot , 0 , 1 , 0 );
        final InvertibleTransformationMat rz = new InvertibleTransformationMat( zrot , 1 , 0 , 0 );
        
        final InvertibleTransformationMat r1 = new InvertibleTransformationMat();
        final InvertibleTransformationMat r2 = new InvertibleTransformationMat();
        
        rx.multMat( ry , r1 );
        r1.multMat( rz , r2 );
        return( r2 );
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);

		myv.setProperty("trans", trans);
		myv.setProperty("inverse", inverse);

		out.writeObject(myv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		try {
			VersionBuffer myv = (VersionBuffer) (in.readObject());
			VersionBuffer.chkNul(myv);

			trans = (TransformationMat)( myv.getPropertyEx("trans") );
			inverse = (TransformationMat)( myv.getPropertyEx("inverse") );

		} catch (ClassCastException ex) {
			throw (new DataFormatException(ex));
		}
	}

}

