import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'beauty_cam_method_channel.dart';

abstract class BeautyCamPlatform extends PlatformInterface {
  /// Constructs a BeautyCamPlatform.
  BeautyCamPlatform() : super(token: _token);

  static final Object _token = Object();

  static BeautyCamPlatform _instance = MethodChannelBeautyCam();

  /// The default instance of [BeautyCamPlatform] to use.
  ///
  /// Defaults to [MethodChannelBeautyCam].
  static BeautyCamPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BeautyCamPlatform] when
  /// they register themselves.
  static set instance(BeautyCamPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  ///切换镜头
  Future<String?> switchCamera() {
    throw UnimplementedError('switchCamera() has not been implemented.');
  }

  ///切换滤镜
  Future<String?> updateFilter() {
    throw UnimplementedError('updateFilter() has not been implemented.');
  }

  ///开启或关闭美颜
  Future<String?> enableBeauty() {
    throw UnimplementedError('enableBeauty() has not been implemented.');
  }

  ///美颜程度（0~1）
  Future<String?> setBeautyLevel() {
    throw UnimplementedError('setBeautyLevel() has not been implemented.');
  }

  ///拍照
  Future<String?> takePicture() {
    throw UnimplementedError('takePicture() has not been implemented.');
  }

  ///拍视频
  Future<String?> takeVideo() {
    throw UnimplementedError('takeVideo() has not been implemented.');
  }

  ///设置文件保存路径
  Future<String?> setOuputMP4File() {
    throw UnimplementedError('setOuputMP4File() has not been implemented.');
  }
}
