package com.example.beauty_cam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.provider.CalendarContract;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;


public class CameraFlutterPluginView extends TextView implements PlatformView, MethodChannel.MethodCallHandler, TextureView.SurfaceTextureListener{
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public  Context context;
    /**
     * 通道
     */
    private  MethodChannel methodChannel = null;

    public CameraFlutterPluginView(Context context, int viewId, Object args, BinaryMessenger messenger) {
        super(context);
        this.context = context;
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(Color.argb(255,100,79,79));  //0完全透明  255不透明
        //注册
        methodChannel = new MethodChannel(messenger, "beauty_cam");
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        handleCall(call, result);

    }
    private void handleCall(MethodCall methodCall, MethodChannel.Result result) {
//

        switch (methodCall.method) {
            //切换镜头
            case "switchCamera":

                break;
            //切换滤镜
            case "updateFilter":

                break;
            //添加滤镜
            case "addFilter":

                break;
            //获取滤镜列表
            case "getFilterList":


                break;
            //开启或关闭美颜
            case "enableBeauty":

                break;
            //美颜程度（0~1）
            case "setBeautyLevel":

                break;
            //拍照
            case "takePicture":
                Toast.makeText(context, "takePicture", Toast.LENGTH_SHORT).show();
                break;
            //录制视频
            case "takeVideo":

                break;
            //设置文件保存路径
            case "setOuputMP4File":

                break;
            default:

                break;
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void dispose() {

    }
}
