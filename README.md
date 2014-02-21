# PhoneGap Flashlight / Torch plugin

by [Eddy Verbruggen](http://www.x-services.nl) for iOS and Android, PhoneGap 3+

1. [Description](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#1-description)
2. [Installation](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#2-installation)
	2. [Automatically (CLI / Plugman)](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#automatically-cli--plugman)
	2. [Manually](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#manually)
	2. [PhoneGap Build](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#phonegap-build)
3. [Usage](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#3-usage)
4. [Credits](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#4-credits)
5. [License](https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin#5-license)

## 1. Description

This plugin allows you to switch the flashlight / torch of the device on and off.

* Works on Android, but likely not on 2.x devices.
* Works on iOS 5.0+, maybe even lower.
* Depends on capabilities of the device, so you can test it with an API call.
* Compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman).
* Pending review at [PhoneGap Build](https://build.phonegap.com/plugins).

## 2. Installation

### Automatically (CLI / Plugman)
Flashlight is compatible with [Cordova Plugman](https://github.com/apache/cordova-plugman), compatible with [PhoneGap 3.0 CLI](http://docs.phonegap.com/en/3.0.0/guide_cli_index.md.html#The%20Command-line%20Interface_add_features), here's how it works with the CLI:

```
$ cordova plugin add https://github.com/EddyVerbruggen/Flashlight-PhoneGap-Plugin.git
$ cordova prepare
```
Then reference `Flashlight.js` in `index.html`, after `cordova.js`/`phonegap.js`. Mind the path:
```html
<script type="text/javascript" src="js/plugins/Flashlight.js"></script>
```

### Manually

1\. Add the following xml to your `config.xml` in the root directory of your `www` folder:
```xml
<!-- for iOS -->
<feature name="Flashlight">
  <param name="ios-package" value="Flashlight" />
</feature>
```
```xml
<!-- for Android -->
<feature name="Flashlight">
  <param name="android-package" value="nl.xservices.plugins.Flashlight" />
</feature>
```

2\. For Android, add the following xml to your `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.CAMERA"/>
```

3\. Grab a copy of Flashlight.js, add it to your project and reference it in `index.html`:
```html
<script type="text/javascript" src="js/plugins/Flashlight.js"></script>
```

4\. Download the source files for iOS and/or Android and copy them to your project.

iOS: Copy `Flashlight.h` and `Flashlight.h` to `platforms/ios/<ProjectName>/Plugins`

Android: Copy `Flashlight.java` to `platforms/android/src/nl/xservices/plugins` (create the folders)

### PhoneGap Build

Flashlight works with PhoneGap build too! Compatible with PhoneGap 3.0.0 and up.
You can implement the plugin with these simple steps.

1\. Add the following xml to your `config.xml` to always use the latest version of this plugin:
```xml
<gap:plugin name="nl.x-services.plugins.flashlight" />
```
or to use this exact version:
```xml
<gap:plugin name="nl.x-services.plugins.flashlight" version="1.4" />
```

2\. Reference the JavaScript code in your `index.html`:
```html
<!-- below <script src="phonegap.js"></script> -->
<script src="js/plugins/Flashlight.js"></script>
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


## 5. License

[The MIT License (MIT)](http://www.opensource.org/licenses/mit-license.html)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
