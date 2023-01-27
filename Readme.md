Simple readme file for project details

Title: Weather app
Description: A simple lightweight weather app that displays below fields
   1. Current location ( City and Country )
   2. Current temperature in Celsius
   3. Time of Sunrise/Sunset, 
   4. Current weather status 

* Also has some other features like below:
   1. Login
   2. Sign-up
   3. Capture current location and load weather report at that location. Uses open weather API
      (https://openweathermap.org/) to capture current weather report
   4. History of previously captured weather records 

* Project uses ROOM database to store user and weather details therefor has 2 respective tables
* Uses below versions
    a. Android studio: Android Studio Dolphin | 2021.3.1
    b. gradle version: 7.4
    c. gradle plugin version: 7.3.0
* Used standard android MVVM architecture in Kotlin
* Supports Android version L (version code 21) and above

* Uses below libraries:
    a. Retrofit - for network calls
    
    b. OkHttp - http client
    
    c. GSON converter - serialization of JSON
    
    d. Picasso -  image loading
    
    e. ROOM -  local db
    
    f. dagger-hilt - dependency injection
    
 * Created below packages for proper folder structure
    
    a. activities
    
    b. di
    
    c. utils

