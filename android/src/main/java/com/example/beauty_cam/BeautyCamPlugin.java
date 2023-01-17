package com.example.beauty_cam;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** BeautyCamPlugin */
public class BeautyCamPlugin implements FlutterPlugin{
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
//  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
//    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "beauty_cam");
//    channel.setMethodCallHandler(this);
    flutterPluginBinding.getPlatformViewRegistry().registerViewFactory("beauty_cam", new CameraFlutterPluginViewFactory(flutterPluginBinding.getBinaryMessenger()));

  }

//  @Override
//  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
//    switch (call.method) {
//      //切换镜头
//      case "switchCamera":
//
//        break;
//        //切换滤镜
//      case "updateFilter":
//
//        break;
//        //添加滤镜
//      case "addFilter":
//
//        break;
//        //获取滤镜列表
//      case "getFilterList":
//
//        break;
//      //开启或关闭美颜
//      case "enableBeauty":
//
//        break;
//      //美颜程度（0~1）
//      case "setBeautyLevel":
//
//        break;
//        //拍照
//      case "takePicture":
//
//        break;
//        //录制视频
//      case "takeVideo":
//
//        break;
//        //设置文件保存路径
//      case "setOuputMP4File":
//
//        break;
//      default:
//
//        break;
//    }
//  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
//    channel.setMethodCallHandler(null);
  }
}
