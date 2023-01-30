package com.example.beauty_cam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;


public class CameraFlutterPluginView extends SurfaceView implements PlatformView, MethodChannel.MethodCallHandler, SurfaceHolder.Callback {

    private static final String TAG = CameraFlutterPluginView.class.getSimpleName();

    private SurfaceHolder mSurfaceHolder;

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        CameraUtils.openFrontalCamera(CameraUtils.DESIRED_PREVIEW_FPS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        CameraUtils.startPreviewDisplay(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        CameraUtils.releaseCamera();
    }

    public  Context context;
    /**
     * 通道
     */
    private  MethodChannel methodChannel = null;

    public CameraFlutterPluginView(Context context, int viewId, Object args, BinaryMessenger messenger) {
        super(context);
        this.context = context;
        init();
//        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        setBackgroundColor(Color.argb(255,100,79,79));  //0完全透明  255不透明
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
