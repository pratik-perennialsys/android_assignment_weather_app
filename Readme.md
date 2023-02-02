Title: Weather app

**Description**: A simple lightweight weather app that displays below fields
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

**Project details**

    a. Android studio: Android Studio Dolphin | 2021.3.1

    b. gradle version: 7.4

    c. gradle plugin version: 7.3.0
    
    d. MVVM architecture
    
    e. Supports Android version L (version code 21) and above

    
**Security**

    a. Using Encrypted Shared Preferences to store logged in user session
    
    b. Using SQLCipher to encrypt ROOM database 
    
**Note:** 
       
       Need to update SQL_CIPHER_SECRET_KEY at gradle.properties->SQL_CIPHER_SECRET_KEY
       
       Need to update OPEN_WEATHER_API_KEY at gradle.properties->OPEN_WEATHER_API_KEY
       

**Project structure** 
- Under main folder we have java->com->perennial->androidassignmentweatherapp
- Under application folder we have WeatherApplication class
- Under data folder we have models, repositories, rest, room db, viewmodels
- Under di folder we have dependency injection for ViewModels, DatabaseModule, SharedPreferencesModule, NetworkModule
- Under ui folder we have activities, adapters and fragments
- Under utils folder we have all common util classes

**Unit Test**

We have covered unit test cases for Viewmodel and Util classes and instrument test cases for database. Test coverage is above 90%.


<img width="633" alt="Screenshot 2023-02-02 at 9 58 37 AM" src="https://user-images.githubusercontent.com/123717712/216235111-374b6181-8333-49b4-bbc4-f913c42490d6.png">

       
**App screens**


![splash](https://user-images.githubusercontent.com/123717712/216235237-d7d7bac4-070f-4b5f-891f-9174487abf11.png)


![Login](https://user-images.githubusercontent.com/123717712/216235288-799b3b14-11e1-42f3-bf32-130fc9ca46ad.png)

![signup](https://user-images.githubusercontent.com/123717712/216235330-d164e1d4-39fe-433f-95d3-59f723aef32b.png)

![Weather details](https://user-images.githubusercontent.com/123717712/216235488-bb55d0cf-1887-404f-83da-1a98dbbe4df2.png)

![previous weathers](https://user-images.githubusercontent.com/123717712/216235515-25377f7f-e254-4e8b-bfd8-a656cfd516ce.png)


**Note**
As I'm the only contributor, I've put all code in master branch.
 

