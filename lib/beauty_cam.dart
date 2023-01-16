
import 'beauty_cam_platform_interface.dart';

class BeautyCam {
  Future<String?> getPlatformVersion() {
    return BeautyCamPlatform.instance.getPlatformVersion();
  }
}
