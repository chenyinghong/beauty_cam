package com.example.beauty_cam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.atech.glcamera.interfaces.FilteredBitmapCallback;
import com.atech.glcamera.utils.FileUtils;
import com.atech.glcamera.utils.FilterFactory;
import com.atech.glcamera.views.GLCameraView;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
public class CameraFlutterPluginView extends GLCameraView implements PlatformView, MethodChannel.MethodCallHandler{

    private static final String TAG = CameraFlutterPluginView.class.getSimpleName();
    public  Context context;
    /**
     * 通道
     */
    private  MethodChannel methodChannel = null;

    public CameraFlutterPluginView(Context context, int viewId, Object args, BinaryMessenger messenger) {
        super(context);
        this.context = context;
        //注册
        methodChannel = new MethodChannel(messenger, "beauty_cam");
        methodChannel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        handleCall(call, result);

    }
    private void handleCall(MethodCall methodCall, MethodChannel.Result result) {

        switch (methodCall.method) {
            //切换镜头
            case "switchCamera":
                this.switchCamera();
                break;
            //切换滤镜
            case "updateFilter":
                //TODO:切换滤镜
                this.updateFilter(FilterFactory.FilterType.Amaro);
                break;
            //添加滤镜
            case "addFilter":
                //TODO:添加滤镜
                break;
            //获取滤镜列表
            case "getFilterList":
                //TODO:获取滤镜

                break;
            //开启或关闭美颜
            case "enableBeauty":
                this.enableBeauty(methodCall.argument("isEnableBeauty"));
                break;
            //美颜程度（0~1）
            case "setBeautyLevel":
                final float level = Float.parseFloat(Objects.requireNonNull(methodCall.argument("level")).toString());
                Toast.makeText(context, methodCall.method+"=="+level, Toast.LENGTH_SHORT).show();
                this.setBeautyLevel(level);
                break;
            //拍照
            case "takePicture":
                this.takePicture(new FilteredBitmapCallback() {
                    @Override
                    public void onData(Bitmap bitmap) {

                        File file = FileUtils.createImageFile();
                        //重新写入文件
                        try {
                            // 写入文件
                            FileOutputStream fos;
                            fos = new FileOutputStream(file);
                            //默认jpg
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                            bitmap.recycle();

                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                    Uri.fromFile(file)));

                            Toast.makeText(context, "takePicture", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

                break;
            //录制视频
            case "takeVideo":
                //TODO：录制视频
                break;
            //设置文件保存路径
            case "setOuputMP4File":
                //TODO：设置文件保存路径
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
