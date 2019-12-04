
# Sport Places Finder
  
Coded by :
 - Jean-Christophe CHEVALIER   
 - Alexandre LADRIERE   

## Description  

This application uses two APIs from Décathlon: __Sport Places API__ (`https://developers.decathlon.com/products/sport-places`) and __Sport API__ (`https://developers.decathlon.com/products/sports`).

This application allows the user to find all the places where he can practice the chosen sport(s) within the specified search radius with respect to his current position. 

The Sport API is used to propose the list of sports, and the Sport Places API is used to retrieve the places. 

In order to retrieve the data, the client sends a GET request to the server, in the following form:
`https://sportplaces.api.decathlon.com/api/v1/places?origin=X&radius=Y&sports=Z`

  - `X`: the current position of the user.

  - `Y`: the search radius in km.

  - `Z`: the code identifying the sport indicated. Each sport is associated with a unique code, retrieve with the Sport API.

__Note__: The Decathlon API database does not contain all the sports places in the world. The API may not return any results even if installations exist. To test the application and see the type of results returned, it is best to place yourself in Montreal (Quebec, CA) and do a search for sports such as Fitness, Ice Hockey, Football, ...
Moreover, there are no direct links for image in the data returned by the request. An image reference could be retrieve for each place by using the following request:
`https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=PLACE_NAME&inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry&key=YOUR_API_KEY`

Then, the image can be retrieve thanks to the following request: `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=PHOTO_REFERENCE&key=YOUR_API_KEY`, where `PHOTO_REFERENCE` is the photo reference returned by the previous request.

However, the Google Place Image API is limited to 1 request/month in free version. Therefor, we can not use it and we are not able to get different images for each places. That is why we decided to use the same for each place.  

## Libraries 
  
  - Converter: Gson: `'com.squareup.retrofit2:converter-gson:2.6.2'`
  - Retrofit: `'com.squareup.retrofit2:retrofit:2.6.2'`
  - Adapter: RxJava 2: `'com.squareup.retrofit2:adapter-rxjava2:2.3.0'`
  - [Ramotion] - [Fluid Slider]: `"com.ramotion.fluidslider:fluid-slider:0.3.1"`
  - RxAndroid: `'io.reactivex.rxjava2:rxandroid:2.1.1'`
  - Gson: `'com.google.code.gson:gson:2.8.6'`
  - Material Components for Android: `'com.google.android.material:material:1.0.0'`
  - Play Services Plus: `'com.google.android.gms:play-services-plus:17.0.0'`
  - Play Services Maps: `'com.google.android.gms:play-services-maps:17.0.0'`
  - Place Services Location: `"com.google.android.gms:play-services-location:17.0.0"`
  - Android AppCompat Library: `'androidx.appcompat:appcompat:1.1.0'`
  - Core Kotlin Extensions: `'androidx.core:core-ktx:1.1.0'`
  - Android Constraint Layout: `'androidx.constraintlayout:constraintlayout:1.1.3'`
  - Legacy Support: `'androidx.legacy:legacy-support-v4:1.0.0'`
  - Android Support Recycler View: `'androidx.recyclerview:recyclerview:1.1.0'`

  
## Instructions  
  
Link to the subject : https://docs.google.com/presentation/d/1mwu2xx7_qfCZDfsRxseC94n7oBGYfhw-9xIftaTDbzk/edit#slide=id.p97  
  
### Purpose of the project  
  
 - The goal of the project is to create an application to visualize a series of OpenData data  
 - The data will have to be retrieved from a remote server and displayed in a list and on a map  
 - Clicking on an item in the list or on a marker on the map gives access to a screen showing the details of the item  
 - A screen will present general information about the recovered data  
  
### Requirements    
- OpenData data format:   
	 - Json format  
	 - With a field corresponding to the url of an image  
	 - With a field corresponding to a position  
 - Application composed of at least 3 Fragments and 2 Activities  
 - An Actionbar will be present and will refresh the recovered and displayed data
 
 ## Bonus
 
 - Improved user experience:
    - Clustering of markers on the map according to the zoom level
    - Setting up a search/filter system on the displayed list
 
 - Technical enhancements :
    - Setting up a local database to display the item list in offline mode
    - Using LiveData or Observable for data recovery in the database
 
 ## License
 This project is licensed under the MIT License - see the [LICENSE] file for details.


  [Ramotion]: <www.ramotion.com>
  [Fluid Slider]: <https://github.com/Ramotion/fluid-slider-android>
  [LICENSE]: <LICENSE>