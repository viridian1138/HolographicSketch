
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


import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

// S3D OpenGL demo of a spinning box
// touch screen to rotate box, use button to toggle S3D mode, slider to zoom in or out
// also illustrates red/cyan anaglyph fallback for non S3D devices/emulators
public class S3DOpenGLActivity extends Activity {
	
	// create menu ids for each menu option 
	
	private static final int DRAW_MODE_MENU_ID = Menu.FIRST;
	private static final int ROTATE_MODE_MENU_ID = Menu.FIRST + 1;
	private static final int DRAW_COLOR_MENU_ID = Menu.FIRST + 2;
	private static final int BKGND_COLOR_MENU_ID = Menu.FIRST + 3;
	private static final int WIDTH_MENU_ID = Menu.FIRST + 4;
	private static final int ERASE_ALL_MENU_ID = Menu.FIRST + 5;
	private static final int ERASE_LAST_TRACE_MENU_ID = Menu.FIRST + 6;
	private static final int STOP_ROTATION_MENU_ID = Menu.FIRST + 7;
	private static final int TOGGLE_SNOW_MODE_MENU_ID = Menu.FIRST + 8;

	
	
	
	
    private S3DGLSurfaceView glSurfaceView;
    final private static String TAG = "S3DOpenGLActivity";
    private Button s3dButton;
    private SeekBar myZoomBar;
    
    private boolean savingState = false;
    
    
    
    protected void stopRotation()
    {
    	glSurfaceView.stopRotation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	savingState = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        glSurfaceView = (S3DGLSurfaceView) findViewById(R.id.glview);
        s3dButton = (Button) findViewById(R.id.button1);
        s3dButton.setText("S3D\nis  ON");
        myZoomBar = (SeekBar) findViewById(R.id.zoomBar);
        myZoomBar.setProgress(3);
        myZoomBar.setOnSeekBarChangeListener(myZoomBarOnSeekBarChangeListener);
        setZoomLevel();
        
        
        Bundle bundle = this.getIntent().getExtras();
        String fname = bundle.getString( "fname" );
        String newD = bundle.getString( "new" );
        
        
        
        if( fname == null )
        {
        	fname = "CurDrawing.skt";
        }
        
        
        if( !( newD != null ) || !( newD.equals( "trueXX" ) ) )
        {
        
        	try
        	{
        		final String fn = fname;
        		final ProgressDialog progressDialog = new ProgressDialog( S3DOpenGLActivity.this );
        		progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        		progressDialog.setMessage( "Loading " + fn + " ..." );
        		progressDialog.setMax( 100 );
        		progressDialog.show();
        		progressDialog.setProgress( 50 );
        		
        		
        		final PersistenceModel[] model = new PersistenceModel[ 1 ];
        		
        		final Handler handler = new Handler()
        		{
        			public void handleMessage( Message msg )
        			{
        				if( model[ 0 ] != null )
        				{
        					glSurfaceView.loadModel( model[ 0 ] );
        				}
        				progressDialog.dismiss();
        			}
        		};
        		
        		final Runnable runn = new Runnable()
        		{
        			public void run()
        			{
        				try
        				{
        					FileInputStream fis = openFileInput( fn ); 
        					ObjectInputStream ois = new ObjectInputStream( fis );
        					PersistenceModel f = (PersistenceModel) ois.readObject(); 
        					model[ 0 ] = f;
        					Message msg = handler.obtainMessage();
        					handler.sendMessage(msg);
        				}
        				catch( Throwable ex )
        				{
        					Log.e(TAG, "Failed", ex);
        					model[ 0 ] = null;
        					Message msg = handler.obtainMessage();
        					handler.sendMessage(msg);
        				}
        			}
        		};
        		
        		
        		( new Thread( runn ) ).start();
        		
        		
        		//FileInputStream fis = openFileInput( fname ); 
        		//ObjectInputStream ois = new ObjectInputStream( fis );
        		//PersistenceModel f = (PersistenceModel) ois.readObject(); 
        		//glSurfaceView.loadModel( f );
        	}
        	catch( Throwable e )
        	{
        		Log.e(TAG, "Failed", e);
        	}
        }
        
        glSurfaceView.setFname( fname );
    }
    
    
    @Override
    public void onBackPressed()
    {
    	savingState = true;
    	( new SaveStateTask() ).execute();
    }

    
    
    class SaveStateTask extends AsyncTask<Void, Void, Void>
    {
    	ProgressDialog progressDialog;
    	
    	@Override
    	protected void onPreExecute()
    	{
    		final String fn = glSurfaceView.getFname();
    		progressDialog = new ProgressDialog( S3DOpenGLActivity.this );
    		progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
    		progressDialog.setMessage( "Saving " + fn + " ..." );
    		progressDialog.setMax( 100 );
    		progressDialog.show();
    		progressDialog.setProgress( 50 );
    	}
    	
    	@Override
    	protected void onPostExecute( Void result )
    	{
    		progressDialog.dismiss();
    	}
    	
    	@Override
    	protected Void doInBackground( Void... arg0 )
    	{
    		glSurfaceView.saveActivityState();
    		S3DOpenGLActivity.this.finish();
    		
    		return( null );
    	}
    	
    };
    

    public void onClickS3DButton(View view) {
        glSurfaceView.toggle();
        if (glSurfaceView.is3Denabled) {
            s3dButton.setText("S3D\nis  ON");
        } else {
            s3dButton.setText("S3D\nis OFF");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        savingState = false;
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        } else {
            Log.d(TAG, "onResume with null glSurfaceView");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
            if( !savingState )
            {
            	glSurfaceView.saveActivityState();
            }
        } else {
            Log.d(TAG, "onPause with null glSurfaceView");
        }
    }

    private void setZoomLevel() {
        int myZoomLevel = myZoomBar.getProgress();
        glSurfaceView.slide(myZoomLevel);
    }

    private SeekBar.OnSeekBarChangeListener myZoomBarOnSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {

                public void onProgressChanged(SeekBar seekBar, int progress,
                        boolean fromUser) {
                    setZoomLevel();
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            };
            
         // displays configuration options in menu
         @Override
         public boolean onCreateOptionsMenu(Menu menu) 
            {
               super.onCreateOptionsMenu(menu); // call super's method

               // add options to menu
               menu.add(Menu.NONE, DRAW_MODE_MENU_ID, Menu.NONE, 
                       R.string.menuitem_draw_mode);
               menu.add(Menu.NONE, ROTATE_MODE_MENU_ID, Menu.NONE, 
                       R.string.menuitem_rotate_mode);
               menu.add(Menu.NONE, DRAW_COLOR_MENU_ID, Menu.NONE, 
                  R.string.menuitem_draw_color);
               menu.add(Menu.NONE, BKGND_COLOR_MENU_ID, Menu.NONE, 
                  R.string.menuitem_bkgnd_color);
               menu.add(Menu.NONE, WIDTH_MENU_ID, Menu.NONE, 
                  R.string.menuitem_line_width);
               menu.add(Menu.NONE, ERASE_ALL_MENU_ID, Menu.NONE, 
                       R.string.menuitem_erase_all);
               menu.add(Menu.NONE, ERASE_LAST_TRACE_MENU_ID, Menu.NONE, 
                       R.string.menuitem_erase_last_trace);
               menu.add(Menu.NONE, STOP_ROTATION_MENU_ID, Menu.NONE, 
                       R.string.menuitem_stop_rotation);
               menu.add(Menu.NONE, TOGGLE_SNOW_MODE_MENU_ID, Menu.NONE, 
                       R.string.menuitem_toggle_snow_mode);
               //menu.add(Menu.NONE, ERASE_MENU_ID, Menu.NONE, 
               //   R.string.menuitem_erase);
               //menu.add(Menu.NONE, SAVE_MENU_ID, Menu.NONE, 
               //   R.string.menuitem_save_image);

               return true; // options menu creation was handled
            } // end onCreateOptionsMenu
         
         
         
      // handle choice from options menu
         @Override
         public boolean onOptionsItemSelected(MenuItem item) 
         {
            // switch based on the MenuItem id
            switch (item.getItemId()) 
            {
              case DRAW_MODE_MENU_ID:
                 setDrawMode(); // display color selection dialog
                 return true; // consume the menu event
              case ROTATE_MODE_MENU_ID:
                 setRotateMode(); // display color selection dialog
                 return true; // consume the menu event
               case DRAW_COLOR_MENU_ID:
                  showDrawColorDialog(); // display color selection dialog
                  return true; // consume the menu event
               case BKGND_COLOR_MENU_ID:
                   showBkgndColorDialog(); // display color selection dialog
                   return true; // consume the menu event
               case WIDTH_MENU_ID:
                  showLineWidthDialog(); // display line thickness dialog
                  return true; // consume the menu event
               case ERASE_ALL_MENU_ID:
                   eraseAll(); // display line thickness dialog
                   return true; // consume the menu event
               case ERASE_LAST_TRACE_MENU_ID:
                   eraseLastTrace(); // display line thickness dialog
                   return true; // consume the menu event
               case STOP_ROTATION_MENU_ID:
                   stopRotation(); // display line thickness dialog
                   return true; // consume the menu event
               case TOGGLE_SNOW_MODE_MENU_ID:
                   toggleSnowMode(); // display line thickness dialog
                   return true; // consume the menu event
               //case ERASE_MENU_ID:
               //   doodleView.setDrawingColor(Color.WHITE); // line color white
               //   return true; // consume the menu event
               //case SAVE_MENU_ID:     
               //   doodleView.saveImage(); // save the current images
               //   return true; // consume the menu event
            } // end switch
            
            return super.onOptionsItemSelected(item); // call super's method
         } // end method onOptionsItemSelected
         
         
         public void setDrawMode()
         {
        	 glSurfaceView.setDrawMode();
         }
         
         public void setRotateMode()
         {
        	 glSurfaceView.setRotateMode();
         }
         
         public void toggleSnowMode()
         {
        	 glSurfaceView.toggleSnowMode();
         }
         
         public void eraseAll()
         {
        	LayoutInflater li = LayoutInflater.from( this );
         	View view = li.inflate( R.layout.erase_all_dialog , null);
         	
         	AlertDialog.Builder builder = new AlertDialog.Builder( this );
         	builder.setTitle( "Erase All ?" );
         	builder.setView(view);
         	
         	android.content.DialogInterface.OnClickListener pl = new android.content.DialogInterface.OnClickListener()
         	{
         		public void onClick( DialogInterface v , int buttonId )
         		{
         			if( buttonId == DialogInterface.BUTTON_POSITIVE )
         			{
         				glSurfaceView.eraseAll();
         			}
         		}
         	};
         	
         	builder.setPositiveButton( "OK" , pl );
         	builder.setNegativeButton( "Cancel" , pl );
         	
         	AlertDialog ad = builder.create();
         	
         	ad.show(); 
         }
         
         public void eraseLastTrace()
         {
        	 LayoutInflater li = LayoutInflater.from( this );
          	View view = li.inflate( R.layout.erase_last_trace_dialog , null);
          	
          	AlertDialog.Builder builder = new AlertDialog.Builder( this );
          	builder.setTitle( "Erase All ?" );
          	builder.setView(view);
          	
          	android.content.DialogInterface.OnClickListener pl = new android.content.DialogInterface.OnClickListener()
          	{
          		public void onClick( DialogInterface v , int buttonId )
          		{
          			if( buttonId == DialogInterface.BUTTON_POSITIVE )
          			{
          				glSurfaceView.eraseLastTrace();
          			}
          		}
          	};
          	
          	builder.setPositiveButton( "OK" , pl );
          	builder.setNegativeButton( "Cancel" , pl );
          	
          	AlertDialog ad = builder.create();
          	
          	ad.show(); 
         }
         
         
         public void showLineWidthDialog()
         {
        	 ILineWidthDef def = glSurfaceView.createLineWidthDef();
        	 LineWidthDialog dialog = new LineWidthDialog( def );
        	 dialog.showLineWidthDialog( this );
         }
         
         
         protected void showDrawColorDialog()
         {
        	 IColorDef cdef = glSurfaceView.createDrawingColorDef();
        	 ColorDialog dialog = new ColorDialog();
        	 dialog.showColorDialog( this , cdef );
         }
         
         
         protected void showBkgndColorDialog()
         {
        	 IColorDef cdef = glSurfaceView.createBkgndColorDef();
        	 ColorDialog dialog = new ColorDialog();
        	 dialog.showColorDialog( this , cdef );
         }
         
}

