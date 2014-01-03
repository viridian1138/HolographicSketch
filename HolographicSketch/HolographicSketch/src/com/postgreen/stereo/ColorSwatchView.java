

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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author thorngreen
 *
 */
public class ColorSwatchView extends View {
	
	private static final String TAG = "ColorSwatchView";
	
	protected IColorSet colorSet = null;
	

	/**
	 * @param context
	 */
	public ColorSwatchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ColorSwatchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ColorSwatchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public static void cross(double[] v1, double[] v2, double[] r) {
        r[0] = v1[1] * v2[2] - v2[1] * v1[2];
        r[1] = v1[2] * v2[0] - v2[2] * v1[0];
        r[2] = v1[0] * v2[1] - v2[0] * v1[1];
    }

    public static void scalarMultiply(double[] v, double s) {
        for (int i = 0; i < v.length; i++) {
            v[i] *= s;
        }
    }

    public static double magnitude(double[] v) {
        return (double) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }

    public static void normalize(double[] v) {
        scalarMultiply(v, 1 / magnitude(v));
    }
    
    public static double[] getYVector()
    {
    	final double[] y = { 0.299 , 0.587 , 0.114 };
    	normalize( y );
    	return( y );
    }
    
    public static double[] getBpVector()
    {
    	final double[] bpvect = new double[ 3 ];
    	final double[] gvect = { 0.0 , 1.0 , 0.0 };
    	cross( getYVector() , gvect , bpvect );
    	normalize( bpvect );
    	return( bpvect );
    }
    
    public static double[] getGpVector()
    {
    	final double[] gpvect = new double[ 3 ];
    	cross( getYVector() , getBpVector() , gpvect );
    	normalize( gpvect );
    	return( gpvect );
    }
    
    
    protected static double getScalarExtent( double strt , double orient )
    {
    	if( orient > 1E-6 )
    	{
    		return( ( 1.0 - strt ) / orient );
    	}
    	
    	if( orient < -1E-6 )
    	{
    		return( - ( strt / orient ) );
    	}
    	
    	return( 0.0 );
    }
    
    
    public static void getVectExtent( double[] strt , double[] orient , double[] res )
    {
    	final double d0a = getScalarExtent( strt[ 0 ] , orient[ 0 ] );
    	final double d0b = - getScalarExtent( strt[ 0 ] , - orient[ 0 ] );
    	
    	final double d1a = getScalarExtent( strt[ 1 ] , orient[ 1 ] );
    	final double d1b = - getScalarExtent( strt[ 1 ] , - orient[ 1 ] );
    	
    	final double d2a = getScalarExtent( strt[ 2 ] , orient[ 2 ] );
    	final double d2b = - getScalarExtent( strt[ 2 ] , - orient[ 2 ] );
    	
    	final double d0 = Math.max( Math.min( d0a , d0b ) , Math.max( Math.min( d1a , d1b ) , Math.min( d2a , d2b ) ) );
    	final double d1 = Math.min( Math.max( d0a , d0b ) , Math.min( Math.max( d1a , d1b ) , Math.max( d2a , d2b ) ) );
    	
    	res[ 0 ] = d0;
    	res[ 1 ] = d1;
    }
    
    
    protected volatile double stVal = 0.5;
    
    
    protected void setColorVal( double[] col )
    {
    	double[] yVector = getYVector();
    	double yVal = col[ 0 ] * yVector[ 0 ] + col[ 1 ] * yVector[ 1 ] + col[ 2 ] * yVector[ 2 ];
    	double divVal = yVal / ( yVector[ 0 ] + yVector[ 1 ] + yVector[ 2 ] );
    	// stVal = 3.0 * divVal;
    	stVal = divVal;
    	if( stVal > 1.0 ) stVal = 1.0;
    	invalidate();
    }
    
    
    
 // called each time this View is drawn
    @Override
    protected void onDraw(Canvas canvas) 
    {
    	final int width = getWidth();
    	final int height = getHeight();
    	
    	final int wid2 = width / 2;
    	final int hei2 = height / 2;
    	
    	final double[] strt = { stVal , stVal , stVal };
    	final double[] gpvect = getGpVector();
    	final double[] bpvect = getBpVector();
    	
    	Paint paintFill = new Paint();
        // paintLine.setAntiAlias(true); // smooth edges of drawn line
        paintFill.setColor(Color.RED); // default color is black
        paintFill.setStyle(Paint.Style.FILL); // solid line
        //paintLine.setStrokeWidth(5); // set the default line width
        //paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends
    	
    	int count;
    	for( count = 0 ; count < 180 ; count++ )
    	{
    		double ang = count * Math.PI / 180.0;
    		final double cosAng = Math.cos( ang );
    		final double sinAng = Math.sin( ang );
    		
    		final double[] orient = { cosAng * gpvect[ 0 ] + sinAng * bpvect[ 0 ] , 
    				cosAng * gpvect[ 1 ] + sinAng * bpvect[ 1 ] , 
    				cosAng * gpvect[ 2 ] + sinAng * bpvect[ 2 ] };
    		
    		final double[] res = new double[ 2 ];
    		
    		getVectExtent( strt , orient , res );
    		
    		
    		for( int count2 = 0 ; count2 <= 100 ; count2++ )
    		{
    			double pct = count2 / 100.0;
    			double pctc = pct;
    			double[] cval = { ( 1.0 - pctc ) * strt[ 0 ]  + pctc * ( strt[ 0 ]  + res[ 1 ] * orient[ 0 ] ) ,
    					( 1.0 - pctc ) * strt[ 1 ]  + pctc * ( strt[ 1 ]  + res[ 1 ] * orient[ 1 ] ) , 
    					( 1.0 - pctc ) * strt[ 2 ]  + pctc * ( strt[ 2 ]  + res[ 1 ] * orient[ 2 ] ) };
    			if( cval[ 0 ] < 0.0 ) cval[ 0 ] = 0.0;
    			if( cval[ 1 ] < 0.0 ) cval[ 1 ] = 0.0;
    			if( cval[ 2 ] < 0.0 ) cval[ 2 ] = 0.0;
    			if( cval[ 0 ] > 1.0 ) cval[ 0 ] = 1.0;
    			if( cval[ 1 ] > 1.0 ) cval[ 1 ] = 1.0;
    			if( cval[ 2 ] > 1.0 ) cval[ 2 ] = 1.0;
    			int red = (int)( 256.0 * cval[ 0 ] );
    			int green = (int)( 256.0 * cval[ 1 ] );
    			int blue = (int)( 256.0 * cval[ 2 ] );
    			if( red > 255 ) red = 255;
    			if( green > 255 ) green = 255;
    			if( blue > 255 ) blue = 255;
    			paintFill.setColor( Color.rgb(red, green, blue) );
    			canvas.drawRect( (float)( wid2 + wid2 * pct * cosAng - 2 ) , 
    					(float)( hei2 + hei2 * pct * sinAng - 2 ) , 
    					(float)( wid2 + wid2 * pct * cosAng + 2 ) , 
    					(float)( hei2 + hei2 * pct * sinAng + 2 ) , 
    					paintFill);
    		}
    		
    		for( int count2 = 0 ; count2 <= 100 ; count2++ )
    		{
    			double pct = - count2 / 100.0;
    			double pctc = -pct;
    			double[] cval = { ( 1.0 - pctc ) * strt[ 0 ]  + pctc * ( strt[ 0 ]  + res[ 0 ] * orient[ 0 ] ) ,
    					( 1.0 - pctc ) * strt[ 1 ]  + pctc * ( strt[ 1 ]  + res[ 0 ] * orient[ 1 ] ) , 
    					( 1.0 - pctc ) * strt[ 2 ]  + pctc * ( strt[ 2 ]  + res[ 0 ] * orient[ 2 ] ) };
    			if( cval[ 0 ] < 0.0 ) cval[ 0 ] = 0.0;
    			if( cval[ 1 ] < 0.0 ) cval[ 1 ] = 0.0;
    			if( cval[ 2 ] < 0.0 ) cval[ 2 ] = 0.0;
    			if( cval[ 0 ] > 1.0 ) cval[ 0 ] = 1.0;
    			if( cval[ 1 ] > 1.0 ) cval[ 1 ] = 1.0;
    			if( cval[ 2 ] > 1.0 ) cval[ 2 ] = 1.0;
    			int red = (int)( 256.0 * cval[ 0 ] );
    			int green = (int)( 256.0 * cval[ 1 ] );
    			int blue = (int)( 256.0 * cval[ 2 ] );
    			if( red > 255 ) red = 255;
    			if( green > 255 ) green = 255;
    			if( blue > 255 ) blue = 255;
    			paintFill.setColor( Color.rgb(red, green, blue) );
    			canvas.drawRect( (float)( wid2 + wid2 * pct * cosAng - 2 ) , 
    					(float)( hei2 + hei2 * pct * sinAng - 2 ) , 
    					(float)( wid2 + wid2 * pct * cosAng + 2 ) , 
    					(float)( hei2 + hei2 * pct * sinAng + 2 ) , 
    					paintFill);
    		}
    		
    	}
    	
    	
    } // end method onDraw
    
    
    
 // handle touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	final int action = event.getActionMasked(); // event type 
    	
    	if( action != MotionEvent.ACTION_DOWN )
    	{
    		return( true );
    	}
    	
    	final int width = getWidth();
    	final int height = getHeight();
    	
    	final int wid2 = width / 2;
    	final int hei2 = height / 2;
    	
    	final float x = event.getX();
    	final float y = event.getY();
    	
    	final double[] strt = { stVal , stVal , stVal };
    	final double[] gpvect = getGpVector();
    	final double[] bpvect = getBpVector();
    	
    	final double ang = Math.atan2( y - hei2 , x - wid2 );
    	
    	final double cosAng = Math.cos( ang );
		final double sinAng = Math.sin( ang );
		
		final double[] orient = { cosAng * gpvect[ 0 ] + sinAng * bpvect[ 0 ] , 
				cosAng * gpvect[ 1 ] + sinAng * bpvect[ 1 ] , 
				cosAng * gpvect[ 2 ] + sinAng * bpvect[ 2 ] };
		
		final double[] res = new double[ 2 ];
		
		getVectExtent( strt , orient , res );
		
		
		double pct = Math.sqrt( ( x - wid2 ) * ( x - wid2 ) + ( y - hei2 ) * ( y - hei2 ) ) / wid2;
		Log.d(TAG, "strt=" + stVal + " pct=" + pct);
		if( pct > 1.0 ) pct = 1.0;
		
		double[] cval = { ( 1.0 - pct ) * strt[ 0 ]  + pct * ( strt[ 0 ]  + res[ 1 ] * orient[ 0 ] ) ,
				( 1.0 - pct ) * strt[ 1 ]  + pct * ( strt[ 1 ]  + res[ 1 ] * orient[ 1 ] ) , 
				( 1.0 - pct ) * strt[ 2 ]  + pct * ( strt[ 2 ]  + res[ 1 ] * orient[ 2 ] ) };
		Log.d(TAG, "cval0=" + cval[0] + " cval1=" + cval[1] + " cval2=" + cval[2] );
		if( cval[ 0 ] < 0.0 ) cval[ 0 ] = 0.0;
		if( cval[ 1 ] < 0.0 ) cval[ 1 ] = 0.0;
		if( cval[ 2 ] < 0.0 ) cval[ 2 ] = 0.0;
		if( cval[ 0 ] > 1.0 ) cval[ 0 ] = 1.0;
		if( cval[ 1 ] > 1.0 ) cval[ 1 ] = 1.0;
		if( cval[ 2 ] > 1.0 ) cval[ 2 ] = 1.0;
		int red = (int)( 256.0 * cval[ 0 ] );
		int green = (int)( 256.0 * cval[ 1 ] );
		int blue = (int)( 256.0 * cval[ 2 ] );
		if( red > 255 ) red = 255;
		if( green > 255 ) green = 255;
		if( blue > 255 ) blue = 255;
		
		if( colorSet != null )
		{
			colorSet.setColors(red, green, blue);
		}
    	
    	return true;
    }

	/**
	 * @return the colorSet
	 */
	public IColorSet getColorSet() {
		return colorSet;
	}

	/**
	 * @param colorSet the colorSet to set
	 */
	public void setColorSet(IColorSet colorSet) {
		this.colorSet = colorSet;
	}
    
    

}
