package com.example.beauty_cam.glcamera.interfaces;

import android.graphics.Bitmap;

public interface ImageCallback {

    //给surfaceview回调的原生数据
    void onData(byte[] file);


}
