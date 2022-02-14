## Requirements
- You must use AndroidStudio or Eclipse
- You must download the aar file: https://github.com/lucky-cart/lucky-cart-android/tree/master/sdk/releases/v-1.0.1

## Dependencies
Add those libraries in your gradle file :
implementation files('libs/sdk-release.aar')
implementation "com.squareup.retrofit2:converter-gson:2.6.1"
implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
implementation "com.squareup.retrofit2:adapter-rxjava2:2.6.1"
implementation "io.reactivex.rxjava2:rxkotlin:2.2.0"
implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'

LuckyCartSample will always use latest "master" (stable) versions of theses dependencies.
https://github.com/lucky-cart/lucky-cart-client-sample-android



