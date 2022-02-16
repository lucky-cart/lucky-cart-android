## Requirements
- You must use AndroidStudio or Eclipse
- You can find the aar file here : https://github.com/lucky-cart/lucky-cart-android/tree/master/sdk/releases/v-1.0.1


## Dependencies
You can implement library luckCart in build.gradle or add file .aar library

Method 1- Gradle Configuration:

Add it in your settings.gradle at the end of repositories:
```
 maven { url 'https://jitpack.io' }
 
```
Add the dependency
```
implementation 'com.github.lucky-cart:lucky-cart-android:1.0.0'

```


Method 2- ADD file .aar library:

Add those libraries in your gradle file :
implementation files('libs/sdk-release.aar')
implementation "com.squareup.retrofit2:converter-gson:2.6.1"
implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2"
implementation "com.squareup.retrofit2:adapter-rxjava2:2.6.1"
implementation "io.reactivex.rxjava2:rxkotlin:2.2.0"
implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'


LuckyCartSample will always use latest "master" (stable) versions of theses dependencies.
https://github.com/lucky-cart/lucky-cart-client-sample-android

## Download or clone the sample application

All the sample code in this documentation is extracted from the project LuckyCartSample :https://github.com/lucky-cart/lucky-cart-client-sample-android

It is a simple shopping application draft using a trivial shop model ( Shop, Customer, Cart, Products and Orders ).

   <img width="500" alt="Screen Shot 2022-02-16 at 12 30 59" src="https://user-images.githubusercontent.com/2062107/154256472-5b7da013-7f2e-4655-b90c-343f20ee31a5.png">



## Use in Client Application

1-You can use class LuckCartSDK to initialize LuckyCart, generate the data required by the LuckyCart platform and send cart data to the LuckyCart server.

```
var luckyCartSDK: LuckCartSDK? = null
luckyCartSDK = LuckCartSDK(mContext)
luckyCartSDK?.init(auth, null)

```
      
LuckyCartListenerCallback is an interface which contains response receive from LuckCart server

```
interface LuckyCartListenerCallback {
   fun listAvailableBanners(banners: Banners)
   fun getBannerDetails(banners: BannerDetails)
   fun sendCard(transactionResponse: TransactionResponse)
   fun getGame(game: GameResponse)
   fun onError(error: String?)
}
```
        
2-Start LuckyCart

Initialize the LuckyCart using object LCAuthorization and JsonObject which contains sdk configuration properties (URL Lucky Cart server, ...).

```
val auth = LCAuthorization(AUTH_KEY, "")
luckyCartSDK = LuckCartSDK(mContext)
// Object LCAuthorization contains the value of key and secret
luckyCartSDK?.init(auth, null)
```
3-Set the current user

If customer id is not passed in init, you can set it by calling the setUserId() function. Customer id is required to send cart information at checkout time.

```
luckyCartSDK?.setUser(CUSTOMER_ID)
```
     
4-Load and display Banner 

To Load banner you can call function listAvailableBanners():

```
luckyCartSDK?.listAvailableBanners()
```

To display list of banner, you should extend the interface LuckyCartListenerCallback and call this function setActionListener(callBack: LuckyCartListenerCallback?) in your class.

```
override fun listAvailableBanners(banners: Banners) {
     availableBanners = banners
     loadBannerHomePage()
 }
 ```

5-Display Banner Details

To get banner details,you can call the function getBannerDetails(pageType: kotlin.String, pageID: kotlin.String).

pageType: Current page type (homepage, cart, categories, etc...).
pageID: Current page ID

 ```
 luckyCartSDK?.setActionListener(this)
 luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, shopID)
 ```


6-Send Cart to LuckyCart

*Prepare data 

Each time your application does a check out, some data are sent to LuckyCart.The data should be of type JsonObject and contains cartId.
In this exemple, we create a simple JsonObject with the sample data from the LuckyCart application sample. It will then be merged with LuckyCart required fields before being sent:

```
val card = JsonObject()
card.addProperty("cartId", cardId)
card.addProperty("ttc", productPrice)
card.add("products", products)
```

*Send Data

Once your application did a succesful checkout, call this function to send ticket information to LuckyCart and receive an aknowledgment.

```
luckyCartSDK?.setActionListener(this)
luckyCartSDK?.sendCard(card)
```

7-Display Game

To get list of game, you can call function getGame with input cart id.

```
luckyCartSDK?.getGame(cardID)
```


      
