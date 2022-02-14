# Introduction
AuraFramework give you all embedded features to access certified product data for an user


# Getting Started
- [Requirements](#requirements)
- [Dependencies](#dependencies)
- [Installation](#installation)
- [Update](#sdk-update)
- [Debug](#sdk-debug)

# Generics
- [Generic Callbacks](#generic-callbacks)
- [Error Types](#customer-error-types)
- [Server Health Status](#server-health-status)

# Using Customer Framework
- [API References](#customer-api-references)
- [Availables Model extension methods](#customer-model-extensions)

# Using Vendor - Logistic Framework
- [API References](#vendor-api-references)
- [Availables Model extension methods] (#vendor-model-extensions)


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

LuckyCart will always use latest "master" (stable) versions of theses dependencies.

