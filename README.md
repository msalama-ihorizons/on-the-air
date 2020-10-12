# The Air Exercise 

The Air Exercise prototype application to list all on the air tv shows with its title, genres, rate and release dates. 
and also user could check more details about the show like show cast and also the similar tv shows. User can add tv shows to his favourites list and rate the show as well.

# Features 

•	User will able to see all on the air tv shows.

•	User will be able see more details about the show.

•	User will be able to add shows to his favourites list. 

•	User will be able to rate tv shows. 


# Technical Features

•	Model-View-ViewModel architecture.

•	Kotlin Programming language 

•	Kotlin Coroutines to handle all asynchronous tasks   

•	Android Jetpack

    o LiveData to notify view with any data changes
  
    o Lifecycle handle lifecycle state changes 
  
    o ViewModel allows data to survive configuration changes like screen rotations 
  
    o Room to save data locally on SQLite so user can use the app offline 
  
    o Hilt to handle dependency injection 
  
•	Retrofit for https network calls

•	Junit4, Hamcrest, AndroidX Test, and AndroidX architecture components core for building unit tests

# Prototype Packages 

•	api: it contains movies DB apis and its responses models

•	model: contains all model classes.

•	repository: contains repositories classes to fetch data and handle all business logic.

•	db: for local database creation and its operations like insert and select

•	di: to provide app third party libraries dependencies like Room and Retrofit

•	ui: contains view classes along with their ViewModel.

•	test: contains all unit tests classes

# Libraries

•	Retrofit 

•	GSON

•	Hilt

•	Room

•	Junit4

•	Robolectric

•	Mockito

# How it works 

Once app is open for the first time, on the air tv shows list will be loaded from Movies DB APIs.

When user click on tv show, app will open details activity for displaying more details about the selected show and also to display show's cast, and similar tv shows. 
User could click on rating button to rate the show.

# Screen Shots
![Alt text](/app/shots/1.jpg?raw=true "")
![Alt text](/app/shots/2.jpg?raw=true "")
![Alt text](/app/shots/3.jpg?raw=true "")
![Alt text](/app/shots/4.jpg?raw=true "")
![Alt text](/app/shots/5.jpg?raw=true "")





