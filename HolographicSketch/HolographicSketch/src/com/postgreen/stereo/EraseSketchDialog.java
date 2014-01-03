



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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EraseSketchDialog {
	
	// variable that refers to a Choose Color or Choose Line Width dialog
		private Dialog currentDialog; 
		
		private AtomicBoolean dialogIsVisible = new AtomicBoolean(); // false
	
		private String[] fileList;
		
	   
    // display a dialog for selecting a sketch to erase
       public void showEraseSketchDialog( String[] _fileList , final S3DListActivity activity )
       {
    	   fileList = _fileList;
    	   
          // create the dialog and inflate its content
          currentDialog = new Dialog( activity );
          currentDialog.setContentView(R.layout.erase_sketch_dialog);
          currentDialog.setTitle(R.string.title_erase_sketch_dialog);
          currentDialog.setCancelable(true);
          
          final ListView eraseSketch_List = (ListView)( currentDialog.findViewById( R.id.eraseSketch_list ) );
          
          final Button cancelButton = (Button)( currentDialog.findViewById( R.id.cancelEraseSketchButton ) );
          
          
          
          eraseSketch_List.setAdapter( new ArrayAdapter<String>( activity , android.R.layout.simple_list_item_1 , fileList) );
          
          
          eraseSketch_List.setOnItemClickListener(
        		  new OnItemClickListener()
        		  {
        			  public void onItemClick( AdapterView<?> arg0 , View arg1 , int arg2 , long arg3 )
        			  {
        				  dialogIsVisible.set(false); // dialog is not on the screen
        		          currentDialog.dismiss(); // hide the dialog
        		          currentDialog = null; // dialog no longer needed
        		          
        		          final long rowId = arg3;
        		          
        		          final String row = fileList[ (int)( rowId ) ];
        		          
        		          LayoutInflater li = LayoutInflater.from( activity );
        		         	View view = li.inflate( R.layout.erase_sketch_confirm_dialog , null);
        		         	
        		         	AlertDialog.Builder builder = new AlertDialog.Builder( activity );
        		         	builder.setTitle( "Erase Sketch " + row + " ?" );
        		         	builder.setView(view);
        		         	
        		         	android.content.DialogInterface.OnClickListener pl = new android.content.DialogInterface.OnClickListener()
        		         	{
        		         		public void onClick( DialogInterface v , int buttonId )
        		         		{
        		         			if( buttonId == DialogInterface.BUTTON_POSITIVE )
        		         			{
        		         				boolean success = activity.deleteFile( row );
        		         				
        		         				Log.d( "success" , "" + success );
        		         				
        		         				activity.refreshList();
        		         			}
        		         		}
        		         	};
        		         	
        		         	builder.setPositiveButton( "OK" , pl );
        		         	builder.setNegativeButton( "Cancel" , pl );
        		         	
        		         	AlertDialog ad = builder.create();
        		         	
        		         	ad.show(); 
        			  }
        		  } );
          
          
          
          
          cancelButton.setOnClickListener( cancelEraseSketchButtonListener );
          
     
          dialogIsVisible.set(true); // dialog is on the screen
          currentDialog.show(); // show the dialog
       } // end method showEraseSketchDialog
       
       
    // OnClickListener for the color dialog's Set Color Button
       private OnClickListener cancelEraseSketchButtonListener = new OnClickListener() 
       {
          //@Override
          public void onClick(View v) 
          {
             dialogIsVisible.set(false); // dialog is not on the screen
             currentDialog.dismiss(); // hide the dialog
             currentDialog = null; // dialog no longer needed
          } // end method onClick
       }; // end setColorButtonListener
       
    


}

