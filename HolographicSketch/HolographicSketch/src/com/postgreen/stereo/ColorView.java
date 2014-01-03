

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
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

// the main screen that is painted
public class ColorView extends View 
{
   // used to determine whether user moved a finger enough to draw again   
   
   private Paint paintLine; // used to draw lines onto bitmap
   

   // ColorView constructor initializes the ColorView
   public ColorView(Context context, AttributeSet attrs) 
   {
      super(context, attrs); // pass context to View's constructor

      // set the initial display settings for the painted line
      paintLine = new Paint();
      paintLine.setAntiAlias(true); // smooth edges of drawn line
      paintLine.setColor(Color.CYAN);
      paintLine.setStyle(Paint.Style.FILL); // solid line
      paintLine.setStrokeWidth(5); // set the default line width
      paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends
   } // end ColorView constructor
   
   // set the painted line's color
   public void setDrawingColor(int color) 
   {
	   paintLine = new Paint();
	      paintLine.setAntiAlias(true); // smooth edges of drawn line
	      paintLine.setColor( /* Color.YELLOW */ color );
	      paintLine.setStyle(Paint.Style.FILL); // solid line
	      paintLine.setStrokeWidth(5); // set the default line width
	      paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends
   } // end method setDrawingColor

   // return the painted line's color
   public int getDrawingColor() 
   {
      return paintLine.getColor();
   } // end method getDrawingColor

   // called each time this View is drawn
   @Override
   protected void onDraw(Canvas canvas) 
   {
      
    Rect r = new Rect( -1000 , -1000 , 1000 , 1000 );
	canvas.drawRect( r , paintLine);
      
   } // end method onDraw

   
  
} // end class ColorView

