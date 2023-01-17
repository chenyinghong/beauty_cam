import 'beauty_cam_platform_interface.dart';

class BeautyCam {
  // Future<String?> getPlatformVersion() {
  //   return BeautyCamPlatform.instance.getPlatformVersion();
  // }

  ///切换镜头
  Future<void> switchCamera() {
    return BeautyCamPlatform.instance.switchCamera();
  }

  ///切换滤镜
  Future<void> updateFilter(String filter) {
    return BeautyCamPlatform.instance.updateFilter(filter);
  }

  ///获取滤镜列表
  Future<List<String>> getFilterList() {
    return BeautyCamPlatform.instance.getFilterList();
  }

  ///添加滤镜
  Future<void> addFilter(String filter) {
    return BeautyCamPlatform.instance.addFilter(filter);
  }

  ///开启或关闭美颜
  Future<void> enableBeauty(bool isEnableBeauty) {
    return BeautyCamPlatform.instance.enableBeauty(isEnableBeauty);
  }

  ///美颜程度（0~1）
  Future<void> setBeautyLevel(double level) {
    return BeautyCamPlatform.instance.setBeautyLevel(level);
  }

  ///拍照
  Future<String?> takePicture() {
    return BeautyCamPlatform.instance.takePicture();
  }

  ///拍视频
  Future<String?> takeVideo() {
    return BeautyCamPlatform.instance.takeVideo();
  }

  ///设置文件保存路径
  Future<String?> setOuputMP4File(String path) {
    return BeautyCamPlatform.instance.setOuputMP4File(path);
  }
}
