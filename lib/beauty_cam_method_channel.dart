import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'beauty_cam_platform_interface.dart';

/// An implementation of [BeautyCamPlatform] that uses method channels.
class MethodChannelBeautyCam extends BeautyCamPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('beauty_cam');

  // @override
  // Future<String?> getPlatformVersion() async {
  //   final version =
  //       await methodChannel.invokeMethod<String>('getPlatformVersion');
  //   return version;
  // }
  ///切换相机
  @override
  Future<void> switchCamera() {
    return methodChannel.invokeMethod('switchCamera');
  }

  ///切换滤镜
  @override
  Future<void> updateFilter(String filter) {
    return methodChannel.invokeMethod('updateFilter', {"filter": filter});
  }

  ///获取滤镜列表
  @override
  Future<List<String>?> getFilterList() {
    return methodChannel.invokeMethod<List<String>>('getFilterList');
  }

  ///添加滤镜
  @override
  Future<void> addFilter(String filter) {
    return methodChannel.invokeMethod('addFilter', <String, String>{
      'filter': filter,
    });
  }

  ///开启或关闭美颜
  @override
  Future<void> enableBeauty(bool isEnableBeauty) {
    return methodChannel.invokeMethod('enableBeauty', <String, bool>{
      'isEnableBeauty': isEnableBeauty,
    });
    return super.enableBeauty(isEnableBeauty);
  }

  ///美颜程度（0~1）
  @override
  Future<void> setBeautyLevel(double level) {
    return methodChannel.invokeMethod('setBeautyLevel', <String, double>{
      'level': level,
    });
  }

  ///拍照
  @override
  Future<String?> takePicture() async {
    return methodChannel.invokeMethod<String>('takePicture');
  }

  ///拍视频
  @override
  Future<String?> takeVideo() {
    return methodChannel.invokeMethod<String>('takeVideo');
  }

  ///设置文件保存路径
  @override
  Future<String?> setOuputMP4File(String path) {
    return methodChannel
        .invokeMethod<String>('setOuputMP4File', {"path": path});
  }
}
