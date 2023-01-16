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
}
