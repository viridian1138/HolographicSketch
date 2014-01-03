



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
import java.util.ArrayList;

import meta.DataFormatException;
import meta.VersionBuffer;


public class PersistenceModel implements Externalizable {
	
	/**
	* Version number used to support versioned persistence.
	*/
	static final long serialVersionUID = (PersistenceModel.class).getName().hashCode() + "v3/98A".hashCode();
	
	
	private InvertibleTransformationMat rotMat;
	
	private InvertibleTransformationMat speedMat;
	
	private ArrayList<FlexPoly> drawList;
	
	private double lineWidth = 3.0f;
    
    
    private int drawingColor = 0;
    
    private int backgroundColor = 0;
	

	/**
	 * 
	 */
	public PersistenceModel() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		VersionBuffer myv = new VersionBuffer(VersionBuffer.WRITE);
		
		myv.setProperty( "rotMat" , rotMat );
		
		myv.setProperty( "speedMat" , speedMat );
		
		final FlexPoly[] polys = drawList.toArray( new FlexPoly[0] );
		myv.setProperty( "drawList" , polys );
		
		
		myv.setDouble( "lineWidth" , lineWidth );
	    
	    
	    myv.setInt( "drawingColor" , drawingColor );
	    
	    myv.setInt( "backgroundColor" , backgroundColor );

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
			
			
			rotMat = (InvertibleTransformationMat)( myv.getPropertyEx( "rotMat" ) );
			
			speedMat = (InvertibleTransformationMat)( myv.getPropertyEx( "speedMat" ) );
			
			FlexPoly[] dr = (FlexPoly[])( myv.getPropertyEx( "drawList" ) );
			drawList = new ArrayList<FlexPoly>();
			for( int count = 0 ; count < dr.length ; count++ )
			{
				drawList.add( dr[ count ] );
			}
			
			
			lineWidth = myv.getDouble( "lineWidth" );
		    
		    
		    drawingColor = myv.getInt( "drawingColor" );
		    
		    backgroundColor =  myv.getInt( "backgroundColor" );

			
		} catch (ClassCastException ex) {
			throw (new DataFormatException(ex));
		}
	}

	/**
	 * @return the rotMat
	 */
	public InvertibleTransformationMat getRotMat() {
		return rotMat;
	}

	/**
	 * @param rotMat the rotMat to set
	 */
	public void setRotMat(InvertibleTransformationMat rotMat) {
		this.rotMat = rotMat;
	}

	/**
	 * @return the speedMat
	 */
	public InvertibleTransformationMat getSpeedMat() {
		return speedMat;
	}

	/**
	 * @param speedMat the speedMat to set
	 */
	public void setSpeedMat(InvertibleTransformationMat speedMat) {
		this.speedMat = speedMat;
	}

	/**
	 * @return the drawList
	 */
	public ArrayList<FlexPoly> getDrawList() {
		return drawList;
	}

	/**
	 * @param drawList the drawList to set
	 */
	public void setDrawList(ArrayList<FlexPoly> drawList) {
		this.drawList = drawList;
	}

	/**
	 * @return the lineWidth
	 */
	public double getLineWidth() {
		return lineWidth;
	}

	/**
	 * @param lineWidth the lineWidth to set
	 */
	public void setLineWidth(double lineWidth) {
		this.lineWidth = lineWidth;
	}

	/**
	 * @return the drawingColor
	 */
	public int getDrawingColor() {
		return drawingColor;
	}

	/**
	 * @param drawingColor the drawingColor to set
	 */
	public void setDrawingColor(int drawingColor) {
		this.drawingColor = drawingColor;
	}

	/**
	 * @return the backgroundColor
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
