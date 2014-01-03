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

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;

// assorted bitmap utilities used with S3D image examples
public class BitmapUtils {

    public static Bitmap create2DBitmap(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = 2f;
        float scaleHeight = 1f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // extract images by cropping and scaling with matrix
        Bitmap leftBitmap = Bitmap.createBitmap(bitmap, 0, 0, width / 2, height, matrix, true);
        // Bitmap rightBitmap = Bitmap.createBitmap(bitmap, width/2, 0, width/2,
        // height, matrix, true);

        // return left image as the 2D image
        return leftBitmap;
    }

    public static Bitmap createAnaglyphBitmap(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = 2f;
        float scaleHeight = 1f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // extract images by cropping and scaling with matrix
        Bitmap leftBitmap = Bitmap.createBitmap(bitmap, 0, 0, width / 2, height, matrix, true);
        Bitmap rightBitmap = Bitmap.createBitmap(bitmap, width / 2, 0, width / 2, height, matrix,
                true);
        bitmap.recycle();

        // create result bitmap
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.ARGB_8888;
        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        result.eraseColor(Color.BLACK);

        // paint the left and right images onto canvas using red and cyan color
        // filters
        Paint red = new Paint();
        red.setShader(new BitmapShader(leftBitmap, TileMode.CLAMP, TileMode.CLAMP));
        red.setColorFilter(new PorterDuffColorFilter(Color.rgb(200, 0, 0), Mode.MULTIPLY));
        red.setXfermode(new PorterDuffXfermode(Mode.SCREEN));

        Paint cyan = new Paint();
        cyan.setShader(new BitmapShader(rightBitmap, TileMode.CLAMP, TileMode.CLAMP));
        cyan.setColorFilter(new PorterDuffColorFilter(Color.rgb(0, 200, 200), Mode.MULTIPLY));
        cyan.setXfermode(new PorterDuffXfermode(Mode.SCREEN));

        // use result bitmap for the canvas to draw into
        Canvas c = new Canvas(result);
        c.drawRect(0, 0, width, height, red);
        c.drawRect(0, 0, width, height, cyan);
        leftBitmap.recycle();
        rightBitmap.recycle();

        return result;
    }

}
