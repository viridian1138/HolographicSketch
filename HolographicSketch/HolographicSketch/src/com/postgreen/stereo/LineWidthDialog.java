



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

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class LineWidthDialog {
	
	// variable that refers to a Choose Color or Choose Line Width dialog
	private Dialog currentDialog; 
			
	private AtomicBoolean dialogIsVisible = new AtomicBoolean(); // false
	
	private ILineWidthDef def;
	
	
	public LineWidthDialog( ILineWidthDef _def )
	{
		def = _def;
	}
	
	
	// display a dialog for setting the line width
	   public void showLineWidthDialog( Activity activity )
	   {
	      // create the dialog and inflate its content
	      currentDialog = new Dialog( activity );
	      currentDialog.setContentView(R.layout.width_dialog);
	      currentDialog.setTitle(R.string.title_line_width_dialog);
	      currentDialog.setCancelable(true);
	      
	      // get widthSeekBar and configure it
	      SeekBar widthSeekBar = 
	         (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
	      widthSeekBar.setOnSeekBarChangeListener(widthSeekBarChanged);
	      widthSeekBar.setMax( Math.min( (int)( def.getMaxLineWidth() ) , 50 ) );
	      widthSeekBar.setProgress( (int)( def.getLineWidth() ) ); 
	       
	      // set the Set Line Width Button's onClickListener
	      Button setLineWidthButton = 
	         (Button) currentDialog.findViewById(R.id.widthDialogDoneButton);
	      setLineWidthButton.setOnClickListener(setLineWidthButtonListener);
	      
	      dialogIsVisible.set(true); // dialog is on the screen
	      currentDialog.show(); // show the dialog      
	   } // end method showLineWidthDialog
	   
	// OnSeekBarChangeListener for the SeekBar in the width dialog
	   private OnSeekBarChangeListener widthSeekBarChanged = 
	      new OnSeekBarChangeListener() 
	      {
	         //Bitmap bitmap = Bitmap.createBitmap( // create Bitmap
	          //  400, 100, Bitmap.Config.ARGB_8888);
	         //Canvas canvas = new Canvas(bitmap); // associate with Canvas
	         
	         //@Override
	         public void onProgressChanged(SeekBar seekBar, int progress,
	            boolean fromUser) 
	         {  
	            // get the ImageView
	            LineWidthView lineWidthView = (LineWidthView) 
	               currentDialog.findViewById(R.id.lineWidthView);
	            
	            // configure a Paint object for the current SeekBar value
	            //Paint p = new Paint();
	            //p.setColor(def.getDrawingColor());
	            //p.setStrokeCap(Paint.Cap.ROUND);
	            //p.setStrokeWidth(progress);
	            
	            // erase the bitmap and redraw the line
	            //bitmap.eraseColor(Color.WHITE);
	            //canvas.drawLine(30, 30, 370, 70, p);
	            //widthImageView.setImageBitmap(bitmap);
	            lineWidthView.setBackgroundColor( Color.WHITE );
	            lineWidthView.setDrawingColor( def.getDrawingColor() );
	            lineWidthView.setLineWidth( Math.max( progress , def.getMinLineWidth() ) );
	         } // end method onProgressChanged
	   
	         // required method of interface OnSeekBarChangeListener
	         //@Override
	         public void onStartTrackingTouch(SeekBar seekBar) 
	         {
	         } // end method onStartTrackingTouch
	   
	         // required method of interface OnSeekBarChangeListener
	         //@Override
	         public void onStopTrackingTouch(SeekBar seekBar) 
	         {
	         } // end method onStopTrackingTouch
	      }; // end widthSeekBarChanged

	      
	   // OnClickListener for the line width dialog's Set Line Width Button
	   private OnClickListener setLineWidthButtonListener = 
	      new OnClickListener() 
	      {
	         //@Override
	         public void onClick(View v) 
	         {
	            // get the color SeekBars
	            SeekBar widthSeekBar = 
	               (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
	   
	            // set the line color
	            def.setLineWidth( Math.max( widthSeekBar.getProgress() , def.getMinLineWidth() ) );
	            dialogIsVisible.set(false); // dialog is not on the screen
	            currentDialog.dismiss(); // hide the dialog
	            currentDialog = null; // dialog no longer needed
	         } // end method onClick
	      }; // end setColorButtonListener

	      
}

