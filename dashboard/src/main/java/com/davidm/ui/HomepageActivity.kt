package com.davidm.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.davidm.payees.ui.PayeesFragment
import com.google.android.material.bottomappbar.BottomAppBar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_homepage.*
import javax.inject.Inject


class HomepageActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var dashboardFragment: DashboardFragment
    lateinit var payeesFragment: PayeesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_homepage)
        setSupportActionBar(bottomAppBar)


//        val bottomDialogFragment =
//            PayeeCreationFragment()
//
//        fab.setOnClickListener {
//            if (view_pager.currentItem == 1) {
//                bottomDialogFragment.show(
//                    supportFragmentManager,
//                    "new_payee_dialog_fragment"
//                )
//            }
//
//        }

        dashboardFragment = DashboardFragment()
        payeesFragment = PayeesFragment()

        // initial position
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view_tag, dashboardFragment).commit()

        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        bottomAppBar.replaceMenu(R.menu.bottom_nav_menu)

//
//        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//        bottomAppBar.replaceMenu(R.menu.bottom_second_menu)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.navigation_payees -> {

                supportFragmentManager.beginTransaction().add(R.id.fragment_container_view_tag, payeesFragment).addToBackStack("home").commit()

                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                bottomAppBar.replaceMenu(R.menu.bottom_second_menu)

            }
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view_tag, dashboardFragment).commit()
                bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                bottomAppBar.replaceMenu(R.menu.bottom_nav_menu)
            }

        }

        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && supportFragmentManager.findFragmentByTag("home") !== null ) {
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            bottomAppBar.replaceMenu(R.menu.bottom_nav_menu)
            onBackPressed()


            false
        } else super.onKeyDown(keyCode, event)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
