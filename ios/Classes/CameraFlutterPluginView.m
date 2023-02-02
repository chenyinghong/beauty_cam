//
//  CameraFlutterPluginView.m
//  beauty_cam
//
//  Created by 比赞 on 2023/1/30.
//
#import "CameraFlutterPluginView.h"
#import "GPUImage.h"


@interface CameraFlutterPluginView ()
/** channel*/
@property (nonatomic, strong)  FlutterMethodChannel  *channel;

@property (nonatomic, strong) UIView *nativeView;
@property (nonatomic, strong) GPUImageView *gpuImageView;
@property (nonatomic, strong) GPUImageStillCamera *gpuVideoCamera;
@property (nonatomic, strong) GPUImageMovieWriter *movieWriter;
@property (nonatomic, strong) GPUImageFilterGroup *filterGroup;
@property (nonatomic, strong) NSString *pathToMovie; // 视频储存路径
@property (nonatomic, strong) NSString *pathToPic; // 图片储存路径



@end
 
@implementation CameraFlutterPluginView
{
    CGRect _frame;
    int64_t _viewId;
    id _args;
    
    id _imagefilter;
}

- (id)initWithFrame:(CGRect)frame
             viewId:(int64_t)viewId
               args:(id)args
           messager:(NSObject<FlutterBinaryMessenger>*)messenger
{
    if (self = [super init])
    {
        _frame = frame;
        _viewId = viewId;
        _args = args;
        
        ///建立通信通道 用来 监听Flutter 的调用和 调用Fluttter 方法 这里的名称要和Flutter 端保持一致
        _channel = [FlutterMethodChannel methodChannelWithName:@"beauty_cam" binaryMessenger:messenger];
        
        __weak __typeof__(self) weakSelf = self;
        
        [_channel setMethodCallHandler:^(FlutterMethodCall * _Nonnull call, FlutterResult  _Nonnull result) {
            [weakSelf onMethodCall:call result:result];
        }];
        
    }
    return self;
}
 
- (UIView *)view{
    
    self.nativeView = [[UIView alloc] initWithFrame:_frame];
    self.nativeView.backgroundColor = [UIColor blackColor];
    
    __block CameraFlutterPluginView *weakSelfView = self;
    dispatch_time_t delayTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC));
    dispatch_after(delayTime, dispatch_get_main_queue(), ^{
        [weakSelfView initCamera];
    });
    
    return self.nativeView;
    
}
- (void)initCamera {
    
    self.pathToMovie = [[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0] stringByAppendingPathComponent:@"BeautyCamMovie.mp4"];
    self.pathToPic = [[NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0] stringByAppendingPathComponent:@"BeautyCamMovie.png"];
    
    self.gpuImageView = [[GPUImageView alloc] init];
    self.gpuImageView.frame = self.nativeView.frame;
    [self.nativeView addSubview:self.gpuImageView];
    
    // videoCamera
    self.gpuVideoCamera = [[GPUImageStillCamera alloc] initWithSessionPreset:AVCaptureSessionPreset1280x720 cameraPosition:AVCaptureDevicePositionBack];
    [self.gpuVideoCamera addAudioInputsAndOutputs];
    
    // GPUImageView填充模式
    self.gpuImageView.fillMode = kGPUImageFillModePreserveAspectRatioAndFill;
    self.gpuVideoCamera.outputImageOrientation = UIInterfaceOrientationPortrait;
    self.gpuVideoCamera.horizontallyMirrorFrontFacingCamera = YES;
    
    // 默认开启美颜
    [self enableBeauty:@"0"];
    
    __block CameraFlutterPluginView *weakSelfView = self;
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        //Start camera capturing, 里面封装的是AVFoundation的session的startRunning
        [weakSelfView.gpuVideoCamera startCameraCapture];
    });
    
}

#pragma mark - Flutter 交互监听
-(void)onMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result{
    
    NSLog(@"[call method] ----  %@  call.arguments %@", call.method, call.arguments);
    
    NSDictionary *argumentsDic = call.arguments;
    //监听Fluter
    if ([[call method] isEqualToString:@"enableBeauty"]) { // 开启/关闭美颜
        [self enableBeauty:[argumentsDic objectForKey:@"isEnableBeauty"]];
    }else if ([[call method] isEqualToString:@"switchCamera"]) { // 切换摄像头
        [self switchCamera];
    }else if ([[call method] isEqualToString:@"takePicture"]) { // 拍照
        [self takePictureWithResult:result];
    }else if ([[call method] isEqualToString:@"takeVideo"]) { // 开始录制
        [self takeVideo];
    }else if ([[call method] isEqualToString:@"stopVideo"]) { // 结束录制
        [self endVideoWithResult:result];
    }else if ([[call method] isEqualToString:@"setOuputMP4File"]) { // 设置视频储存路径
        [self setOuputMP4File:[argumentsDic objectForKey:@"path"]];
    }else if ([[call method] isEqualToString:@"setOuputPicFile"]) { // 设置图片储存路径
        [self setOuputPicFile:[argumentsDic objectForKey:@"path"]];
    }
    
}
// 调用Flutter
- (void)flutterMethod{
    [self.channel invokeMethod:@"clickAciton" arguments:@"我是参数"];
}

// 开启/关闭美颜
- (void)enableBeauty:(NSString *)isEnableBeauty {
    
    [self.gpuVideoCamera removeAllTargets];
    if(isEnableBeauty.integerValue == 0) { // 开启
        self.filterGroup = [[GPUImageFilterGroup alloc] init];
        // 双边模糊
        GPUImageBilateralFilter *bilateralFilter = [[GPUImageBilateralFilter alloc] init];
        bilateralFilter.distanceNormalizationFactor = 12.0;
        // 曝光
        GPUImageExposureFilter *exposureFilter = [[GPUImageExposureFilter alloc] init];
        exposureFilter.exposure = 0; // -1/1 正常0
        // 美白
        GPUImageBrightnessFilter *brightnessFilter = [[GPUImageBrightnessFilter alloc] init];
        brightnessFilter.brightness = 0.05;  // -1/1 正常0
        // 饱和
        GPUImageSaturationFilter *saturationFilter = [[GPUImageSaturationFilter alloc] init];
        saturationFilter.saturation = 1.0;  // 0/2 正常1
        // 黑白色调模糊
//        GPUImageOpeningFilter *openingFilter = [[GPUImageOpeningFilter alloc] init];
        
        [bilateralFilter addTarget: exposureFilter];
        [exposureFilter addTarget: brightnessFilter];
        [brightnessFilter addTarget: saturationFilter];
        
        [self.filterGroup setInitialFilters:[NSArray arrayWithObject:bilateralFilter]];
        [self.filterGroup setTerminalFilter:saturationFilter];
        
        [self.gpuVideoCamera addTarget:self.filterGroup];
        [self.filterGroup addTarget:self.gpuImageView];
        _imagefilter = self.filterGroup;
    }else { // 关闭
        GPUImageExposureFilter *exposureFilter = [[GPUImageExposureFilter alloc] init];
        [self.gpuVideoCamera addTarget:exposureFilter];
        [exposureFilter addTarget:self.gpuImageView];
        _imagefilter = exposureFilter;
    }
}

// 切换摄像头
- (void)switchCamera {
    [self.gpuVideoCamera rotateCamera];
}

// 拍照
- (void)takePictureWithResult:(FlutterResult)result {
    __block CameraFlutterPluginView *weakSelfView = self;
    [self.gpuVideoCamera capturePhotoAsImageProcessedUpToFilter:_imagefilter withCompletionHandler:^(UIImage *processedImage, NSError *error) {
        if(error){
            NSLog(@"error --- %@", error);
            return;
        }
        // 写入指定路径
        NSData *data = UIImagePNGRepresentation(processedImage);
        [data writeToFile:weakSelfView.pathToPic atomically:YES];
        // 存到相册
        UIImageWriteToSavedPhotosAlbum(processedImage, weakSelfView, @selector(image:didFinishSavingWithError:contextInfo:),(__bridge void *)weakSelfView);
        result(weakSelfView.pathToPic);
    }];
}

// 开始拍摄
- (void)takeVideo {
    NSLog(@"开始拍摄");
    unlink([self.pathToMovie UTF8String]);
    __block CameraFlutterPluginView *weakSelfView = self;
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.2 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self->_imagefilter addTarget:weakSelfView.movieWriter];
        weakSelfView.gpuVideoCamera.audioEncodingTarget = weakSelfView.movieWriter;
        [weakSelfView.movieWriter startRecording];
    });
    
}

// 结束拍摄
- (void)endVideoWithResult:(FlutterResult)result {
//    [self.movieWriter finishRecording];
//    [self.gpuVideoCamera useNextFrameForImageCapture];
//    //获取当前数据流的图像，
//    UIImage *processedImage = [self.gpuVideoCamera imageFromCurrentFramebuffer];
//    // processedImage 为获取图片
    
    [_imagefilter removeTarget:self.movieWriter];
    self.gpuVideoCamera.audioEncodingTarget = nil;
    __block CameraFlutterPluginView *weakSelfView = self;
    [self.movieWriter finishRecordingWithCompletionHandler:^{
        [weakSelfView saveVideo];
        weakSelfView.movieWriter = nil;
        result(weakSelfView.pathToMovie);
    }];
    
}

// 设置视频储存路径
- (void)setOuputMP4File:(NSString *)path {
    self.pathToMovie = path;
}
// 设置图片储存路径
- (void)setOuputPicFile:(NSString *)path {
    self.pathToPic = path;
}

- (void)saveVideo {
    BOOL exists = [[NSFileManager defaultManager] fileExistsAtPath:self.pathToMovie];
    if (!exists) {
        NSLog(@"不存在");
        return;
    }
    if (UIVideoAtPathIsCompatibleWithSavedPhotosAlbum(self.pathToMovie)) {
        UISaveVideoAtPathToSavedPhotosAlbum(self.pathToMovie, self, @selector(video:didFinishSavingWithError:contextInfo:), nil);
    }
}

//保存视频完成之后的回调
- (void)video:(NSString *)videoPath didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo {
    if (error) {
        NSLog(@"保存视频失败%@", error.localizedDescription);
    }else {
        NSLog(@"保存视频成功");
    }
}

//保存图片完成之后的回调
- (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo{
    NSLog(@"image = %@, error = %@, contextInfo = %@", image, error, contextInfo);
}

#pragma mark - Getters & Setters
- (GPUImageMovieWriter *)movieWriter {

    if (_movieWriter) {
        return _movieWriter;
    }
    
    CGSize movieSize = CGSizeMake(720, 1280);
    NSURL *movieURL = [NSURL fileURLWithPath:self.pathToMovie];
    NSMutableDictionary *settings = [[NSMutableDictionary alloc] init];
    [settings setObject:AVVideoCodecTypeH264 forKey:AVVideoCodecKey];
    [settings setObject:[NSNumber numberWithInteger:movieSize.width] forKey:AVVideoWidthKey];
    [settings setObject:[NSNumber numberWithInteger:movieSize.height] forKey:AVVideoHeightKey];
    _movieWriter = [[GPUImageMovieWriter alloc] initWithMovieURL:movieURL size:movieSize fileType:AVFileTypeMPEG4 outputSettings:settings];
    _movieWriter.encodingLiveVideo = YES;
    _movieWriter.assetWriter.movieFragmentInterval = kCMTimeInvalid;
    return _movieWriter;
}



@end



