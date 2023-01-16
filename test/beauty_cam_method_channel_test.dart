import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:beauty_cam/beauty_cam_method_channel.dart';

void main() {
  MethodChannelBeautyCam platform = MethodChannelBeautyCam();
  const MethodChannel channel = MethodChannel('beauty_cam');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
