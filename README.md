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
    implementation 'com.github.lucky-cart:lucky-cart-android:1.0.18'
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
        // set PollingConfig (retryAfter and maxAttempts by API)
        luckyCartSDK?.setPollingConfig(500L, 5)

        // get list of available banners when application start
        luckyCartSDK?.getBannersExperience(page_type = "Homepage", format = "banner")
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

then send the banner details with the Click to get BannerClicked Event:

```
    bannerView.setBannerParams(item, clickListener, listener)
```
      
LuckyCartListenerCallback is an interface which contains response receive from LuckCart server, this is managed in the SDK
no need to create LuckyCartListenerCallback.

```
    interface LuckyCartListenerCallback {
        fun onBannerListReceived(bannerList: List<Banner>)
        fun onBannerDetailReceived(banner: Banner)
        fun onPostEvent(success: String?)
        fun onGameListReceived(gameList: List<GameExperience>)
        fun onError(error: String?)
    }
```

You will need to implement LuckyCartListenerCallback to receive and override what happen when you get all those events above

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
   
   // Get BannersExperience List
   fun getBannersExperience(){
       luckyCartSDK?.getBannersExperience(page_type = "Homepage", format = "banner")
   }
   
   //Callback Of getBannersExperience Api
   override fun onBannerListReceived(bannerList: List<Banner>) {
       
   }
   
   // Get BannerExperience Detail 
   fun loadBannerCategory(pageId: String) {
        getBannerCategory = true
        luckyCartSDK?.getBannerExperienceDetail(page_type = BANNER_CATEGORIES, format = "banner", pageId = pageId)
   }
    
   //Callback Of getBannerExperienceDetail Api
   override fun onBannerDetailReceived(banner: Banner) {
       
   }
   
   // Get GamesAccess List 
   fun getGameList(){
       if(cardID == "cartValidated"){
                val filters = arrayListOf<Filter>()
                filters.add(Filter(filterProperty = "cartId", filterValue = "filtervalue"))
                luckyCartSDK?.getGamesAccess(siteKey = AUTH_KEY, count = 1, filters = GameFilter(filters = filters))
       }
   }
   
   //Callback Of getGamesAccess Api
   override fun onGameListReceived(gameList: List<GameExperience>) {

   }
   
   //Callback of Api error
   override fun onError(error: String?) {
   
   }
   
   //Post Event bannerViewed to User
   fun bannerViewed(banner : Banner){
        cardID = ""
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card", operationId = banner.operationId)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "bannerViewed", eventPayload)
   }
   
   //Post Event bannerDisplayed to User
   fun bannerDisplayed(){
        cardID = ""
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card")
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "pagerView", eventPayload)
   }
   
   //Post Event bannerClicked by User
   fun bannerClicked(banner : Banner){
        cardID = ""
        val eventPayload =EventPayload(pageType = "homepage",pageId = null,bannerType = "banner", bannerPosition = "homepage card", operationId = banner.operationId)
        luckyCartSDK?.sendShopperEvent(AUTH_KEY, "bannerClicked", eventPayload)
   }

   //Callback of sendShopperEvent Api
   override fun onPostEvent(success: String?) {

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
        
    // set PollingConfig (retryAfter and maxAttempts by API)
    luckyCartSDK?.setPollingConfig(500L, 5)
```
3-Set the current user

If customer id is not passed in init, you can set it by calling the setUserId() function. Customer id is required to send cart information at checkout time.

```
    luckyCartSDK?.setUser(CUSTOMER_ID)
```
     
4-Load and display Banner 

To Load bannersList, you can call getBannersExperience() function:

```
    luckyCartSDK?.getBannersExperience(page_type = "Homepage", format = "banner")
```

To display list of banner, you should extend the interface LuckyCartListenerCallback and call this function setActionListener(callBack: LuckyCartListenerCallback?) in your class.

```
    override fun onBannerListReceived(bannerList: List<Banner>) {
           
    }
 ```

5-Display Banner Details

To get banner details,you can call the function getBannerExperienceDetail(page_type = BANNER_CATEGORIES, format = "banner", pageId = pageId).

page_type: Current page type (homepage, cart, categories, etc...).
pageId: Current page ID

 ```
 luckyCartSDK?.setActionListener(this)
 luckyCartSDK?.getBannerExperienceDetail(page_type = BANNER_CATEGORIES, format = "banner", pageId = pageId)
 
 ```


6-Sending Events to LuckyCart

* Prepare the data

LuckyCart allows the user to send the details of the transaction that has just been performed by the consumer in custom Object named "Event".
We will Provide all fields need it for the current transaction in EventPayload Object.

```
Event(
    val shopperId: String,
    val siteKey: String,
    val eventName: String? = null,
    val ipAddress: String? = null,
    val payload: EventPayload? = null
)
EventPayload(
    val cartId: Long? = null,
    val page: String? = null,
    val pageType: String? = null,
    val pageId: String? = null,
    val operationId: String? = null,
    val products: List<Product>? = null
    ...
)
```

* Send Current Event 

LuckyCart lets you to send various events to Lucky Cart services with sendShopperEvent function.
you can send the event type with eventName field like(bannerViewed, pagerView, bannerClicked, cartValidated)

```
   luckyCartSDK?.sendShopperEvent(siteKey= AUTH_KEY, eventName = "bannerViewed", eventPayload = EventPayload())
   
   or 
   
   val products = arrayListOf<SDKProduct>()
   products.add(SDKProduct())
   products.add(SDKProduct())
   val eventPayload =EventPayload(cartId = timesTamp,"currency", "options", products = products)

   luckyCartSDK?.sendShopperEvent(siteKey=AUTH_KEY,eventName = "cartValidated",eventPayload = eventPayload)  
   
   // check if sendShopper send it successfully
    override fun onPostEvent(success: String?) {
        
    }
      
```

7-Display Game

To display a list of games you need to call getGamesAccess function with the right gamesFilter object 

```
    val filters = arrayListOf<Filter>()
    filters.add(Filter(filterProperty = "cartId", filterValue = "filter Value"))
    luckyCartSDK?.getGamesAccess(siteKey = AUTH_KEY, count = 1, filters = GameFilter(filters = filters))

```

The game list will be provided in onGameListReceived callback with all games available to display it

```
    override fun onGameListReceived(gameList: List<GameExperience>) {
       
    }

```
Below you can see this video as a sample of the sdk usage.

https://user-images.githubusercontent.com/2062107/211288636-825e6861-39fb-4271-81f4-17daff14a269.mp4



      
