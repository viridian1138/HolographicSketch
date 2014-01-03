
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
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.htc.view.DisplaySetting;

//surface view used in the S3DOpenGLExample
public class S3DGLSurfaceView extends GLSurfaceView implements Renderer {

    public boolean is3Denabled = true;
    public boolean is3Dsupported = true;

    private static final String TAG = "S3DGLSurfaceView";
    
    public enum ModeType { ZOOM, DRAG, DRAW };
    
    public enum MirrorType { NONE , HORIZ , VERT , HORIZ_VERT , DIAG1 , DIAG2 }; 

    
    private int width;
    private int height;

    
    private static final float INITYROT = 0.2f;
    
    
    private InvertibleTransformationMat rotMat = InvertibleTransformationMat.buildRotationMat( 0.0 , INITYROT, 0.0 );
    
    
    private float INITYSPEED = 0.8f;
    
    
    private InvertibleTransformationMat speedMat = InvertibleTransformationMat.buildRotationMat( 0.0 , INITYSPEED , 0.0 );
    
    
    private InvertibleTransformationMat tmpSpeedMat = new InvertibleTransformationMat();
    
    

    private float z = 0.0f;

    private float oldX;
    private float oldY;
    private final float scaleFactor = 0.4f;
    private Context context;
    private SurfaceHolder holder;
    private float oldDist;
    
    private volatile boolean useDrawMode = true;
    private FloatBuffer vertexBuffer = null;
    
    private ModeType mode = ModeType.ZOOM;
    
    private MirrorType mirrorType = MirrorType.NONE;
    
    private boolean snowMode = false;
    
    
    private FlexPoly tmpPoly = null;
    private ArrayList<FlexPoly> drawList = new ArrayList<FlexPoly>();
    
    
    private final int CSZ = 5;
    private final double[] xrots = new double[ CSZ ];
    private final double[] yrots = new double[ CSZ ];
    private final double[] zrots = new double[ CSZ ];
    private final boolean[] brots = new boolean[ CSZ ];
    
    
    private float lineWidth = 3.0f;
    
    private final IntBuffer maxLineWidth = IntBuffer.allocate( 2 );
    
    
    private volatile int drawingColor = Color.argb(
            255 , 128 , 0 , 255 );
    
    private volatile int backgroundColor = Color.argb(
            255 , 255 , 255 , 158 );
    
    
    private String fname = "CurDrawing.skt";
    
    
    
    public void stopRotation()
    {
    	speedMat = new InvertibleTransformationMat();
    }
    
    protected ModeType useDragDrawMode()
    {
    	return( useDrawMode ? ModeType.DRAW : ModeType.DRAG );
    }

    public S3DGLSurfaceView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public S3DGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
        this.setRenderer(this);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
        setBackgroundColor( backgroundColor );
        
        float vertices[] = {
        	      -1.0f,  1.0f, 0.0f,  // 0, Top Left
        	      -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
        	      
        	      -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
        	      1.0f, -1.0f, 0.0f,  // 2, Bottom Right
        	      
        	       1.0f, -1.0f, 0.0f,  // 2, Bottom Right
        	       1.0f,  1.0f, 0.0f,  // 3, Top Right
        	       
        	       1.0f,  1.0f, 0.0f,  // 3, Top Right
        	       -1.0f,  1.0f, 0.0f,  // 0, Top Left
        	};
        
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // box.loadGLTexture(gl, context);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glShadeModel(GL10.GL_SMOOTH);
        
        final int color = getBackgroundColor();
        // gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        gl.glClearColor( (float)( Color.red(color) / 255.0 ) , 
        		(float)( Color.green(color) / 255.0 ) , 
        		(float)( Color.blue(color) / 255.0 ) , 
        		(float)( Color.alpha(color) / 255.0 ) );
        
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
        Log.d(TAG, "w=" + w + " h=" + h);
    }

    private void draw(GL10 gl) {
    	
       // gl.glTranslatef(z, 0.0f, 0.0f);
       // gl.glScalef(0.8f, 0.8f, 0.8f);

       // gl.glRotatef(xrot, 0.0f, 0.0f, 1.0f);
       // gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
       // gl.glRotatef(zrot, 1.0f, 0.0f, 0.0f);
    	
    	// InvertibleTransformationMat tr = new InvertibleTransformationMat( /* z */ -2.6 );
    	// InvertibleTransformationMat tr = new InvertibleTransformationMat( -z );
    	InvertibleTransformationMat tr = new InvertibleTransformationMat( /* z */ -3.6 );
    	InvertibleTransformationMat tr2 = new InvertibleTransformationMat();
    	
    	tr.multMat( rotMat , tr2 );
    	
    	tr2.applyToGl(gl);
    	
    	// gl.glTranslatef(z, 0.0f, 0.0f);
    	
    	gl.glScalef(0.8f, 0.8f, 0.8f);
        
    	// gl.glScalef( 0.08f , 0.08f , 0.08f );
    	
    	

     // Enabled the vertex buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        //gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        
        final int color = Color.argb(255 , 128 , 255 , 255 );
        gl.glColor4f( 
        		(float)( Color.red(color) / 255.0 ) , 
        		(float)( Color.green(color) / 255.0 ) , 
        		(float)( Color.blue(color) / 255.0 ) , 
        		(float)( Color.alpha(color) / 255.0 ) );
        
        gl.glLineWidth( 3.0f );

        
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); // OpenGL docs.
        
        gl.glDrawArrays(GL10.GL_LINES, 0, 8);
        
        
        int count;
        for( count = 0 ; count < drawList.size() ; count++ )
        {
        	drawList.get( count ).draw(gl);
        }

        
     // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        //gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        int error = gl.glGetError(); if (error != GL10.GL_NO_ERROR) { Log.d(TAG, "err=" + error); }
        
        final InvertibleTransformationMat res = new InvertibleTransformationMat();
        speedMat.multMat( rotMat , res );
        rotMat = res;
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (h == 0) {
            h = 1;
        }

        Log.d(TAG, "h=" + h + " h=" + h);

        width = w;
        height = h;

        initCameraProjection(width, height);

        enableS3D(is3Denabled);

        float ratio = (float) width / (float) height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);

        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    public void onDrawFrame(GL10 gl) {
        float left, right, top, bottom;
        
        gl.glClearColor( (float)( Color.red( backgroundColor ) / 255.0 ) , 
        		(float)( Color.green( backgroundColor ) / 255.0 ) , 
        		(float)( Color.blue( backgroundColor ) / 255.0 ) , 
        		(float)( Color.alpha( backgroundColor ) / 255.0 ) );

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glColorMask(true, true, true, true);
        
        gl.glGetIntegerv( GL10.GL_ALIASED_LINE_WIDTH_RANGE , maxLineWidth );

        if (is3Denabled) {
            // LEFT
            if (is3Dsupported) {
                gl.glViewport(0, 0, (int) width / 2, (int) height);
            } else {
                gl.glColorMask(true, false, false, true);
            }
        } else {
            gl.glViewport(0, 0, (int) width, (int) height);
        }
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        left = (float) (-ratio * wd2 + 0.5 * camera.eyeSeparation * ndfl);
        right = (float) (ratio * wd2 + 0.5 * camera.eyeSeparation * ndfl);
        top = wd2;
        bottom = -wd2;
        gl.glFrustumf(left, right, bottom, top, near, far);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, camera.viewPos[0] - r[0], camera.viewPos[1] - r[1], camera.viewPos[2]
                - r[2],
                camera.viewPos[0] - r[0] + camera.viewDirection[0],
                camera.viewPos[1] - r[1] + camera.viewDirection[1],
                camera.viewPos[2] - r[2] + camera.viewDirection[2],
                camera.viewUp[0], camera.viewUp[1], camera.viewUp[2]);
        draw(gl);

        if (is3Denabled) {
            // RIGHT
            if (is3Dsupported) {
                gl.glViewport((int) width / 2, 0, (int) width / 2, (int) height);
            } else {
                gl.glClear(GL10.GL_DEPTH_BUFFER_BIT);
                gl.glColorMask(true, true, true, true);
                gl.glColorMask(false, true, true, true);
                // gl.glEnable(GL10.GL_BLEND);
                // gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
            }

            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            left = (float) (-ratio * wd2 - 0.5 * camera.eyeSeparation * ndfl);
            right = (float) (ratio * wd2 - 0.5 * camera.eyeSeparation * ndfl);
            top = wd2;
            bottom = -wd2;
            gl.glFrustumf(left, right, bottom, top, near, far);

            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            GLU.gluLookAt(gl, camera.viewPos[0] + r[0], camera.viewPos[1] + r[1], camera.viewPos[2]
                    + r[2],
                    camera.viewPos[0] + r[0] + camera.viewDirection[0],
                    camera.viewPos[1] + r[1] + camera.viewDirection[1],
                    camera.viewPos[2] + r[2] + camera.viewDirection[2],
                    camera.viewUp[0], camera.viewUp[1], camera.viewUp[2]);
            draw(gl);
        }
    }
    
    // @Override
    // public void onPause()
    
   
    
    public void saveActivityState() {
    	try
    	{
    		is3Denabled = false;
    		enableS3D(is3Denabled);
        
    		PersistenceModel model = generateModel();
        
    		FileOutputStream fos = context.openFileOutput(fname, context.MODE_PRIVATE);
    		ObjectOutputStream oos = new ObjectOutputStream(fos); 
    		oos.writeObject(model); 
    		oos.close();
    	}
    	catch( Throwable e )
    	{
    		Log.e(TAG, "Failure", e);
    	}
        
    }
   
    
    
    public PersistenceModel generateModel()
    {
    	PersistenceModel model = new PersistenceModel();
    	
    	model.setRotMat( rotMat );
    	
    	model.setSpeedMat( speedMat );
    	
    	model.setDrawList( drawList );
    	
    	model.setLineWidth( lineWidth );
        
        model.setDrawingColor( drawingColor );
        
        model.setBackgroundColor( backgroundColor );
    	
    	return( model );
    }
    
    
    
    public void loadModel( PersistenceModel m )
    {
    	
    	rotMat = m.getRotMat( );
    	
    	speedMat = m.getSpeedMat( );
    	
    	drawList = m.getDrawList( );
    	
    	lineWidth = (float)( m.getLineWidth( ) );
        
        drawingColor = m.getDrawingColor( );
        
        backgroundColor = m.getBackgroundColor( );
    	
    }
    
    

    private float pinch(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    protected void slide(/* int zoom */ int slideValue ) {
        Log.d(TAG, "MIRROR to " + slideValue);
        
        if( slideValue >= 5 )
        {
        	mirrorType = MirrorType.HORIZ_VERT;
        	return;
        }
        
        if( slideValue == 0 )
        {
        	mirrorType = MirrorType.HORIZ;
        	return;
        }
        
        if( slideValue == 1 )
        {
        	mirrorType = MirrorType.VERT;
        	return;
        }
        
        if( slideValue == 2 )
        {
        	mirrorType = MirrorType.DIAG1;
        	return;
        }
        
        if( slideValue == 4 )
        {
        	mirrorType = MirrorType.DIAG2;
        	return;
        }
        
        mirrorType = MirrorType.NONE;

        
        //float newZoom = zoom;

        //float zfactor = (float) (newZoom * scaleFactor);

        //if (zoom > 3)
        //{
            // z -= zfactor;
        //    z = (float) (-zfactor + (3 * scaleFactor));
        //}
        //else if (zoom < 3)
        //{
            // z += zfactor;
        //    z = (float) ((3 * scaleFactor) - zfactor);
        //}
        //else
        //    z = 0;
        //
        //Log.d(TAG, "zfactor = " + zfactor + ", z = " + z);
        //
        // limit zoom
        //Log.d(TAG, "z=" + z);
        //if (z > 2.6f) {
        //    Log.d(TAG, "z is above threshold");
        //    z = 2.6f;
        //} else if (z < -2.6f) {
        //    Log.d(TAG, "z is below threshold");
        //    z = -2.6f;
        //}
        //
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	mode = useDragDrawMode();
            	tmpSpeedMat = speedMat;
            	drCnt = -1;
            	if( mode != ModeType.DRAW )
            	{
            		speedMat = new InvertibleTransformationMat();
            	}
            	if( mode == ModeType.DRAG )
            	{
            		int count;
            		for( count = 0 ; count < CSZ ; count++ )
            		{
            			brots[ count ] = false;
            		}
            	}
            	if( mode == ModeType.DRAW )
            	{
            		tmpPoly = new FlexPoly();
            		tmpPoly.setDrawingColor( getDrawingColor() );
            		tmpPoly.setLineWidth( lineWidth );
            		drawList.add( tmpPoly );
            	}
                break;
            case MotionEvent.ACTION_UP:
            	if( mode == ModeType.DRAG )
            	{
            		int count;
            		int scnt = 0;
            		double dxa = 0.0;
            		double dya = 0.0;
            		double dza = 0.0;
            		for( count = 0 ; count < CSZ ; count++ )
            		{
            			if( brots[ count ] )
            			{
            				scnt++;
            				dxa += xrots[ count ];
            				dya += yrots[ count ];
            				dza += zrots[ count ];
            			}
            		}
            		if( scnt > 0 )
            		{
            			speedMat = InvertibleTransformationMat.buildRotationMat( dxa / scnt , dya / scnt , dza / scnt );
            		}
            	}
            	else
            	{
            		speedMat = tmpSpeedMat;
            	}
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = useDragDrawMode();
                oldDist = pinch(event);
                Log.d(TAG, "oldDist=" + oldDist);
                // if (oldDist > 10f) { !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //    mode = ModeType.ZOOM;
                //    Log.d(TAG, "mode=ZOOM");
                //}
                break;
            case MotionEvent.ACTION_MOVE:
            {
                float dx = x - oldX;
                float dy = y - oldY;
                switch( mode )
                {
                	case ZOOM:
                	{
                		float newDist = pinch(event);
                        if (newDist > 10f) {
                            float zfactor = dx * scaleFactor / 2;
                            z -= zfactor;
                            // limit zoom
                            Log.d(TAG, "z=" + z);
                            if (z > 2.6f) {
                                z = 2.6f;
                            } else if (z < -2.6f) {
                                z = -2.6f;
                            }
                        }
                	}
                	break;
                	
                	case DRAG:
                	{
                		final InvertibleTransformationMat r = 
                				InvertibleTransformationMat.buildRotationMat( 0.0 , dx * scaleFactor , dy * scaleFactor );
                		final InvertibleTransformationMat res = new InvertibleTransformationMat();
                		r.multMat( rotMat , res );
                		rotMat = res;
                        int count;
                        for( count = 1 ; count < CSZ ; count++ )
                        {
                        	xrots[ count ] = xrots[ count - 1 ];
                        	yrots[ count ] = yrots[ count - 1 ];
                        	zrots[ count ] = zrots[ count - 1 ];
                        	brots[ count ] = brots[ count - 1 ];
                        }
                        xrots[ 0 ] = 0.0;
                        yrots[ 0 ] = dx * scaleFactor;
                        zrots[ 0 ] = dy * scaleFactor;
                        brots[ 0 ] = true;
                	}
                	break;
                	
                	case DRAW:
                	{
                		final double dScale = 1.25 * 1.5;
                		final double width = getWidth();
                		final double height = getHeight();
                		final double width2 = width / 2.0;
                		final double height2 = height / 2.0;
                		final double x0 = dScale * ( - ( ( oldX - width2 ) / height2 ) );
                		final double y0 = dScale * ( - ( ( oldY - height2 ) / height2 )  );
                		final double x1 = dScale * ( - ( ( x - width2 ) / height2 ) );
                		final double y1 = dScale * ( - ( ( y - height2 ) / height2 ) );
                		final double[] pi0 = { - x0 , y0 , 0.0 };
                		final double[] pi1 = { - x1 , y1 , 0.0 };
                		handlePolyAdd( 0 , pi0 , pi1 );
                		switch( mirrorType )
                		{
                			case HORIZ_VERT:
                			{
                				final double[] pi0a = { - x0 , - y0 , 0.0 };
                        		final double[] pi1a = { - x1 , - y1 , 0.0 };
                        		handlePolyAdd( 1 , pi0a , pi1a );
                        		
                        		final double[] pi0b = { x0 , - y0 , 0.0 };
                        		final double[] pi1b = { x1 , - y1 , 0.0 };
                        		handlePolyAdd( 2 , pi0b , pi1b );
                			}
                			// Drop Through
                				
                			case HORIZ:
                			{
                				final double[] pi0a = { x0 , y0 , 0.0 };
                        		final double[] pi1a = { x1 , y1 , 0.0 };
                        		handlePolyAdd( 3 , pi0a , pi1a );
                			}
                				break;
                				
                			case VERT:
                			{
                				final double[] pi0a = { - x0 , - y0 , 0.0 };
                        		final double[] pi1a = { - x1 , - y1 , 0.0 };
                        		handlePolyAdd( 1 , pi0a , pi1a );
                			}
                				break;
                				
                			case DIAG2:
                			{
                				final double MULTA = 960.0 / 540.0;
                				final double MULTB = 1.0 / MULTA;
                				
                				final double[] pi0a = { y0 * MULTA , - x0 * MULTB , 0.0 };
                        		final double[] pi1a = { y1 * MULTA , - x1 * MULTB , 0.0 };
                        		handlePolyAdd( 1 , pi0a , pi1a );
                        		
                        		final double[] pi0b = { - y0 * MULTA , x0 * MULTB , 0.0 };
                        		final double[] pi1b = { - y1 * MULTA , x1 * MULTB , 0.0 };
                        		handlePolyAdd( 2 , pi0b , pi1b );
                			}
                			// Drop Through
                				
                			case DIAG1:
                			{
                				final double[] pi0a = { x0 , - y0 , 0.0 };
                        		final double[] pi1a = { x1 , - y1 , 0.0 };
                        		handlePolyAdd( 3 , pi0a , pi1a );
                			}
                				break;
                				
                			default:
                				break;
                		}
                	}
                	break;
                	
                	default:
                	{
                		Log.d(TAG, "CaseNotFound");
                	}
                	break;
                	
                };
            }       
                break;
        }
        drCnt++;
        oldX = x;
        oldY = y;
        return true;
    }
    
    
    protected int drCnt = -1;
    
    protected double[] tmpMirrorVals = new double[ 12 ];
    
    
    protected void handlePolyAdd( int index , double[] pi0 , double[] pi1 )
    {
    	final double[] p0 = new double[ 3 ];
		final double[] p1 = new double[ 3 ];
		final int BASE = 3 * index;
		if( ( drCnt == 0 ) || snowMode )
		{
			rotMat.multVectInverse( pi0 , p0 );
			rotMat.multVectInverse( pi1 , p1 );
		}
		else
		{
			p0[ 0 ] = tmpMirrorVals[ BASE ];
			p0[ 1 ] = tmpMirrorVals[ BASE + 1 ];
			p0[ 2 ] = tmpMirrorVals[ BASE + 2 ];
			rotMat.multVectInverse( pi1 , p1 );
		}
		tmpPoly.add( p0 , p1 );
		if( !snowMode )
		{
			tmpMirrorVals[ BASE ] = p1[ 0 ];
			tmpMirrorVals[ BASE + 1 ] = p1[ 1 ];
			tmpMirrorVals[ BASE + 2 ] = p1[ 2 ];
		}
    }
    

    private void enableS3D(boolean enable) {

        int mode = DisplaySetting.STEREOSCOPIC_3D_FORMAT_SIDE_BY_SIDE;
        if (!enable) {
            mode = DisplaySetting.STEREOSCOPIC_3D_FORMAT_OFF;
        }

        boolean formatResult = true;
        Surface surface = holder.getSurface();
        try {
            formatResult = DisplaySetting.setStereoscopic3DFormat(surface, mode);
        } catch (NoClassDefFoundError e) {
            android.util.Log.i(TAG, "class not found - S3D display not available");
            is3Dsupported = false;
        } catch (UnsatisfiedLinkError usle) {
            android.util.Log.i(TAG, "unsatisfied link - S3D display not available");
            is3Dsupported = false;
        }

        android.util.Log.i(TAG, "return value:" + formatResult);
        if (!formatResult) {
            android.util.Log.i(TAG, "S3D format not supported");
        }
    }

    public void toggle() {
        is3Denabled = !is3Denabled;
        if (!is3Denabled) {
            if (is3Dsupported) {
                // compensate for difference (ideally time based animation)
            	InvertibleTransformationMat.buildRotationMat( 0.0 , INITYSPEED , 0.0 );
            } else {
            	InvertibleTransformationMat.buildRotationMat( 0.0 , INITYSPEED , 0.0 );
            }
        } else {
        	speedMat = InvertibleTransformationMat.buildRotationMat( 0.0 , INITYSPEED , 0.0 );
        }
        enableS3D(is3Denabled);
    }
    
    
 // set the painted line's color
    private void setDrawingColor(int color) 
    {
       drawingColor = color;
    } // end method setDrawingColor

    
    // return the painted line's color
    private int getDrawingColor() 
    {
       return( drawingColor );
    } // end method getDrawingColor
    
    
    public IColorDef createDrawingColorDef()
    {
    	IColorDef cdef = new IColorDef()
   	 {
   		 public int getColor()
   		 {
   			 return( getDrawingColor() );
   		 }
   		 
   		 public void applyColor( int cdef )
   		 {
   			 setDrawingColor( cdef );
   			 invalidate();
   		 }
   	 };
   	 
   	 return( cdef );
    }
    
    
    public void setDrawMode()
    {
    	useDrawMode = true;
    }
    
    public void setRotateMode()
    {
    	useDrawMode = false;
    }
    
    public void eraseAll()
    {
    	drawList.clear();
    }
    
    public void eraseLastTrace()
    {
    	drawList.remove( drawList.size() - 1 );
    }
    
    
    @Override
    public void setBackgroundColor( int cdef )
    {
    	// super.setBackgroundColor( cdef );
    	backgroundColor = cdef;
    }
    
    
    private int getBackgroundColor()
    {
    	return( backgroundColor );
    }
    
    
    public IColorDef createBkgndColorDef()
    {
    	IColorDef cdef = new IColorDef()
   	 {
   		 public int getColor()
   		 {
   			 return( getBackgroundColor() );
   		 }
   		 
   		 public void applyColor( int cdef )
   		 {
   			setBackgroundColor( cdef );
   		 }
   	 };
   	 
   	 return( cdef );
    }
    
    
    public float getLineWidth()
    {
    	return( lineWidth );
    }
    
    public void setLineWidth( float in )
    {
    	lineWidth = in;
    }
    
    
    public ILineWidthDef createLineWidthDef()
    {
    	ILineWidthDef cdef = new ILineWidthDef()
    	{

			public int getDrawingColor() {
				return( S3DGLSurfaceView.this.getDrawingColor() );
			}

			public float getLineWidth() {
				return( S3DGLSurfaceView.this.getLineWidth() );
			}

			public void setLineWidth(float in) {
				S3DGLSurfaceView.this.setLineWidth( in );
			}

			public float getMaxLineWidth() {
				return( maxLineWidth.get( 1 ) );
			}
			
			public float getMinLineWidth() {
				return( maxLineWidth.get( 0 ) );
			}
    		
    	};
    	
    	return( cdef );
    }

    
    class Camera {
        float[] rotationPoint;
        float[] viewPos;
        float[] viewDirection;
        float[] viewUp;
        float aperture;
        float focallength; // along view direction
        float eyeSeparation; // = 0.325f;
        public int screenheight;
        public int screenwidth;

        public Camera() {
            focallength = 4;
            eyeSeparation = (float) (focallength / 30.0);
            aperture = 60;
            rotationPoint = new float[] {
                    0, 0, 0
            };
            viewDirection = new float[] {
                    1, 0, 0
            };
            viewUp = new float[] {
                    0, 1, 0
            };
            viewPos = new float[] {
                    -3, 0, 0
            };
        }
    };

    public static void cross(float[] v1, float[] v2, float[] r) {
        r[0] = v1[1] * v2[2] - v2[1] * v1[2];
        r[1] = v1[2] * v2[0] - v2[2] * v1[0];
        r[2] = v1[0] * v2[1] - v2[0] * v1[1];
    }

    public static void scalarMultiply(float[] v, float s) {
        for (int i = 0; i < v.length; i++) {
            v[i] *= s;
        }
    }

    public static float magnitude(float[] v) {
        return (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }

    public static void normalize(float[] v) {
        scalarMultiply(v, 1 / magnitude(v));
    }

    float[] r = new float[3];
    Camera camera;
    float near = 0.01f;
    float far = 1000;
    float ratio, wd2, ndfl;

    public void initCameraProjection(int width, int height) {
        camera = new Camera();
        camera.screenwidth = width;
        camera.screenheight = height;
        near = camera.focallength / 5;
        cross(camera.viewDirection, camera.viewUp, r);
        normalize(r);
        r[0] *= camera.eyeSeparation / 2.0;
        r[1] *= camera.eyeSeparation / 2.0;
        r[2] *= camera.eyeSeparation / 2.0;
        ratio = camera.screenwidth / (float) camera.screenheight;
        float radians = (float) (0.0174532925 * camera.aperture / 2);
        wd2 = (float) (near * Math.tan(radians));
        ndfl = near / camera.focallength;
    }

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	/**
	 * Toggles snow mode.
	 */
	public void toggleSnowMode()
	{
		snowMode = !snowMode;
	}
	
}
