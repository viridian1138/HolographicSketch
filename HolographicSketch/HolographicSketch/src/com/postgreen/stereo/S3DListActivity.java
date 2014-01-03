
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

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

// main S3D demo entry point - simple string array list launched demo activities
// see the 4 activitie's source code for more information
public class S3DListActivity extends ListActivity {

	private static final int ERASE_SKETCH_MENU_ID = Menu.FIRST;
	private static final int LOAD_URL_MENU_ID = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		createClickMe();

		final String[] fileList = fileList();

		final String[] menu = new String[fileList.length + 1];
		menu[0] = "Create New Sketch...";
		for (int count = 0; count < fileList.length; count++) {
			menu[count + 1] = fileList[count];
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu));
		setContentView(R.layout.list);
		
		new SimpleEula(this).show();
	}

	public void refreshList() {
		final String[] fileList = fileList();

		final String[] menu = new String[fileList.length + 1];
		menu[0] = "Create New Sketch...";
		for (int count = 0; count < fileList.length; count++) {
			menu[count + 1] = fileList[count];
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu));
	}

	@Override
	protected void onResume() {
		super.onResume();
		final String[] fileList = fileList();

		final String[] menu = new String[fileList.length + 1];
		menu[0] = "Create New Sketch...";
		for (int count = 0; count < fileList.length; count++) {
			menu[count + 1] = fileList[count];
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menu));
		setContentView(R.layout.list);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		long rowId = getListAdapter().getItemId(position);
		String item = (String) getListAdapter().getItem(position);
		if (rowId == 0) {
			LayoutInflater li = LayoutInflater.from(this);
			final View view = li.inflate(R.layout.file_dialog, null);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("New File");
			builder.setView(view);

			
			android.content.DialogInterface.OnClickListener pl = new android.content.DialogInterface.OnClickListener()
			{
				
				public void onClick( DialogInterface v , int buttonId )
				{
					if( buttonId == DialogInterface.BUTTON_POSITIVE )
					{
			        	
			        	EditText et = (EditText)( view.findViewById( R.id.fileText_prompt ) );
			        	
			        	String itema = et.getText().toString().trim();
			        	
			        	String item = "";
			        	
			        	for( int count = 0 ; count < itema.length() ; count++ )
			        	{
			        		char cn = itema.charAt( count );
			        		if( Character.isLetterOrDigit( cn ) )
			        		{
			        			item = item + cn;
			        		}
			        	}
			        	
			        	
			        	if( item.equals( "" ) )
			        	{
			        		item = "CurDrawing";
			        	}
			        	
			        	if( !( item.endsWith( ".skt" ) ) )
			        	{
			        		item = item + ".skt";
			        	}
			        	
			        	
			        	try {
			                Intent intent = new Intent( S3DListActivity.this , S3DOpenGLActivity.class );
			                S3DListActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
			                
			                // startActivity(intent);
			                
			                Bundle bundle = new Bundle();
			                bundle.putString( "fname" , item );
			                bundle.putString( "new" , "trueXX" );


			                intent.putExtras(bundle);
			                S3DListActivity.this.startActivityForResult(intent, 0);
			                
			            } catch (Throwable  e) {
			                e.printStackTrace();
			            }
					}
					else
					{
						return;
					}
				}
				
			};
			
			builder.setPositiveButton("OK", pl);
			builder.setNegativeButton("Cancel", pl);

			AlertDialog ad = builder.create();

			ad.show();

			return;
		}
		try {
			// Toast toast = Toast.makeText(this, "Loading " + item + " ..." ,
			// 2000);
			// toast.setGravity(Gravity.TOP, -30, 50);
			// toast.show();

			Intent intent = new Intent(this, S3DOpenGLActivity.class);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);

			// startActivity(intent);

			Bundle bundle = new Bundle();
			bundle.putString("fname", item);

			intent.putExtras(bundle);
			startActivityForResult(intent, 0);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	// displays configuration options in menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu); // call super's method

		// add options to menu
		menu.add(Menu.NONE, ERASE_SKETCH_MENU_ID, Menu.NONE,
				R.string.menuitem_erase_sketch);
		menu.add(Menu.NONE, LOAD_URL_MENU_ID, Menu.NONE,
				R.string.menuitem_load_url);

		return true; // options menu creation was handled
	} // end onCreateOptionsMenu

	// handle choice from options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch based on the MenuItem id
		switch (item.getItemId()) {
		case ERASE_SKETCH_MENU_ID:
			eraseSketchSelected(); // display color selection dialog
			return true; // consume the menu event
		case LOAD_URL_MENU_ID:
			loadUrl(); // display color selection dialog
			return true; // consume the menu event
		} // end switch

		return super.onOptionsItemSelected(item); // call super's method
	} // end method onOptionsItemSelected

	private void eraseSketchSelected() {
		final String[] fileList = fileList();
		EraseSketchDialog dialog = new EraseSketchDialog();
		dialog.showEraseSketchDialog(fileList, this);
	}

	private void createClickMe() {
		try {
			PersistenceModel pm = new PersistenceModel();

			pm.setDrawingColor(Color.argb(255, 128, 0, 255));

			pm.setBackgroundColor(Color.argb(255, 255, 255, 158));

			final float INITYROT = 0.2f;

			final float INITYSPEED = 0.8f;

			pm.setRotMat(InvertibleTransformationMat.buildRotationMat(0.0,
					INITYROT, 0.0));

			pm.setSpeedMat(InvertibleTransformationMat.buildRotationMat(0.0,
					INITYSPEED, 0.0));

			ArrayList<FlexPoly> drawList = new ArrayList<FlexPoly>();

			FlexPoly poly = new FlexPoly();

			poly.setDrawingColor(Color.argb(255, 255, 0, 128));
			poly.setLineWidth(3.0f);

			Random rand = new Random(747L);
			int count;
			double[] p0;
			double[] p1 = { rand.nextDouble() * 4.0 - 2.0,
					rand.nextDouble() * 4.0 - 2.0,
					rand.nextDouble() * 4.0 - 2.0 };
			for (count = 0; count < 15; count++) {
				p0 = p1;
				p1 = new double[] { rand.nextDouble() * 4.0 - 2.0,
						rand.nextDouble() * 4.0 - 2.0,
						rand.nextDouble() * 4.0 - 2.0 };
				poly.add(p0, p1);
			}

			drawList.add(poly);

			pm.setDrawList(drawList);

			if ((Arrays.asList(fileList())).contains("ClickMe.skt")) {
				return;
			}

			FileOutputStream fos = openFileOutput("ClickMe.skt", MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(pm);
			oos.close();
		} catch (Throwable ex) {
			ex.printStackTrace(System.out);
		}

	}

	private void loadUrl() {
		final LayoutInflater li = LayoutInflater.from(this);
		final View view = li.inflate(R.layout.load_url_dialog, null);

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Load URL");
		builder.setView(view);

		final android.content.DialogInterface.OnClickListener pl = new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface v, int buttonId) {
				if (buttonId == DialogInterface.BUTTON_POSITIVE) {
					final EditText et = (EditText) (view
							.findViewById(R.id.urlText_prompt));

					final String urlPmpt = et.getText().toString().trim();

					loadUrl(urlPmpt);
				} else {
					return;
				}
			}
		};

		builder.setPositiveButton("OK", pl);
		builder.setNegativeButton("Cancel", pl);

		AlertDialog ad = builder.create();

		ad.show();
	}

	private String extractFname(String urlPmptA) {
		int index = -1;
		for (int count = 0; count < urlPmptA.length(); count++) {
			if (urlPmptA.charAt(index) == '/') {
				index = count;
			}
		}

		if (index < 0) {
			throw (new RuntimeException("Fail"));
		}

		String ret = "";

		for (int count = index + 1; count < urlPmptA.length(); count++) {
			final char ch = urlPmptA.charAt(index);
			if (Character.isLetter(ch)) {
				ret = ret + ch;
			}
		}

		if (ret.length() == 0) {
			throw (new RuntimeException("Fail"));
		}

		return (ret);
	}

	private void loadUrl(final String urlPmpt) {
		try {
			final ProgressDialog progressDialog = new ProgressDialog(
					S3DListActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			String urlPmptA = urlPmpt;
			if (!(urlPmptA.endsWith(".skt"))) {
				urlPmptA = urlPmptA + ".skt";
			}
			final URL url = new URL(urlPmptA);
			final String urlPmptB = urlPmptA;
			progressDialog.setMessage("Loading " + urlPmptA + " ...");
			progressDialog.setMax(100);
			progressDialog.show();
			progressDialog.setProgress(50);
			
			final boolean[] success = { false };

			final Handler handler = new Handler() {
				public void handleMessage(Message msg) {
					refreshList();
					progressDialog.dismiss();
					if( !( success[ 0 ] ) )
					{
						final Toast toast = Toast.makeText(S3DListActivity.this, "Load Failed.", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
						toast.show();
					}
				}
			};

			final Runnable runn = new Runnable() {
				public void run() {
					try {

						final URLConnection myCon = url.openConnection();
						final InputStream is = myCon.getInputStream();
						final ObjectInputStream ois = (ObjectInputStream) is;
						final PersistenceModel pm = (PersistenceModel) (ois
								.readObject());
						ois.close();

						final String fname = extractFname(urlPmptB);

						FileOutputStream fos = openFileOutput(fname,
								MODE_PRIVATE);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(pm);
						oos.close();
						success[ 0 ] = true;
						Message msg = handler.obtainMessage();
    					handler.sendMessage(msg);
					} catch (Throwable ex) {
						Log.e("Lst", "LstFail", ex);
						Message msg = handler.obtainMessage();
    					handler.sendMessage(msg);
					}
				}
			};

			(new Thread(runn)).start();

		} catch (Throwable ex2) {
			Log.e("Lst", "LstFail", ex2);
		}
	}
	

}

