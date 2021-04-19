# Random City App

## Running the project
The *Google Maps* API key is placed in `secrets.properties`. The file is encrypted with git crypt to ensure it can be stored securely in public repository. 
Decrypting the file requires *git-crypt* present and can be decrypted by running the following command: 
```
git crypt unlock <path to the key>
```
Alternatively the file can be replaced to provide different key. The contents of the file should be following:
```
MAPS_API_KEY=<key>
```

The project was created with *Android Studio* v. 4.1.3

## Java class
Project features one *Java* class as per requirements: [CitiesFragment](app/src/main/java/io/github/staakk/randomcity/ui/city/CitiesFragment.java)

## Architecture
The project is using simple *MVVM* implementation. Each `Fragment` represents relevant part of the screen (master-detail layout parts) or a whole screen (splash screen). The fragments are observing `ViewModel`s which in turn are communicating with the data layer of the app. This relationship could be further decoupled by using use cases between the `ViewModel`s and data layer.

## Concurrency
For structured concurrency *RxJava2* was used.

## Persistence
For persistence layer *Room* was used.

## Geocoding and maps
For geocoding and displaying map *GoogleMaps* was used. The API key is unrestricted and should work with app signed with any key.

## Api key
The *GoogleMaps* API key is stored in [secrets.properties](secrets.properties). It was put in the repository as per requirements. Ideally the key should be provided for the build via secure keystore on the CI or should be stored locally for development and never committed. For the purpose of this task the key was encrypted using *git-crypt*.

## Testing
For mocking in tests, since most of the classes are written in Kotlin, *mockk* was used.
For testing interactions with external libraries (e.g. `android.location.Geocoder`) *Robolectric* was used.
Integration tests for *Room* are written as android tests as per *Room* guidelines recommendations.


