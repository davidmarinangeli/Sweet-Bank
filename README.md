
<img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/cover_image.png" width="900"/>

# Sweet Bank 🍬
Sweet Bank is an Android app in **Kotlin** that I developed to play around with some principles of the mobile development such as scope separation, design patterns ( such as MVVM ) and much more.

I used Starling Bank APIs to fetch and send data to the Sandbox: this really helped me to focus on what really matters. Their APIs are very well documented and there is a lot to explore in their developer dashboard. [Try it](https://developer.starlingbank.com/docs)!

Watch out, if you want to run the project, you will find out that the `NetworkUtils.kt` file is missing: it would contain the `BASE_URL` and the token, which is necessary to do all the APIs calls. 


## Version [2.0.0](https://github.com/davidmarinangeli/Sweet-Bank/releases/tag/2.0.0) released

I decided to change the UI ( yeah, again ) and I got big help from my brother with all the new concepts and elements of the home screen. **Motion Layout** is now even more present here and the blobs move up along with the sheet.

The "Payee" part has been hidden ( temporary ) and the "profile picture" upload has been added instead ( with a picture rotation fix function ).

I changed the implementation of the styles by adding ☀️🌑 **Dark Theme** , using the `Theme.MaterialComponents.DayNight.NoActionBar`, and the typography system, using the `TextAppearance.MdcTypographyStyles`.

I started using `Koin` instead of Dagger2, just because I wanted to try that: it's nice, for a project this small!

<img src="https://github.com/davidmarinangeli/Sweet-Bank/blob/master/screenshots%20and%20cover%20images/dashboard_gif.gif" width=300 />

## Software Architecture Pattern 🏛
<img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/AppStructure.png" width="700"/>

I used the MVVM pattern to make the code more readable, scalable and easily testable. To do this I divided the project into **view slices** and three layers:

1.  UI
2.  Repository - that could handle both local (SharedPrefs etc…) and network requests
3.  Network - that fetches the data from StarlingBank APIs

As you can see from the above picture ( you can click it if you want to zoom the structure ) the app is divided into several modules that communicate altogether.

## Modules
As for now, there are some modules that fulfill a specific view or purpose:
1. *app* - the main one.
2. *network* - handles the network global setup and, thanks to Dagger, provides the dependencies that are used in the whole project.
3. *dashboard* - handles the whole stack for the **Dashboard Fragment** and part of the Main Activity. You can find there its entities, the APIs, the LocalMapper, the UI and so on.
6. *payees* - the module that handles the whole stack for the **Payees Fragment** in the home: in the future will handle also the Payee creation.
7. *resources* - take care and share all the common resources ( fonts, drawables, colors and so on )

I decided to split the project in more modules because of 1. shorter Build Time 2. Separation of the Scopes 3. Cleaner code and better architecture

## UI 🎨
I made a quick low-fidelity prototype of the app's menus with all the things I wanted to show. You can see the Dashboard Fragment and the Payees one from the pictures down below. Shoutout to my friend Claudio that gave me some help to improve the design!!

<img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/sweetbank_new_ui_1.png" width="200"/><img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/sweetbank_new_ui_2.png" width="200"/><img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/sweetbank_new_ui_3.png" width="200"/>

From the Payee List the user can navigate to the Payee detail that shows its general data, the accounts and so on. The view is composed of a  `CollapsingToolbarLayout` , two `RecyclerViews` and other views, all wrapped in a `NestedScrollView`.

## Libraries
I used some libraries such as:
 1. Android Jetpack ( ViewModels, LiveData )
 2. ViewPager2
 3. Dagger2
 4. Retrofit
 5. Kotlin Coroutines
 6. Moshi
 7. Google Android Material
 8. JUnit4 / Coroutine-test
 9. Mockk
 10. Shimmer Animation for Android ( Facebook )
 11. MotionLayout 
 12. Koin

