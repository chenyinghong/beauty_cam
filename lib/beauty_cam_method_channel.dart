import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'beauty_cam_platform_interface.dart';

/// An implementation of [BeautyCamPlatform] that uses method channels.
class MethodChannelBeautyCam extends BeautyCamPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('beauty_cam');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
