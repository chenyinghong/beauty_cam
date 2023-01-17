import 'package:beauty_cam/beauty_cam.dart';
import 'package:beauty_cam/camera_view.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  ///定义一个测试类的属性 用来调用原生方法 和原生交互
  BeautyCam? cameraFlutterPluginDemo; // 定一个插件的对象，
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ///初始化 测试视图的类，我们写的TextView
    CameraView cameraView = CameraView(
      onCreated: onCameraViewCreated,
    );
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: Column(
            children: <Widget>[
              Container(
                height: 500,
                width: double.infinity,
                child: cameraView,

                ///使用原生视图
              ),
              FloatingActionButton(
                ///添加一个按钮 用来触发原生调用
                onPressed: onNativeMethon,

                ///点击方法里面调用原生
              )
            ],
          )),
    );
  }

  void onCameraViewCreated(cameraFlutterPluginDemo) {
    this.cameraFlutterPluginDemo = cameraFlutterPluginDemo;
  }

  /// 调用原生
  void onNativeMethon() {
    cameraFlutterPluginDemo?.takePicture();
  }
}
