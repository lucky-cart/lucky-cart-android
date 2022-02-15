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

## Use in Client Application
1_You can use class LuckCartSDK to initialize LuckyCart, generate the data required by the LuckyCart platform and send cart data to the LuckyCart server.

        var luckyCartSDK: LuckCartSDK? = null
        luckyCartSDK = LuckCartSDK(mContext)
        luckyCartSDK?.init(auth, null)
        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.listAvailableBanners()

        
_LuckyCartListenerCallback is an interface wich contains response receive from LuckCart server 

interface LuckyCartListenerCallback {
    
    fun listAvailableBanners(banners: Banners)
    fun getBannerDetails(banners: BannerDetails)
    fun sendCard(transactionResponse: TransactionResponse)
    fun getGame(game: GameResponse)
    fun onError(error: String?)

}         

        
2_Start LuckyCart
Initialize the LuckyCart using object LCAuthorization and JsonObject which contains sdk configuration properties (URL Lucky Cart server, ...).
    
    // Object LCAuthorization contains the value of key and secret
    val auth = LCAuthorization(AUTH_KEY, "")
    luckyCartSDK = LuckCartSDK(mContext)
    luckyCartSDK?.init(auth, null)
   
    
3_Set the current user
If customer id is not passed in init, you can set it by calling the setUserId() function. Customer id is required to send cart information at checkout time.
    luckyCartSDK?.setUser(CUSTOMER_ID)
       
4_ Load and display Banner 
To Load banner you should just call function listAvailableBanners():
luckyCartSDK?.listAvailableBanners()
To display list of banner you should extend the interface LuckyCartListenerCallback and call this function setActionListener(callBack: LuckyCartListenerCallback?) in your class.
     

5_Display Banner Details
To get banner details,you can call the function getBannerDetails(pageType: kotlin.String, pageID: kotlin.String).
pageType: Current page type (homepage, cart, categories, etc...).
pageID: Current page ID
       
      luckyCartSDK?.setActionListener(this)
      luckyCartSDK?.getBannerDetails(BANNER_CATEGORIES, shopID)

6_Send Cart to LuckyCart
_Prepare data 
Each time your application does a check out, some data are sent to LuckyCart.The data should be of type JsonObject and contains cartId.
In this exemple, we create a simple JsonObject with the sample data from the LuckyCart application sample. It will then be merged with LuckyCart required fields before being sent:

     val products = JsonArray()
        val card = JsonObject()
        val cardId = CARD_ID + Random().nextInt(9999999)
        listProductAddedToCard.toSet().toList().forEach {
            val product = JsonObject()
            product.addProperty("productId", it.product.name)
            product.addProperty("ttc", it.product.price.toString())
            product.addProperty("quantity", it.numberOfProduct.toString())
            products.add(product)
        }
        card.addProperty("cartId", cardId)
        card.addProperty("ttc", productPrice)
        card.add("products", products)             
_Send Data
Once your application did a succesful checkout, call this function to send ticket information to LuckyCart and receive an aknowledgment.

        luckyCartSDK?.setActionListener(this)
        luckyCartSDK?.sendCard(card)
        
7_Display Game
To get list of game, you can call function getGame with input cart id.

      luckyCartSDK?.setActionListener(this)
      luckyCartSDK?.getGame(cardID)
      
