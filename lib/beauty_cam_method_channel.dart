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
    // TODO: implement switchCamera
    return super.switchCamera();
  }

  ///切换滤镜
  @override
  Future<void> updateFilter(String filter) {
    // TODO: implement updateFilter
    return super.updateFilter(filter);
  }

  ///获取滤镜列表
  @override
  Future<List<String>> getFilterList() {
    // TODO: implement getFilterList
    return super.getFilterList();
  }

  ///添加滤镜
  @override
  Future<void> addFilter(String filter) {
    // TODO: implement addFilter
    return super.addFilter(filter);
  }

  ///开启或关闭美颜
  @override
  Future<void> enableBeauty(bool isEnableBeauty) {
    // TODO: implement enableBeauty
    return super.enableBeauty(isEnableBeauty);
  }

  ///美颜程度（0~1
  @override
  Future<void> setBeautyLevel(double level) {
    // TODO: implement setBeautyLevel
    return super.setBeautyLevel(level);
  }

  ///拍照
  @override
  Future<String?> takePicture() {
    // TODO: implement takePicture
    return super.takePicture();
  }

  ///拍视频
  @override
  Future<String?> takeVideo() {
    // TODO: implement takeVideo
    return super.takeVideo();
  }

  ///设置文件保存路径
  @override
  Future<String?> setOuputMP4File(String path) {
    // TODO: implement setOuputMP4File
    return super.setOuputMP4File(path);
  }
}
