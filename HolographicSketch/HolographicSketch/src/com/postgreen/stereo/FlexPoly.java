



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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import meta.DataFormatException;
import meta.VersionBuffer;
import android.graphics.Color;

public class FlexPoly implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (FlexPoly.class).getName().hashCode() + "v3/98A".hashCode();
	
	private final static int LINES_PER_ARRAY = 128;
	
	private int numLines = 0;
	
	private int drawingColor;

	private float lineWidth;
	
	final ArrayList<float[]> valueList = new ArrayList<float[]>();
	final ArrayList<FloatBuffer> floatList = new ArrayList<FloatBuffer>();

	
	public FlexPoly()
	{
	}
	
	
	public void draw(GL10 gl)
	{
		
		final int color = drawingColor;
        gl.glColor4f( 
        		(float)( Color.red(color) / 255.0 ) , 
        		(float)( Color.green(color) / 255.0 ) , 
        		(float)( Color.blue(color) / 255.0 ) , 
        		(float)( Color.alpha(color) / 255.0 ) );
        
        gl.glLineWidth( lineWidth );

        
        final int numCnt2 = floatList.size() - 1;
        
        int count;
        for( count = 0 ; count < numCnt2 ; count++ )
        {
        	// Specifies the location and data format of an array of vertex
        	// coordinates to use when rendering.
        	gl.glVertexPointer(3, GL10.GL_FLOAT, 0,  floatList.get( count ) ); // OpenGL docs.
        
        	gl.glDrawArrays(GL10.GL_LINES, 0, 2 * LINES_PER_ARRAY);
        }
        
        
        final int xcnt = numLines % LINES_PER_ARRAY;
        
        if( xcnt > 0 )
        {
        	gl.glVertexPointer(3, GL10.GL_FLOAT, 0,  floatList.get( count ) ); // OpenGL docs.
            
        	gl.glDrawArrays(GL10.GL_LINES, 0, 2 * xcnt);
        }
        
	}
	
	
	public void add( double[] p0 , double[] p1 )
	{
		float[] vertices = null;
		FloatBuffer vertexBuffer = null;
		final int numl = numLines % LINES_PER_ARRAY;
		if( numl == 0 )
		{
			vertices = new float[ 6 * LINES_PER_ARRAY ];
	        
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	        vbb.order(ByteOrder.nativeOrder());
	        vertexBuffer = vbb.asFloatBuffer();
	        
	        valueList.add( vertices );
	        floatList.add( vertexBuffer );
		}
		else
		{
			vertices = valueList.get( valueList.size() - 1 );
			vertexBuffer = floatList.get( valueList.size() - 1 );
		}
		
		final int index = 6 * numl;
		vertices[ index ] = (float)( p0[ 0 ] );
		vertices[ index + 1 ] = (float)( p0[ 1 ] );
		vertices[ index + 2 ] = (float)( p0[ 2 ] );
		
		vertices[ index + 3 ] = (float)( p1[ 0 ] );
		vertices[ index + 4 ] = (float)( p1[ 1 ] );
		vertices[ index + 5 ] = (float)( p1[ 2 ] );
		
		vertexBuffer.put(vertices);
        vertexBuffer.position(0);
		
		numLines++;
	}
	
	
	public int getDrawingColor() {
		return drawingColor;
	}


	public void setDrawingColor(int drawingColor) {
		this.drawingColor = drawingColor;
	}


	public float getLineWidth() {
		return lineWidth;
	}


	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);
		
		
		final LineDesc[] dr = new LineDesc[ numLines ];
		for( int count = 0 ; count < numLines ; count++ )
		{
			final float[] vals = valueList.get( count / LINES_PER_ARRAY );
			final int index = 6 * ( count % LINES_PER_ARRAY );
			final double[] p0 = new double[ 3 ];
			final double[] p1 = new double[ 3 ];
			
			p0[ 0 ] = vals[ index ];
			p0[ 1 ] = vals[ index + 1 ];
			p0[ 2 ] = vals[ index + 2 ];
			
			p1[ 0 ] = vals[ index + 3 ];
			p1[ 1 ] = vals[ index + 4 ];
			p1[ 2 ] = vals[ index + 5 ];
			
			final LineDesc ld = new LineDesc();
			ld.setP0( p0 );
			ld.setP1( p1 );
			dr[ count ] = ld;
		}
		myv.setProperty( "valueList" , dr );
		
		
		myv.setDouble( "lineWidth" , lineWidth );
	    
	    
	    myv.setInt( "drawingColor" , drawingColor );
	    

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
			
			
			
			
			LineDesc[] dr = (LineDesc[])( myv.getPropertyEx( "valueList" ) );
			for( int count = 0 ; count < dr.length ; count++ )
			{
				LineDesc ln = dr[ count ];
				add( ln.getP0() , ln.getP1() );
			}
			
			
			lineWidth = (float)( myv.getDouble( "lineWidth" ) );
		    
		    
		    drawingColor = myv.getInt( "drawingColor" );

			
		} catch (ClassCastException ex) {
			throw (new DataFormatException(ex));
		}
	}

	
	
	
}


