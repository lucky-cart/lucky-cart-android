## Requirements
- You need to install AndroidStudio


## Dependencies
You can implement library luckCart in build.gradle or add file .aar library

Gradle Configuration:

Add it in your settings.gradle at the end of repositories:
```
dependencyResolutionManagement {
    repositories {
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
Add the dependency in your application build.gradle file
```
dependencies {
    //luckCart
    implementation 'com.github.lucky-cart:lucky-cart-android:1.0.14'
}
```


## Download or clone the sample application

All the sample code in this documentation is extracted from the project <a href="https://github.com/lucky-cart/lucky-cart-client-sample-android" target="_blank">LuckyCartSample</a>

It is a simple shopping application draft using a trivial shop model ( Shop, Customer, Cart, Products and Orders ).

   <img width="500" alt="Screen Shot 2022-02-16 at 12 30 59" src="https://user-images.githubusercontent.com/2062107/154256472-5b7da013-7f2e-4655-b90c-343f20ee31a5.png">



## Use in Client Application

1- In your application class, You will need to init your LuckCartSDK, then you will be able to use it everywhere in the project.
Just first initialize LuckyCart by sending the data required by the LuckyCart platform.

```
class ApplicationSampleLuckyCart: Application() {

    var luckyCartSDK: LuckCartSDK? = null

    override fun onCreate() {
        super.onCreate()
        // initialize Lucky SDK here
        val auth = LCAuthorization(AUTH_KEY, "")
        luckyCartSDK = LuckCartSDK(applicationContext)
        luckyCartSDK?.init(auth, null)
        luckyCartSDK?.setUser(CUSTOMER_ID)
        // get list of available banners when application start
        luckyCartSDK?.listAvailableBanners() 
        // list of available banners will be saved in a shared preference in the SDK and will accessible from any class in the project
    }

}
```

You can then add a banner in your layout:

```
<com.luckycart.views.BannerView
    android:id="@+id/bannerView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```
      
LuckyCartListenerCallback is an interface which contains response receive from LuckCart server, this is managed in the SDK
no need to create LuckyCartListenerCallback.

```
interface LuckyCartListenerCallback {
   fun onRecieveListAvailableBanners(banners: Banners)
   fun onRecieveBannerDetails(bannerDetails: BannerDetails)
   fun onRecieveSendCartTransactionResponse(transactionResponse: TransactionResponse)
   fun onRecieveListGames(gameResponse: GameResponse)
   fun onError(error: String?)
}
```

You will need to implement LuckyCartListenerCallback to recieve and override what happen when you get all those events above

```
class MainViewModel : ViewModel(), LuckyCartListenerCallback {

   private lateinit var mContext: Context
   var luckyCartSDK: LuckCartSDK? = null

   fun setContext(context: Context) {
        mContext = context
        // Get luckyCart instance
        luckyCartSDK = (mContext.applicationContext as ApplicationSampleLuckyCart).luckyCartSDK
        luckyCartSDK?.setActionListener(this)
   }
   
   private fun loadBannerHomePage() {
        getBannerCategory = false
        Prefs(mContext).banners?.homepage?.forEach { format ->
            luckyCartSDK?.getBannerDetails(BANNER_HOMEPAGE, format, "")
        }
    }
    
    fun loadBannerCategory(pageId: String) {
        getBannerCategory = true
        Prefs(mContext).banners?.categories?.forEach {
            if (it.contains(pageId) && !it.contains("search")) luckyCartSDK?.getBannerDetails(
                BANNER_CATEGORIES,
                it,
                ""
            )
        }
    }
   
   override fun onRecieveListAvailableBanners(banners: Banners) {
        loadBannerHomePage()
    }

    override fun onRecieveBannerDetails(bannerDetails: BannerDetails) {
        if (bannerDetails.name != null) {
            if (getBannerCategory) {
                getBannerCategoryDetails.value = GetBannerState.OnSuccess(bannerDetails)
            } else getBannerDetails.value = GetBannerState.OnSuccess(bannerDetails)
        } else {
            getBannerCategoryDetails.value = GetBannerState.OnError("error")
        }
    }
    
    override fun onRecieveSendCartTransactionResponse(transactionResponse: TransactionResponse) {
        luckyCartSDK?.getGame(cartID)
    }

    override fun onRecieveListGames(gameResponse: GameResponse) {
        (mContext as MainActivity).showFragmentGame(gameResponse.games)
    }

    override fun onError(error: String?) {
        Toast.makeText(mContext, "Error: $error", Toast.LENGTH_SHORT).show()
    }
    
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


      
