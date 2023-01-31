package com.example.beauty_cam.glcamera.filters;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;
import android.content.Context;
import android.opengl.GLES20;
import com.example.beauty_cam.R;
import com.example.beauty_cam.glcamera.utils.OpenGlUtils;

import java.nio.ByteBuffer;
public class BlackFilter extends BaseFilter {


    public BlackFilter(Context c){
        super(c);

    }

    @Override
    public void setPath() {
        path1 = R.raw.base_vertex_shader;
        path2 = R.raw.black_white_shader;
    }



    @Override
    public void onDrawArraysPre() {

    }

    @Override
    public void onDrawArraysAfter() {

    }


}
