# Google Location Demo

<br \>
<br \>

# Screenshot
![Xcode indent settings](https://github.com/rocooshiang/LearningAndroidRecord/blob/ModifyBranch/Net_Tutorial/GoogleLocationDemo/Screenshot/Image1.png)
![Xcode indent settings](https://github.com/rocooshiang/LearningAndroidRecord/blob/ModifyBranch/Net_Tutorial/GoogleLocationDemo/Screenshot/Image2.png)

<br \>
<br \>

# Note
Android 6.0 Marshmallow 有新的 [Permissions Model ](https://developer.android.com/training/permissions/index.html) ，簡化了 App 的安裝與更新過程，
permissions 不會在安裝 App 前就要求，而是會在運行過程依功能來確定 User 有沒有啟用相關的 permissions (學iOS?)。

Google Play services 8.1是第一個 release 版來提供 Android 6.0 及以上的 [runtime permissions](https://developer.android.com/training/permissions/requesting.html) ， Google Play services
自動提供所有 permissions 給 API 使用，所以我們 App 通常不需要申請權限來使用他們，但是，你還是需要檢查和要求運行權限，並且當
使用者拒絕 App 所需來自 Google Play services 的權限時，要能適當的處理錯誤。
 
 ***當你在：***<br \>
![Xcode indent settings](https://github.com/rocooshiang/LearningAndroidRecord/blob/ModifyBranch/Net_Tutorial/GoogleLocationDemo/Screenshot/Image4.png)

***記得是要 import 下圖這個：***
![Xcode indent settings](https://github.com/rocooshiang/LearningAndroidRecord/blob/ModifyBranch/Net_Tutorial/GoogleLocationDemo/Screenshot/Image3.png)

<br \>
<br \>

# Reference
* [Google Play Service and Runtime Permissions](https://developers.google.com/android/guides/permissions)
* [Google Sample Android-RuntimePermissions (Github)](https://github.com/googlesamples/android-RuntimePermissions)

* [Making Your App Location-Aware](https://developer.android.com/training/location/index.html)
* [Google Sample Android-Play-Location (Github)](https://github.com/googlesamples/android-play-location)

* [Requesting Permissions at Run Time](https://developer.android.com/training/permissions/requesting.html)
* [Permissions Model ](https://developer.android.com/training/permissions/index.html)
