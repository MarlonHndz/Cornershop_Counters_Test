# Cornershop_Counters_Test
Android app for counting things.

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

### View retrieval strategy 
I'm using **Android DataBinding** and **View Binding**

## Architecture
- Using **Clean Architechture** for the whole application and using **MVVM** for the presentation layer.
