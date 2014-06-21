using Microsoft.Phone.Tasks;

using Microsoft.Devices.PhotoCamera;

using WPCordovaClassLib.Cordova;
using WPCordovaClassLib.Cordova.Commands;
using WPCordovaClassLib.Cordova.JSON;

namespace WPCordovaClassLib.Cordova.Commands
{
    public class Flashlight : BaseCommand
    {

        public void available(string jsonArgs)
        {
            DispatchCommandResult(new PluginResult(PluginResult.Status.OK, hasFlashlight()));
        }

        public void switchOn(string jsonArgs)
        {
            if (hasFlashlight()) {
                var _device = await AudioVideoCaptureDevice.OpenAsync(CameraSensorLocation.Back, AudioVideoCaptureDevice.GetAvailableCaptureResolutions(CameraSensorLocation.Back).First());
                _device.SetProperty(KnownCameraAudioVideoProperties.VideoTorchMode, VideoTorchMode.On);
                DispatchCommandResult(new PluginResult(PluginResult.Status.OK));
            } else {
                DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR, "Device is not capable of using the flashlight. Please test with flashlight.available()");
            }
        }

        public void switchOff(string jsonArgs)
        {
            if (hasFlashlight()) {
                var _device = await AudioVideoCaptureDevice.OpenAsync(CameraSensorLocation.Back, AudioVideoCaptureDevice.GetAvailableCaptureResolutions(CameraSensorLocation.Back).First());
                _device.SetProperty(KnownCameraAudioVideoProperties.VideoTorchMode, VideoTorchMode.Off);
                DispatchCommandResult(new PluginResult(PluginResult.Status.OK));
            } else {
                DispatchCommandResult(new PluginResult(PluginResult.Status.ERROR, "Device is not capable of using the flashlight. Please test with flashlight.available()");
            }
        }

        private bool hasFlashlight()
        {
            bool supported = PhotoCamera.IsFlashModeSupported;
            return supported;
        }

    }
}