/*
 * Copyright (C) 2011 HTC Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.postgreen.stereo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;


//surface view used in the S3DOpenGLExample
public class LineWidthView extends GLSurfaceView implements Renderer {

    public boolean is3Denabled = true;
    public boolean is3Dsupported = true;

    private static final String TAG = "LineWidthView";

    private int width;
    private int height;

    private float xrot;
    private float inityrot = 0.2f;
    private float yrot = inityrot;
    private float xspeed;
    private float inityspeed = 0.8f;
    private float yspeed = inityspeed;

    private float z = 0.0f;

    private final float scaleFactor = 0.4f;
    private Context context;
    private SurfaceHolder holder;
    
    private FloatBuffer vertexBuffer = null;
    
    
    
    private float lineWidth = 3.0f;
    
    private final IntBuffer maxLineWidth = IntBuffer.allocate( 2 );
    
    
    private volatile int drawingColor = Color.argb(
            255 , 128 , 0 , 255 );
    
    private volatile int backgroundColor = Color.argb(
            255 , 255 , 255 , 158 );
    
    
    

    public LineWidthView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LineWidthView(Context context, AttributeSet attrs) {
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
    	
        gl.glTranslatef(z, 0.0f, 0.0f);
        gl.glScalef(0.8f, 0.8f, 0.8f);

        gl.glRotatef(xrot, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);

     // Enabled the vertex buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        //gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        
        final int color = drawingColor;
        gl.glColor4f( 
        		(float)( Color.red(color) / 255.0 ) , 
        		(float)( Color.green(color) / 255.0 ) , 
        		(float)( Color.blue(color) / 255.0 ) , 
        		(float)( Color.alpha(color) / 255.0 ) );
        
        gl.glLineWidth( lineWidth );

        
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer); // OpenGL docs.
        
        gl.glDrawArrays(GL10.GL_LINES, 0, 8);

        
     // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);// OpenGL docs.
        //gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        int error = gl.glGetError(); if (error != GL10.GL_NO_ERROR) { Log.d(TAG, "err=" + error); }
        
        xrot += xspeed;
        yrot += yspeed;
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (h == 0) {
            h = 1;
        }

        Log.d(TAG, "h=" + h + " h=" + h);

        width = w;
        height = h;

        initCameraProjection(width, height);

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

    @Override
    public void onPause() {
        is3Denabled = false;
    }

    private float pinch(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    protected void slide(int zoom) {
        Log.d(TAG, "ZOOM to " + zoom);

        float newZoom = zoom;

        float zfactor = (float) (newZoom * scaleFactor);

        if (zoom > 3)
        {
            // z -= zfactor;
            z = (float) (-zfactor + (3 * scaleFactor));
        }
        else if (zoom < 3)
        {
            // z += zfactor;
            z = (float) ((3 * scaleFactor) - zfactor);
        }
        else
            z = 0;

        Log.d(TAG, "zfactor = " + zfactor + ", z = " + z);

        // limit zoom
        Log.d(TAG, "z=" + z);
        if (z > 2.6f) {
            Log.d(TAG, "z is above threshold");
            z = 2.6f;
        } else if (z < -2.6f) {
            Log.d(TAG, "z is below threshold");
            z = -2.6f;
        }

    }

    

    public void toggle() {
        is3Denabled = !is3Denabled;
        if (!is3Denabled) {
            if (is3Dsupported) {
                // compensate for difference (ideally time based animation)
                yspeed = (float) (inityspeed / 1.79);
            } else {
                yspeed = inityspeed;
            }
        } else {
            yspeed = inityspeed;
        }
    }
    
    
 // set the painted line's color
    public void setDrawingColor(int color) 
    {
       drawingColor = color;
    } // end method setDrawingColor

    
    // return the painted line's color
    private int getDrawingColor() 
    {
       return( drawingColor );
    } // end method getDrawingColor
    
    
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
				return( LineWidthView.this.getDrawingColor() );
			}

			public float getLineWidth() {
				return( LineWidthView.this.getLineWidth() );
			}

			public void setLineWidth(float in) {
				LineWidthView.this.setLineWidth( in );
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
}
