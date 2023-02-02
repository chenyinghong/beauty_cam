package com.example.beauty_cam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.atech.glcamera.interfaces.FileCallback;
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

import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;

public class CameraFlutterPluginView extends GLCameraView implements PlatformView, MethodChannel.MethodCallHandler{

    private static final String TAG = CameraFlutterPluginView.class.getSimpleName();
    public  Context context;
    /**
     * 通道
     */
    private  MethodChannel methodChannel = null;// controls button state//1.capture 2.record
//    private List<FilterFactory.FilterType> filters = new ArrayList<>();
//    private List<FilterInfo>infos = new ArrayList<>();
    public CameraFlutterPluginView(Context context, int viewId, Object args, BinaryMessenger messenger) {
        super(context);
        this.context = context;
        checkPermissions();
        //注册
        methodChannel = new MethodChannel(messenger, "beauty_cam");
        methodChannel.setMethodCallHandler(this);
    }


    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //点击按钮，访问如下方法
    private void checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = ContextCompat.checkSelfPermission(getContext(), permissions[0]);
            int l = ContextCompat.checkSelfPermission(getContext(), permissions[1]);
            int m = ContextCompat.checkSelfPermission(getContext(), permissions[2]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED ||
                    l != PackageManager.PERMISSION_GRANTED ||
                    m != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                startRequestPermission();
            }
        }
    }
    private void startRequestPermission() {

//        ActivityCompat.requestPermissions(context, permissions, 321);
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
//                this.updateFilter(FilterFactory.FilterType.Amaro);
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

                            result.success(file.getAbsoluteFile());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

                break;
            //录制视频
            case "takeVideo":
                //TODO：录制视频
                changeRecordingState(true);
//                this.setrecordFinishedListnener(file -> {
//                    //update the gallery
//                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                            Uri.fromFile(file)));
//
//                });
                break;
            case "stopVideo":
                //TODO：录制视频
                changeRecordingState(false);
                this.setrecordFinishedListnener(file -> {
                    //update the gallery
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(file)));
                    result.success(file.getAbsoluteFile());
                });
                break;
            //设置文件保存路径
            case "setOuputMP4File":

                String path=methodCall.argument("path");
                this.setOuputMP4File(new File(path));
                break;
            default:

                break;
        }
    }
//    private void initFilters(){
//
//
//        filters.add(FilterFactory.FilterType.Original);
//        filters.add(FilterFactory.FilterType.Sunrise);
//        filters.add(FilterFactory.FilterType.Sunset);
//        filters.add(FilterFactory.FilterType.BlackWhite);
//        filters.add(FilterFactory.FilterType.WhiteCat);
//        filters.add(FilterFactory.FilterType.BlackCat);
//        filters.add(FilterFactory.FilterType.SkinWhiten);
//        filters.add(FilterFactory.FilterType.Healthy);
//        filters.add(FilterFactory.FilterType.Sakura);
//        filters.add(FilterFactory.FilterType.Romance);
//        filters.add(FilterFactory.FilterType.Latte);
//        filters.add(FilterFactory.FilterType.Warm);
//        filters.add(FilterFactory.FilterType.Calm);
//        filters.add(FilterFactory.FilterType.Cool);
//        filters.add(FilterFactory.FilterType.Brooklyn);
//        filters.add(FilterFactory.FilterType.Sweets);
//        filters.add(FilterFactory.FilterType.Amaro);
//        filters.add(FilterFactory.FilterType.Antique);
//        filters.add(FilterFactory.FilterType.Brannan);
//
//
//        infos.add(new FilterInfo(R.drawable.filter_thumb_original,"原图"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_sunrise,"日出"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_sunset,"日落"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_1977,"黑白"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_whitecat,"白猫"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_blackcat,"黑猫"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_beauty,"美白"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_healthy,"健康"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_sakura,"樱花"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_romance,"浪漫"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_latte,"拿铁"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_warm,"温暖"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_calm,"安静"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_cool,"寒冷"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_brooklyn,"纽约"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_sweets,"甜品"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_amoro,"Amaro"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_antique,"复古"));
//        infos.add(new FilterInfo(R.drawable.filter_thumb_brannan,"Brannan"));
//
//        //set your own output file here
//        // mCameraView.setOuputMP4File();
//        //set record finish listener
//        mCameraView.setrecordFinishedListnener(new FileCallback() {
//            @Override
//            public void onData(File file) {
//
//                //update the gallery
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                        Uri.fromFile(file)));
//
//            }
//        });
//    }
    @Override
    public View getView() {
        return this;
    }

    @Override
    public void dispose() {

    }
}