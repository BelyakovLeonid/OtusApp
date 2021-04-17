package com.github.belyakovleonid.feature_main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.belyakovleonid.core.presentation.getDependencies
import com.github.belyakovleonid.core.presentation.viewModel
import com.github.belyakovleonid.feature_main.R
import com.github.belyakovleonid.feature_main.di.MainApiProvider
import com.github.belyakovleonid.feature_main.di.MainComponentHolder
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.a_main) {

    private lateinit var injector: MainApiProvider

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.mainContainer)

    private val viewModel by viewModel { injector.viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = MainComponentHolder.getInstance(getDependencies())
        injector.inject(this)

        if (savedInstanceState == null) {
            viewModel.submitEvent(MainContract.Event.OnScreenOpenEvent)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            MainComponentHolder.release()
        }
    }
}