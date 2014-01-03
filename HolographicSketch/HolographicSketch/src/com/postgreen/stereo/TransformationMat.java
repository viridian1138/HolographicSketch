



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

import meta.DataFormatException;
import meta.VersionBuffer;

public class TransformationMat implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (TransformationMat.class).getName().hashCode() + "v3/98A".hashCode();
	
	protected double[][] mat = new double[ 4 ][ 4 ];
	
	public TransformationMat()
	{
		mat[ 0 ][ 0 ] = 1.0;
		mat[ 1 ][ 1 ] = 1.0;
		mat[ 2 ][ 2 ] = 1.0;
		mat[ 3 ][ 3 ] = 1.0;
	}
	
	public TransformationMat( final double angDegrees , final double x , final double y , final double z )
	{
		final double angRadians = angDegrees * Math.PI / 180.0;
		final double s = Math.sin( angRadians );
		final double c = Math.cos( angRadians );
		
		mat[ 0 ][ 0 ] = x * x * ( 1 - c ) + c;
		mat[ 0 ][ 1 ] = x * y * ( 1 - c ) - z * s;
		mat[ 0 ][ 2 ] = x * z * ( 1 - c ) + y * s;
		
		mat[ 1 ][ 0 ] = y * x * ( 1 - c ) + z * s;
		mat[ 1 ][ 1 ] = y * y * ( 1 - c ) + c;
		mat[ 1 ][ 2 ] = y * z * ( 1 - c ) - x * s;
		
		mat[ 2 ][ 0 ] = x * z * ( 1 - c ) - y * s;
		mat[ 2 ][ 1 ] = y * z * ( 1 - c ) + x * s;
		mat[ 2 ][ 2 ] = z * z * ( 1 - c ) + c;
		
		mat[ 3 ][ 3 ] = 1.0;
		
	}
	
	
	public TransformationMat( final double zTranslate )
	{
		mat[ 0 ][ 0 ] = 1.0;
		mat[ 1 ][ 1 ] = 1.0;
		mat[ 2 ][ 2 ] = 1.0;
		mat[ 3 ][ 3 ] = 1.0;
		mat[ 2 ][ 3 ] = zTranslate;
	}
	
	
	public void multVect( double[] in , double[] out )
	{
		for( int c0 = 0 ; c0 < 3 ; c0++ )
		{
			double sum = 0.0;
			for( int c1 = 0 ; c1 < 3 ; c1++ )
			{
				sum += mat[ c0 ][ c1 ] * in[ c1 ];
			}
			out[ c0 ] = sum;
		}
	}
	
	
	
	
	public void multMat( TransformationMat in , TransformationMat out )
	{
		final double[][] iths = mat;
		final double[][] iin = in.mat;
		final double[][] iout = out.mat;
		
		for( int i = 0 ; i < 4 ; i++ )
		{
			for( int j = 0 ; j < 4 ; j++ )
			{
				double sum = 0.0;
				
				for( int s = 0 ; s < 4 ; s++ )
				{
					sum += iths[ i ][ s ] * iin[ s ][ j ];
				}
				
				iout[ i ][ j ] = sum;
			}
		}
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);

		myv.setProperty("mat", mat);

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

			mat = (double[][])( myv.getPropertyEx("mat") );

		} catch (ClassCastException ex) {
			throw (new DataFormatException(ex));
		}
	}
	

}




