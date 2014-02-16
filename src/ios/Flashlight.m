#import "Flashlight.h"
#import <AVFoundation/AVFoundation.h>
#import <Cordova/CDV.h>

@implementation Flashlight

- (void)available:(CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:[self deviceHasFlashlight]];
    NSString *callbackId = command.callbackId;
    [self writeJavascript:[pluginResult toSuccessCallbackString:callbackId]];
}

- (void)switchOn:(CDVInvokedUrlCommand*)command {
    if ([self deviceHasFlashlight]) {
        AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOn];
        [device setFlashMode:AVCaptureFlashModeOn];
        [device unlockForConfiguration];
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self writeJavascript:[pluginResult toSuccessCallbackString:command.callbackId]];
    } else {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Device is not capable of using the flashlight. Please test with flashlight.available()"];
        [self writeJavascript:[pluginResult toErrorCallbackString:command.callbackId]];
    }
}

- (void)switchOff:(CDVInvokedUrlCommand*)command {
    if ([self deviceHasFlashlight]) {
        AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
        [device lockForConfiguration:nil];
        [device setTorchMode:AVCaptureTorchModeOff];
        [device setFlashMode:AVCaptureFlashModeOff];
        [device unlockForConfiguration];
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self writeJavascript:[pluginResult toSuccessCallbackString:command.callbackId]];
    } else {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Device is not capable of using the flashlight. Please test with flashlight.available()"];
        [self writeJavascript:[pluginResult toErrorCallbackString:command.callbackId]];
    }
}

-(BOOL)deviceHasFlashlight {
    if (NSClassFromString(@"AVCaptureDevice")) {
        AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
        return [device hasTorch] && [device hasFlash];
    }
    return false;
}

@end
