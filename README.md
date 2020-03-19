
<img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/cover_image.png" width="900"/>

# Sweet Bank 🍬
Sweet Bank is an Android app in **Kotlin** that I developed to play around with some principles of the mobile development such as scope separation, design patterns ( such as MVVM ) and much more.

I used Starling Bank APIs to fetch and send data to the Sandbox: this really helped me to focus on what really matters. Their APIs are very well documented and there is a lot to explore in their developer dashboard. [Try it](https://developer.starlingbank.com/docs)!

Watch out, if you want to run the project, you will find out that the `NetworkUtils.kt` file is missing: it would contain the `BASE_URL` and the token, which is necessary to do all the APIs calls. 


## Version [1.1.0](https://github.com/davidmarinangeli/Sweet-Bank/releases/tag/v1.1.0) released

## Software Architecture Pattern 🏛
<img src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/AppStructure.png" width="700"/>

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

I decided to split the project in more modules because of 1. shorter Build Time 2. Separation of the Scopes 3. Cleaner code and better architecture

## UI 🎨
I made a quick low-fidelity prototype of the app's menus with all the things I wanted to show. You can see the Dashboard Fragment and the Payees one from the pictures down below. Shoutout to my friend Claudio that gave me some help to improve the design!!

I decided to use a `BottomAppBar` as a navigation component. It can be used also to host future features.

<img style="float: center;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/lf_dashboard.png" height="300"/><img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/old_palette_dashboard.png" height="300"/><img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/new_palette_dashboard.png" height="300"/><img style="float: left;" src="https://user-images.githubusercontent.com/19487461/77102839-c4ff1700-6a19-11ea-8639-cef3b8df9b7e.gif" height="300"/>


<img style="float: center;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/lf_payees.png" height="300"/><img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/old_palette_payees.png" height="300"/><img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/new_palette_payees.png" height="300"/>

The Payee creation has been developed with the `Bottom Sheet Dialog` official library and using the standard `TextInputLayout` of Google Material.

<img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/payee_creation.png" height="300"/>

From the Payee List the user can navigate to the Payee detail that shows its general data, the accounts and so on. The view is composed of a  `CollapsingToolbarLayout` , two `RecyclerViews` and sections' labels, all wrapped in a `NestedScrollView`.

<img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/lf_payee_profile.png" height="300"/><img style="float: left;" src="https://raw.githubusercontent.com/davidmarinangeli/Sweet-Bank/master/screenshots%20and%20cover%20images/payee_profile.png" height="300"/>

## Libraries
I used some libraries such as:
 1. Android Jetpack ( ViewModels, LiveData )
 2. ViewPager2
 3. Dagger
 4. Retrofit
 5. Kotlin Coroutines
 6. Moshi
 7. Google Android Material
 8. JUnit4 / Coroutine-test
 9. Mockk

## Next updates 

🟢 Payee List - ADDED!

🟢 Unit Testing - Payee Local mapper

🟢 New Color palette

🟢 Fixes on font size and text alignment on both Dashboard and Payee List

🟢 Payee creation

🟢 Better validation on Payee Creation

🟢 Payee profile

🟡 Support for accounts with multiple currencies, balances and transactions

🟡 Unit Testing - Dashboard date and currency converter

🟡 Unit Testing - Dashboard ViewModel

🟡 UI Testing

🔴 Transaction creation

🔴 Paged List on transactions ( Dashboard )

🔴 Better handling of network errors / empty lists

