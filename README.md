
<img src="https://raw.githubusercontent.com/davidmarinangeli/RedditPostsTask/master/Screenshot_1582199390.png" width="300"/><img src="https://raw.githubusercontent.com/davidmarinangeli/RedditPostsTask/master/Screenshot_1582199424.png" width="300"/>



# Sweet Bank
Sweet Bank is an Android app in **Kotlin** that I developed to play around with some principles of the mobile development such as scope separation, design patterns ( such as MVVM ) and much more.

I used Starling Bank APIs to fetch and send data to the Sandbox: this really helped me to focus on what really matters. Their APIs are very well documented and there is a lot to explore in their developer dashboard. [Try it](https://developer.starlingbank.com/docs)!

## Software Architecture Pattern

><img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/AppStructure.png" width="300"/>

I used the MVVM pattern to make the code more readable, scalable and easily testable. To do this I divided the project into **view slices** and three layers:

1.  UI
2.  Repository - that could handle both local (SharedPrefs etc…) and network requests
3.  Network - that fetches the data from StarlingBank APIs

As you can see from the above picture ( you can click it if you want to zoom the structure ) the app is divided into several modules that communicate altogether.

## Modules
As for now, there are seven modules that fulfill a specific view or purpose:
1. *app* - the main one.
2. *network* - handles the network global setup and, thanks to Dagger, provides the dependencies that are used in the whole project.
3. *dashboard* - handles the whole stack for the **Dashboard Fragment** and part of the Main Activity. You can find there its entities, the APIs, the LocalMapper, the UI and so on.
4. *detail* - WIP
5. *account* - here can be found all the necessary to retrieve the Starling Bank's Account objects, reused everywhere in the app
6. *payees* - the module that handles the whole stack for the **Payees Fragment** in the home: in the future will handle also the Payee creation.
7. *resources* - take care and share all the common resources ( fonts, drawables, colors and so on )

I decided to split the project in more modules because of 1. shorter Build Time 2. Separation of the Scopes 3. Cleaner code and better architecture.

## UI
WIP

## Libraries
I used some libraries such as:
 1. Android Jetpack ( ViewModels, LiveData )
 2. Dagger
 3. Retrofit
 4. Kotlin Coroutines
 5. Moshi
