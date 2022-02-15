## Requirements
- You must use AndroidStudio or Eclipse
- You must download the aar file: https://github.com/lucky-cart/lucky-cart-android/tree/master/sdk/releases/v-1.0.1


## Dependencies
You can implement library luckCart in build.gradle or add file .aar library

Method 1- Gradle Configuration:

Add it in your settings.gradle at the end of repositories:
 maven { url 'https://jitpack.io' }
Add the dependency
 implementation 'com.github.lucky-cart:lucky-cart-android:1.0.0'

Method 2- ADD file .aar library:

Add those libraries in your gradle file :
implementation files('libs/sdk-release.aar')
implementation "com.squareup.retrofit2:converter-gson:2.6.1"
implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
implementation "com.squareup.retrofit2:adapter-rxjava2:2.6.1"
implementation "io.reactivex.rxjava2:rxkotlin:2.2.0"
implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'<img width="341" alt="Screen Shot 2022-02-15 at 16 37 19" src="https://user-images.githubusercontent.com/2062107/154095500-9e938d8b-8fde-4f5a-a211-e7383480efc5.png">


LuckyCartSample will always use latest "master" (stable) versions of theses dependencies.
https://github.com/lucky-cart/lucky-cart-client-sample-android

## Use in Client Application
1-You can use class LuckCartSDK to initialize LuckyCart, generate the data required by the LuckyCart platform and send cart data to the LuckyCart server.

<img width="350" alt="Screen Shot 2022-02-15 at 16 06 46" src="https://user-images.githubusercontent.com/2062107/154089561-9560da8d-899d-4b5b-9d7d-7c7d6c278253.png">
        
LuckyCartListenerCallback is an interface wich contains response receive from LuckCart server 

<img width="350" alt="Screen Shot 2022-02-15 at 16 07 59" src="https://user-images.githubusercontent.com/2062107/154089789-87ee4600-c246-46b9-808f-8f88818ca99e.png">

        
2-Start LuckyCart
Initialize the LuckyCart using object LCAuthorization and JsonObject which contains sdk configuration properties (URL Lucky Cart server, ...).

<img width="350" alt="Screen Shot 2022-02-15 at 16 10 05" src="https://user-images.githubusercontent.com/2062107/154090188-4e0445e5-1c3f-4465-8263-451226193c07.png">

3-Set the current user

If customer id is not passed in init, you can set it by calling the setUserId() function. Customer id is required to send cart information at checkout time.

<img width="350" alt="Screen Shot 2022-02-15 at 16 34 45" src="https://user-images.githubusercontent.com/2062107/154095001-d218860e-3a89-4c1e-bf1b-bd3cabb174d2.png">
     
4-Load and display Banner 

To Load banner you can call function listAvailableBanners():

luckyCartSDK?.listAvailableBanners()

To display list of banner, you should extend the interface LuckyCartListenerCallback and call this function setActionListener(callBack: LuckyCartListenerCallback?) in your class.

<img width="526" alt="Screen Shot 2022-02-15 at 16 41 40" src="https://user-images.githubusercontent.com/2062107/154096328-94cdab31-39ce-4df6-9965-b4b4491bb4c5.png">

5-Display Banner Details

To get banner details,you can call the function getBannerDetails(pageType: kotlin.String, pageID: kotlin.String).
pageType: Current page type (homepage, cart, categories, etc...).
pageID: Current page ID
       
      luckyCartSDK?.setActionListener(this)
      luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, shopID)

6-Send Cart to LuckyCart

*Prepare data 

Each time your application does a check out, some data are sent to LuckyCart.The data should be of type JsonObject and contains cartId.
In this exemple, we create a simple JsonObject with the sample data from the LuckyCart application sample. It will then be merged with LuckyCart required fields before being sent:

<img width="350" alt="Screen Shot 2022-02-15 at 16 29 14" src="https://user-images.githubusercontent.com/2062107/154093897-e3276eda-4fda-4cf4-a59d-a145ba4f8095.png">

*Send Data

Once your application did a succesful checkout, call this function to send ticket information to LuckyCart and receive an aknowledgment.

<img width="350" alt="Screen Shot 2022-02-15 at 16 30 06" src="https://user-images.githubusercontent.com/2062107/154094054-97d8698e-eb26-4d2d-8018-3b6e0d3966ad.png">

7-Display Game
To get list of game, you can call function getGame with input cart id.

<img width="350" alt="Screen Shot 2022-02-15 at 16 35 59" src="https://user-images.githubusercontent.com/2062107/154095323-8c5357f7-75e0-438c-8907-bde28fe26830.png">
      
