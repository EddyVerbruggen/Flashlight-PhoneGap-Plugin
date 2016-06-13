package nl.xservices.plugins;

import android.Manifest;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class Flashlight extends CordovaPlugin {

  private static final String ACTION_AVAILABLE = "available";
  private static final String ACTION_SWITCH_ON = "switchOn";
  private static final String ACTION_SWITCH_OFF = "switchOff";

  private static Boolean capable;
  private boolean releasing;
  private Camera mCamera;

  private static final int PERMISSION_CALLBACK_CAMERA = 33;
  private String[] permissions = {Manifest.permission.CAMERA};
  private CallbackContext callbackContext;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    Log.d("Flashlight", "Plugin Called: " + action);

    try {
      if (action.equals(ACTION_SWITCH_ON)) {
        this.callbackContext = callbackContext;

        // When switching on immediately after checking for isAvailable,
        // the release method may still be running, so wait a bit.
        while (releasing) {
          Thread.sleep(10);
        }

        // android M permission
        if (!hasPermisssion()) {
          requestPermissions(PERMISSION_CALLBACK_CAMERA);
        } else {
          toggleTorch(true, callbackContext);
        }

        return true;
      } else if (action.equals(ACTION_SWITCH_OFF)) {
        toggleTorch(false, callbackContext);
        releaseCamera();
        return true;
      } else if (action.equals(ACTION_AVAILABLE)) {
        callbackContext.success(isCapable() ? 1 : 0);
        return true;
      } else {
        callbackContext.error("flashlight." + action + " is not a supported function.");
        return false;
      }
    } catch (Exception e) {
      callbackContext.error(e.getMessage());
      return false;
    }
  }

  private boolean isCapable() {
    if (capable == null) {
      capable = false;
      final PackageManager packageManager = this.cordova.getActivity().getPackageManager();
      for (final FeatureInfo feature : packageManager.getSystemAvailableFeatures()) {
        if (PackageManager.FEATURE_CAMERA_FLASH.equalsIgnoreCase(feature.name)) {
          capable = true;
          break;
        }
      }
    }
    return capable;
  }

  private void toggleTorch(boolean switchOn, CallbackContext callbackContext) {
    try {
      if (isCapable()) {
        doToggleTorch(switchOn, callbackContext);
      } else {
        callbackContext.error("Device is not capable of using the flashlight. Please test with flashlight.available()");
      }
    } catch (Exception e) {
      callbackContext.error(e.getMessage());
    }
  }

  @SuppressWarnings("deprecation")
  private void doToggleTorch(boolean switchOn, CallbackContext callbackContext) throws IOException {
    if (mCamera == null) {
      mCamera = Camera.open();
      if (Build.VERSION.SDK_INT >= 11) { // honeycomb
        // required for (at least) the Nexus 5
        mCamera.setPreviewTexture(new SurfaceTexture(0));
      }
    }
    final Camera.Parameters mParameters = mCamera.getParameters();
    mParameters.setFlashMode(switchOn ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);
    mCamera.setParameters(mParameters);
    mCamera.startPreview();
    callbackContext.success();
  }

  public boolean hasPermisssion() {
    for (final String p : permissions) {
      if (!PermissionHelper.hasPermission(this, p)) {
        return false;
      }
    }
    return true;
  }

  public void requestPermissions(int requestCode) {
    PermissionHelper.requestPermissions(this, requestCode, permissions);
  }

  public void onRequestPermissionResult(int requestCode, String[] permissions,
                                        int[] grantResults) throws JSONException {
    PluginResult result;
    for (int r : grantResults) {
      if (r == PackageManager.PERMISSION_DENIED) {
        result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
        this.callbackContext.sendPluginResult(result);
        return;
      }
    }

    switch (requestCode) {
      case PERMISSION_CALLBACK_CAMERA:
        toggleTorch(true, callbackContext);
        break;
    }
  }

  private void releaseCamera() {
    releasing = true;
    // we need to release the camera, so other apps can use it
    new Thread(new Runnable() {
      public void run() {
        if (mCamera != null) {
          mCamera.stopPreview();
          mCamera.setPreviewCallback(null);
          mCamera.unlock();
          mCamera.release();
          mCamera = null;
        }
        releasing = false;
      }
    }).start();
  }
}
