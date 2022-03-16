# Cornershop_Counters_Test
Android app for counting things.

### **Note:** 
Remember to **update the IP** where your server is located or launched in order to connect to it. You can make that change in the [**URLs file**](https://github.com/MarlonHndz/Cornershop_Counters_Test/blob/master/remotedatasource/src/main/java/com/cornershop/remotedatasource/api/Urls.kt) by changing the value of **BASE_URL**. 

## Technologies

### Libraries
- **Retrofit**: Requests to the server
- **Gson**: Map responses from requests
- **Android ViewModels**: Store and manage UI-related data
- **Android LiveData**: UI updates from ViewModel to Activity/Fragment
- **Jetpack Navigation**: Navigation through the screens
- **Koin**: Dependency injection
- **Room**: Local database

### Background Strategy:
- **Kotlin Coroutines**

### Code Quiality
- **Code Style**: Added task using [ktlint](https://github.com/pinterest/ktlint) to keep a good code styel. The code style task is run always before the build task.

- **Unit Testing**: Created unit tests for the following modules using Mockito and Junit.
	- **data** module
	- **remotedatasource** module
	- **domain** module

### View retrieval strategy 
I'm using **Android DataBinding** and **View Binding**

### TODO
- **Instrumentation test**: Created instrumentation test for testing the Room database in **localdatasource** module.

## Architecture
- Using **Clean Architechture** for the whole application and using **MVVM** for the presentation layer.
![CleanArch](https://user-images.githubusercontent.com/16215046/158522837-2fcbe62d-f7be-4e5a-8f27-9a7f8450d42e.png)

## Evidence
- Here's a video using the app

https://user-images.githubusercontent.com/16215046/158523884-5945a8d0-562e-424d-9d25-b476734195b8.mp4

