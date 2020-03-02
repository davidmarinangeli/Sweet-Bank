package com.davidm.sweetbankapp

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.davidm.network.BASE_URL
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.defaultAccount
import com.davidm.payees.network.PayeesApi
import com.davidm.payees.repository.PayeesRepository
import com.davidm.payees.ui.PayeesViewModel
import com.davidm.ui.HomepageActivity
import dagger.android.AndroidInjection
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject


class HomepageActivityTest {

    @get:Rule
    val mActivityTestRule: ActivityTestRule<HomepageActivity> = ActivityTestRule<HomepageActivity>(
        HomepageActivity::class.java
    )

    @Before
    fun setup() {

        AndroidInjection.inject(mActivityTestRule.activity)

        mActivityTestRule.activity
            .supportFragmentManager.beginTransaction()
    }


    @Test
    fun launchAndCreateAPayee() {

        onView(withId(R.id.navigation_payees)).perform(click())

       val x :PayeesApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(PayeesApi::class.java)

        PayeesViewModel(PayeesRepository(x)).getPayees()

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.createButton)).perform(click())

        onView(withId(R.id.payeeNameEditText)).perform(
            typeText("Antonio La Trippa's Account"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.createButton)).perform(click())
        onView(withId(R.id.businessNameEditText)).perform(
            typeText("BusinessName"),
            closeSoftKeyboard()
        )


    }
}
