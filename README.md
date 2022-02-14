# Introduction
AuraFramework give you all embedded features to access certified product data for an user


# Getting Started
- [Requirements](#requirements)
- [Dependencies](#dependencies)
- [Installation](#installation)
- [Update](#sdk-update)
- [Bootstraping](#bootstraping)
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
- You must use [Cocoapods](https://cocoapods.org/) in your Swift Project.
- You must have an Azure Account, with paired SSH Key to access Pod repository.
- You must handle in your configuration a rootURL to boostrap the API. Please note this route could be different if you want to access different environnement, like DEV, TEST, UAT ... etc.
- You must have a route to fetch a RSA public key from your back-end (as PEM format)
- You must have a route to generate an authorization token

## Dependencies
This framework use many dependencies from opensource projects :
- [Alamofire](https://github.com/Alamofire/Alamofire)
- [SwiftyRSA](https://github.com/TakeScoop/SwiftyRSA)
- [CryptoSwift](https://cryptoswift.io/)

AuraFramework will always use latest "master" (stable) versions of theses dependencies. There is no specified versions in AuraFramework dependency file.

## Installation
If you already use CocoaPods for your project, please skip the next step.

If you don't use CocoaPods in your project, please follow this step.
- Open a terminal
- Navigate to your project path at .xcodeproj location
- Run this command
```bash
$ pod init
```

- Open "Podfile" and put this line
```ruby
pod 'AuraFramework', :git => 'git@ssh.dev.azure.com:v3/llfoundation/LUX/lux-front-sdk-ios'
```
- Open a terminal at YourProject.xcworkspace location and run the following command :

```bash
$ pod install
```

⚠️⚠️⚠️ WARNING ⚠️⚠️⚠️ : Please, be sure you always open your project from .xcworkspace file and never from the .xcodeproj


## SDK Update
Once you have added AURAFramework dependency to your project, be sure you're always up-to-date by running this bash command from the folder which located "Podfile" of your project
```bash
    pod update
```

This command will fetch latest version of framework by downloading latest commit from "master" branch.


## Bootstraping
This framework expose
- instance of [AURRESTCustomerEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEngine.swift&version=GBmaster) inherited from [AURRESTEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTEngine.swift&version=GBmaster)
- instance of [AURRESTVendorEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster) inherited from [AURRESTEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTEngine.swift&version=GBmaster)

Each instance of theses components expose a ".shared" property to access singleton pattern.

```swift
    //Instance of vendor/logistic API
    AURRESTVendorEngine.shared

    //Instance of customer API
    AURRESTCustomerEngine.shared
```

⚠️⚠️⚠️ WARNING ⚠️⚠️⚠️ :
- You shouldn't try to init theses components and use the ".shared" instance instead
- You shouldn't try to use [AURRESTEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTEngine.swift&version=GBmaster) directly. It just an root object to handle requests, authorization & encryption/decryption system.

Once you get
- the RSA public key, required to encrypt all posted requests from AuraFramework,
- an authorization token (JWT Format)

Use this method from the component you choose [.configure(forBaseURL url:String, withRSAKey rsa:String, andUserToken userToken:String)](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTEngine.swift&version=GBmaster&line=19&lineStyle=plain&lineEnd=20&lineStartColumn=1&lineEndColumn=1) to configure the framework when you are ready :
```swift
let authorizationToken : String = "Your-JWT-access_token-value"
let rsaPEMKey : String = "----- BEGIN PUBLIC KEY ...."
let rootURL : String = "https://root-url.tld"

// Bootstrap vendor/logistic API
AURRESTVendorEngine.shared.configure(forBaseURL: rootURL, withRSAKey: rsaPEMKey, andUserToken: authorizationToken)


//Bootstrap customer API
AURRESTCustomerEngine.shared.configure(forBaseURL: rootURL, withRSAKey: rsaPEMKey, andUserToken: authorizationToken)
```

## SDK Debug    
You could have to access raw payload sent and received from AuraFramework. Framework give you ability to print log about request
Use this [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTEngine.swift&version=GBmaster&line=25&lineStyle=plain&lineEnd=26&lineStartColumn=1&lineEndColumn=1) directly from targeted component of framework.

Usage
```swift
let activeDebug : Bool = true/false
// Vendor Engine
AURRESTVendorEngine.shared.setDebug(active: activeDebug)

// Customer Engine
AURRESTCustomerEngine.shared.setDebug(active: activeDebug)
```


Print sample :

```bash
[AURRESTEngine] Request to https://wa-lux-fnd-gateway-dev-weu.azurewebsites.net/api/tac/product/scan / Payload = Optional(["operatorId": Optional("vendor@acme.com"), "productId": Optional("TST-Sc01-1850_1-01")]) / Encrypted = I4X1SMbQ9MBQz880cs8vy/KmUCrnS2cYjZTUVMNpI1rNcmHCnjd0fqRhJVEcamy3a0ap7EhNNz54aKBgLlA9h+bHZox2LGt/Mglmuu/ezHle/e3jVeO0jEDh+ILqw80mQWoo6qRjztIjpqQTyu43/o5JEy5eRc9JXD7WiZrn06MuyOvsMzIc1rIa7T5Z05odrSj55hkNT7vectLZvTvMh6nM8BjFIXeewOImoEO/BsVjEJIAuVtP56OWTnsg7zMdXsRyijcHohzmmiFwEmLSM7tsXo9Mr4nNefPks22Zfrua3cnt5vlo6K4454LGDU4acGiv+EIs/kMWiaDYh2qWog== / StatusCode = 200 / Decrypted response = {"data":{"skuCode":"M40818","skuName":"PETIT NOE MNG","image":"https://fr.louisvuitton.com/images/is/image/lv/1/PP_VP_L/louis-vuitton-sac-seau-petit-no%C3%A9-nm-toile-monogram-sacs-%C3%A0-main--M40818_PM2_Front%20view.jpg?wid=1364&hei=1364"},"otherProperties":{"SERIAL":"TST-Sc01-1850_1-01","SKU CODE":"M40818","SKU NAME":"PETIT NOE MNG","MADE IN":"FQ","CITES":"\"\""}}

```

## Generic callbacks
Each methods which call an API end-point provide a completion block with :
- Optional Fetched structured Object or Data : Could be a structured models which can be found in framework [here](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels&version=GBmaster)
- Optional rawString : Decrypted raw text from server as [String](https://developer.apple.com/documentation/swift/string)
- Optional Error : Request encoutered an HTTP error, or any local error due to a bad configuration  [Error] (https://developer.apple.com/documentation/swift/error)
- statusCode : Each method extract HTTP Status code to give end-developer a direct access to this value. This data is exposed as [Int](https://developer.apple.com/documentation/swift/int)

## Error types
There are some typed error from framework you could catch and get a explaination of your problem. The class [AURError](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURError.swift&version=GBmaster) give you an enum of error code generated by framework

Please refer to [AURErrorType](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURError.swift&version=GBmaster&line=10&lineStyle=plain&lineEnd=11&lineStartColumn=1&lineEndColumn=1) to check errorCode

```swift
    AURRESTCustomerEnginer.shared.function() { (jsonObject, rawString, error, statusCode) in
        if let e = error {
            switch error.code {
                case AURErrorType.noURL.rawValue:
                    // you haven't specify rootURL for AuraFramework
                    break
                case AURErrorType.noRSA
                    //you haven't specify the required RSA public key for payload encryption
                    break
                case AURErrorType.noAuthorizationKey
                    // you haven't specify authorization token, or your token is expired
                    break
                case AURErrorType.noRandomAES
                    //There is a problem while generatig random AES key pair
                    break
                case AURErrorType.noReadableData
                    //There was a problem while parsing data, returned payload from server was empty
                    break
                default:
                    //This is commonly an HTTP error
                    break
            }
        }    
    }
```


## Server health status
You can access to Server health status data if you want to show through application with this [method](). It shows :
- Ping status
- Response time in ms
- IP Address

⚠️⚠️⚠️ WARNING ⚠️⚠️⚠️ : This method requires only an authorization token, and you must use this method with [AURRESTCustomerEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEngine.swift&version=GBmaster) or [AURRESTVendorEngine](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster) with ".shared" instance property.

Signature
```swift
public func AURAStatus(completion:@escaping([String:Any?]?, String?, Error?, Int) -> Void)
```

Exemple :
```swift
    AURRESTVendorEngine.shared.AURAStatus { (data, raw, error, statusCode) in
            if let e = error {

            } else {
                // you can access "data" to direct cast as [String : Any?]
                // you can access "raw" (String?) to parse as JSON this value
            }
        }
```

## Customer API References
- [Get a product list](#customer-api-get-product-list)
- [Get a product history](#customer-api-get-product-history)
- [Scan a TAC](#customer-api-scan-tac) (TAC : Transfer Authorization Code)
- [Consume a TAC](#customer-api-consume-tac)
- [Create a TAC](#customer-api-create-tac)
- [Get a transaction](#customer-api-get-transaction)



### Customer API Get product list
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEnginer.swift&version=GBmaster&line=26&lineStyle=plain&lineEnd=27&lineStartColumn=1&lineEndColumn=1) fetch an optional array of product, [AURProductItem](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductItem.swift&version=GBmaster)

Signature :
```swift
    open func getUserProducts(completion:@escaping ([AURProductItem]?, String?, Error?, Int) -> Void)
```

Usage :
```swift
    AURRESTCustomerEngine.shared.getUserProducts { (items, raw, error, statusCode) in
        //before reading products variable, be sure API request was successfully completed:
        if let e = error {
            //you have an error
        } else  {
            //you can access to 'items' to refresh your list
            //you can access to 'raw' to parse as JSON this value
        }
    }
```


### Customer API Get product history
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEnginer.swift&version=GBmaster&line=37&lineStyle=plain&lineEnd=38&lineStartColumn=1&lineEndColumn=1) fetch a detailled object of a certified product, [AURProductHistory](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductHistory.swift&version=GBmaster)

This object contains :
- "transactions" : a list of validated blockchain transactions [AURProductTransaction](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductTransaction.swift&version=GBmaster)
- "product" : an optional object from product catalog [AURProductInformation](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductInformation.swift&version=GBmaster)



Signature:
```swift
   open func getProductHistory(productId:String, completion:@escaping(AURProductHistory?, String?, Error?, Int) -> Void)
```

Usage:
Ensure you use a "productId" string value as parameter
```swift
    let productId : String = "your-product-id"
    AURRESTCustomerEngine.shared.getProductHistory(productId: productId) { (history, raw, error, statusCode) in
        if let e = error {

        } else {
            //you can access to 'history' object to show your page
            //you can access to 'raw' to parse as JSON this value
        }
    }
```


### Customer API Scan TAC
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEnginer.swift&version=GBmaster&line=49&lineStyle=plain&lineEnd=50&lineStartColumn=1&lineEndColumn=1) should be used once you scan the Aura QR which represents a TAC id. The method fetch a product information [AURProductInformation](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductInformation.swift&version=GBmaster)

You can access to the "data" property which contains some quick information about product [AURProductDataInformation](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductDataInformation.swift&version=GBmaster)
- skuName : SKU name of the product
- skuCode : SKU code of the product
- image : direct link to the image


Signature :
```swift
    open func validateTAC(tacId:String, completion:@escaping(AURProductInformation?, String?, Error?, Int) -> Void)

```

Usage:
Ensure you use a "tacId" string value as parameter from your scan
```swift
    let tacId : String = "your-scanned-tac-id"
    AURRESTCustomerEngine.shared.validateTAC(tacId: tacId) { (productInformation, raw, error, statusCode) in
        if let e = error {

        } else {
            // you can access 'productInformation' to have fetched data
            // you can access 'raw' to parse as JSON this value
        }
    }
```

### Customer API Consume TAC
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEnginer.swift&version=GBmaster&line=61&lineStyle=plain&lineEnd=62&lineStartColumn=1&lineEndColumn=1) is the last step to accept ownership of a product for a user.
This method returns :
- [AURProductTransaction?](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductTransaction.swift&version=GBmaster&line=9&lineStyle=plain&lineEnd=9&lineStartColumn=1&lineEndColumn=46) : product transaction
- String? : raw decrypted String as UTF8
Signature
```swift
    open func consumeTAC(tacId:String, completion:@escaping(AURProductTransaction?, String?, Error?, Int) -> Void) {
```

Usage:
Ensure you use the previous validated TAC id in consume method :
```swift
    let tacId : String = "your-scanned-tac-id"
    AURRESTCustomerEngine.shared.consumeTAC(tacId: tacId) { (data, raw, error, statusCode) in
        if let e = error {

        } else {
            //product ownership acceptation demand was correctly received from server
            // now you can drop the acceptation page and redirect user to his refresh product list
        }
    }
```
### Customer API Create TAC
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTCustomerEnginer.swift&version=GBmaster&line=71&lineStyle=plain&lineEnd=72&lineStartColumn=1&lineEndColumn=1) create a TAC to allow user to transfer his product. The destination could be a vendor (in case of product return), or an other user (in case of ownership transfer)
This method returns
- String? : This is a cleaned tacId from rawResponse
- String? : raw decrypted text response (value could be a HTTP hyperlink)

Signature:
```swift
    open func createTAC(productId:String, completion:@escaping(String?, String?, Error?, Int) -> Void)
```

Usage:
Ensure you use a product id in create TAC method :
```swift
    let productId : String = "your-transferable-product-id"
    AURRESTCustomerEngine.shared.createTAC(productId: productId) { (tacId, raw, error, statusCode) in
        if let e = error {

        } else {
            // you can access
        }
    }
```
### Customer API Get Transaction
This method returns a transaction based on its ID

This method returns
- [AURProductTransaction?](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductTransaction.swift&version=GBmaster&line=9&lineStyle=plain&lineEnd=9&lineStartColumn=1&lineEndColumn=46) : product transaction
- String? : raw decrypted String as UTF8

Signature:
```swift
    open func getProductTransaction(txId:String, completion:@escaping(AURProductTransaction?, String?, Error?, Int) -> Void)
```

Usage:

```swift
    let transactionId : String = "your-transaction-id"
    AURRESTCustomerEngine.shared.getProductTransaction(txId: transactionId) { (transaction, raw, error, statusCode) in
            if let e = error {

            } else {
                // you can access
            }
        }
```

## Customer Model Extensions
### [AURProductTransaction](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=30&lineStyle=plain&lineEnd=31&lineStartColumn=1&lineEndColumn=1)

Get a real date :
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=64&lineStyle=plain&lineEnd=65&lineStartColumn=1&lineEndColumn=1) parse "eventDate" property from this String format : "yyyy-MM-dd'T'HH:mm:ss.SSSSZ"

### [AURProductItem](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=146&lineStyle=plain&lineEnd=147&lineStartColumn=1&lineEndColumn=1)

Get a state as enum of [AURCurrentProductStatus](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=9&lineStyle=plain&lineEnd=10&lineStartColumn=1&lineEndColumn=1) from ownership status of the product when you got a list of certificate :
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=162&lineStyle=plain&lineEnd=163&lineStartColumn=1&lineEndColumn=1) give you a state of the certificate to avoid String comparison

## Vendor API references
- [Global process](#vendor-api-workflow)
- [Get card pairing status](#vendor-api-get-card-pairing-status)
- [Get product information](#vendor-api-get-product-information)
- [Pair card with product](#vendor-api-pair-card-with-product)
- [Activate Card](#vendor-api-activate-card) Or [Extend Card](#vendor-api-extend-card)
- [Product Return](#vendor-api-product-return)

### Global process
Please follow theses steps to insert a validated paired TAC with a specified product in AURA System. At the end of this workflow, a customer should be able to scan a TAC (AURA Card) to acquire product's ownership.

- Step 1. Check card pairing status with an AURA card  [Get card pairing status](#vendor-api-get-card-pairing-status)
        - 404 HTTP status : You can pair this card
        - 409 HTTP status : This card is already paired, you have to choose which action you need to do (check Step 4)  
- Step 2. Check your serial number is valid in AURA System [Get product information](#vendor-api-get-product-information)
- Step 3. Pair card with product [Pair card with product](#vendor-api-pair-card-with-product)
- Step 4. (Elapsed time between previous step & this one is unknow) In store, a vendor scan the card [Activate Card](#vendor-api-activate-card) to give it with the product to the customer. This card could be in a expired state. In this case, you should use [Extend Card](#vendor-api-extend-card) service to updated state. Customer can now scan this card as a TAC, and acquire product ownership.


### Vendor API Get card pairing status

This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster&line=25&lineStyle=plain&lineEnd=26&lineStartColumn=1&lineEndColumn=1) provide a status about scanned card identifier. This method returns an optional [AURAURACardPairingStatus](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURAURACardPairingStatus.swift&version=GBmaster)

Parameters
- "cardId" (String) : a card identifier (provided by a QR Scan or manual input)
- "operatorId" (String) : an operator identifier provided by your system

Signature
```swift
    open func getCardPairingStatus(cardId:String, operatorId:String, completion:@escaping(AURAURACardPairingStatus?, String?, Error?, Int) -> Void)
```

Usage
Ensure you have an "operator identifier" provided by your system
Ensure you have an unpaired AURA Card identifier
```swift
    let operatorIdentifier = "your-operator-identifier"
    let cardIdentifier = "scanned-card-identifier"
    AURRESTVendorEngine.shared.getCardPairingStatus(cardId: cardIdentifier, operatorId: operatorIdentifier) { (data, raw, error, statusCode) in
        switch statusCode {
            case 404:
                //you can pair this card
                break
            case 409:
                //this card is already paired with a product, check status of this card
                break
            default:
                //check data / raw  or error
                break
        }

    }
````


### Vendor API Get product information

This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster&line=36&lineStyle=plain&lineEnd=37&lineStartColumn=1&lineEndColumn=1) provide product information to validate serial number (product identifier). This method return an optional [AURProductInformation](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURProductInformation.swift&version=GBmaster)

Parameters :
- "operatorId" (String) : an operator identifier provided by your system
- "productId" (String) : a product identifier provided by manual input, QR, BarCode or NFC scan

Signature
```swift
    open func getProductInfo(productId:String, operatorId:String, completion:@escaping(AURProductInformation?, String?, Error?, Int) -> Void)
```

Usage
Ensure you have an "operator identifier" provided by your system
Ensure you have a "productId" (provided by manual input, QR, BarCode or NFC scan)
```swift
    let operatorIdentifier = "your-operator-identifier"
    let productId = "your-product-identifier"
    AURRESTVendorEngine.shared.getProductInfo(productId: productId, operatorId:operatorIdentifier) { (data, raw, error, statusCode) in
        if let e = error {

        } else {
            // you can access "data" to cast as AURProductInformation
            // you can access "raw" to parse as JSON this value
        }
    }
````


### Vendor API Pair card with product

This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster&line=47&lineStyle=plain&lineEnd=48&lineStartColumn=1&lineEndColumn=1) provide pairing between scanned AURA Card with a specified product identifier

Parameters:
- "operatorId" : an operator identifier provided by your system
- "productId" : Scanned (NFC/QR/BarCode/Manual Input) product identifier
- "tacId" : Scanned card identifier



Signature
```swift
    open func pairCardWithProduct(productId:String, tacId:String, operatorId:String, completion:@escaping([String:Any?]?, String?, Error?, Int) -> Void)
```

Usage
Ensure you use product and card identifiers used in step 1 & 2
Ensure you have an "operator identifier" provided by your system
```swift
    let operatorIdentifier = "your-operator-identifier"
    let cardIdentifier = "scanned-card-identifier"
    let productId = "your-product-identifier"
    AURRESTVendorEngine.shared.pairCardWithProduct(productId: productId, cardId: cardIdentifier, operatorId:operatorIdentifier) { (data, raw, error, statusCode) in
        if let e = error {

        } else {
            // Logistic journey is now over. A vendor could activate later this AURA Card after selling a product
        }
    }
````


### Vendor API Activate Card

This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster&line=58&lineStyle=plain&lineEnd=59&lineStartColumn=1&lineEndColumn=1) provide to a vendor ability to activate paired AURA Card. Once it activated, customer can scan this card to take product ownership.

Parameters:
- "cardId" : Scanned card identifier
- "operatorId" : Your operator identifier

Signature
```swift
     open func activateCard(cardId:String, operatorId:String, completion:@escaping([String:Any?]?, String?, Error?, Int) -> Void)
```

Usage
Ensure you have a "card identifier", provided by QR Scan
Ensure you have an "operator identifier" provided by your system
```swift
    let operatorIdentifier = "your-operator-identifier"
    let cardIdentifier = "scanned-card-identifier"
    AURRESTVendorEngine.shared.activateCard(cardId: cardIdentifier, operatorId: operatorIdentifier) { (data, raw, error, statusCode) in
        if let e = error {

        } else {
            // Card is now activated
            // Customer can scan this card to acquire product ownership
        }
    }
````

### Vendor API Extend Card
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster&line=69&lineStyle=plain&lineEnd=70&lineStartColumn=1&lineEndColumn=1) provide a route to extend an expired AURA Card.


Parameters:
- "cardId" : Scanned card identifier
- "operatorId" : Your operator identifier

Signature
```swift
     open func extendCard(cardId:String, operatorId:String, completion:@escaping([String:Any?]?, String?, Error?, Int) -> Void)
```

Usage
Ensure you have a "card identifier", provided by QR Scan
Ensure you have an "operator identifier" provided by your system
```swift
    let operatorIdentifier = "your-operator-identifier"
    let cardIdentifier = "scanned-card-identifier"
    AURRESTVendorEngine.shared.extendCard(cardId: cardIdentifier, operatorId: operatorIdentifier) { (data, raw, error, statusCode) in
        if let e = error {

        } else {
            // Card is now activated
            // Customer can scan this card to acquire product ownership
        }
    }
````

### Vendor API Product Return
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FCore%2FAURRESTVendorEngine.swift&version=GBmaster&line=80&lineStyle=plain&lineEnd=81&lineStartColumn=1&lineEndColumn=1) provide a route for vendor to return back a customer product in store.

Parameters
- "productId" : Product identifier
- "operatorId" : Your operator identifier

Signature
```swift
    open func returnProduct(productId:String, operatorId:String, completion:@escaping([String:Any?]?, String?, Error?, Int) -> Void)
```

Usage
Ensure you have a "product identifier"
Ensure you have a "card identifier", provided by QR Scan
```swift
    let operatorIdentifier = "your-operator-identifier"
    let productIdentifier = "your-product-identifier"
    AURRESTVendorEngine.shared.returnProduct(productId: productIdentifier, operatorId: operatorIdentifier) { (data, raw, error, statusCode) in
        if let e = error {

        } else {
            // Product is no longer owned by customer
        }
    }
```
## Vendor Model Extensions

### [AURAURACardPairingStatus](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=174&lineStyle=plain&lineEnd=175&lineStartColumn=1&lineEndColumn=1)

Get parsed date for "expireIn" directly from top object :
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=187&lineStyle=plain&lineEnd=188&lineStartColumn=1&lineEndColumn=1) give you a real date of expiration


Get parsed date for "extendedExpireIn" directly from top object :
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=191&lineStyle=plain&lineEnd=192&lineStartColumn=1&lineEndColumn=1) give you a real date of extended expiration

Get activation status directly from top object :
This [method](https://dev.azure.com/llfoundation/LUX/_git/lux-front-sdk-ios?path=%2FAuraFramework%2FClasses%2FModels%2FAURModel%2BExtensions.swift&version=GBmaster&line=195&lineStyle=plain&lineEnd=196&lineStartColumn=1&lineEndColumn=1)  give you the value of .activationStatus.isActivated
