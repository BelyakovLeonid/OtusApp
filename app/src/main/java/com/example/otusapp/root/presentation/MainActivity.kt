package com.example.otusapp.root.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.otusapp.R
import com.example.otusapp.base.presentation.appComponent
import com.example.otusapp.base.presentation.viewModel
import com.example.otusapp.root.di.DaggerRootComponent
import com.example.otusapp.root.di.RootComponent
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.a_main) {

    private lateinit var injector: RootComponent

    @Inject
    lateinit var cicerone: Cicerone<Router>

    private val navigator = AppNavigator(this, R.id.mainContainer)

    private val viewModel by viewModel { injector.viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector = DaggerRootComponent.factory().create(appComponent)
        injector.inject(this)

        if (savedInstanceState == null) {
            viewModel.submitEvent(MainContract.Event.OnScreenOpenEvent)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }
}