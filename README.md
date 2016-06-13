# PhoneGap Flashlight / Torch plugin

by [Eddy Verbruggen](http://www.x-services.nl) for iOS, Android and WP8

1. [Description](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#1-description)
2. [Installation](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#2-installation)
	2. [Automatically (CLI / Plugman)](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#automatically-cli--plugman)
	2. [Manually](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#manually)
	2. [PhoneGap Build](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#phonegap-build)
3. [Usage](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#3-usage)
4. [Credits](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#4-credits)

## 1. Description

This plugin allows you to switch the flashlight / torch of the device on and off.

* Works on Android 2.x and newer.
* Works on iOS 5.0+, maybe even lower.
* Works on Windows Phone 8 (since v2.0.0).
* Depends on capabilities of the device, so you can test it with an API call.
* Compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman).
* Pending review at [PhoneGap Build](https://build.phonegap.com/plugins).

## 2. Installation

Latest stable version from npm:
```
$ cordova plugin add cordova-plugin-flashlight
```

Bleeding edge version from Github:
```
$ cordova plugin add https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin
```

### PhoneGap Build

Flashlight works with PhoneGap build too! Compatible with PhoneGap 3.0.0 and up.
Just add the following xml to your `config.xml` to always use the latest version of this plugin:
```xml
<gap:plugin name="cordova-plugin-flashlight" source="npm" />
```

## 3. Usage
```javascript
window.plugins.flashlight.available(function(isAvailable) {
  if (isAvailable) {

    // switch on
    window.plugins.flashlight.switchOn(); // success/error callbacks may be passed

    // switch off after 3 seconds
    setTimeout(function() {
      window.plugins.flashlight.switchOff(); // success/error callbacks may be passed
    }, 3000);

  } else {
    alert("Flashlight not available on this device");
  }
});
```

As an alternative to `switchOn` and `switchOff`, you can use the `toggle` function
```javascript
window.plugins.flashlight.toggle(); // success/error callbacks may be passed
```

To know if the flashlight is on or off you can call `isSwitchedOn` 
```javascript
window.plugins.flashlight.isSwitchedOn(); // returns true/false
```

A hint for `Android developers`: you'll want to make sure the torch is switched off when the app is exited via the backbutton.
Otherwise, the camera may be locked so it can't be used by other apps:
```javascript
document.addEventListener("backbutton", function() {
  // pass exitApp as callbacks to the switchOff method
  window.plugins.flashlight.switchOff(exitApp, exitApp);
}, false);

function exitApp() {
  navigator.app.exitApp();
}
```

## 4. CREDITS ##
* This plugin was streamlined and enhanced for Plugman / PhoneGap Build by [Eddy Verbruggen](http://www.x-services.nl).
* The Android code was inspired by the [PhoneGap Torch plugin](https://github.com/phonegap/phonegap-plugins/tree/DEPRECATED/Android/Torch).
* Thanks to [HuaHub](https://github.com/HuaHub) for [making me fix a camera lock issue on Android](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin/issues/3).
* The iOS code was inspired by [Tom Schreck](https://github.com/tomschreck/iOS-Torch-Plugin).
