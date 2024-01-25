    package com.example.pdd_new_project_my

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import androidx.navigation.NavController
    import androidx.navigation.fragment.NavHostFragment
    import androidx.navigation.ui.AppBarConfiguration
    import androidx.navigation.ui.setupWithNavController
    import com.example.core_utils.LocalLangManager
    import com.example.core_utils.SharedPref
    import com.example.core_utils.SoundManager
    import com.example.pdd_new.R
    import com.example.pdd_new.databinding.ActivityMainBinding
    import com.google.android.material.bottomnavigation.BottomNavigationView
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        private lateinit var sharedPref: SharedPref
        private lateinit var soundManager: SoundManager
        private lateinit var navController: NavController

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            sharedPref = SharedPref(this)

            val currentLanguage = sharedPref.selectedLanguage
            currentLanguage?.let { LocalLangManager.setLocale(this, it) }

            val isSoundEnabled = sharedPref.isSoundEnabled
            soundManager = SoundManager.getInstance(this)

            if (isSoundEnabled) {
                soundManager.enableSound()
            } else {
                soundManager.disableSound()
            }

            val navView: BottomNavigationView = binding.navView

            val host =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
            navController = host.navController

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    com.example.presentation.R.id.nav_register, R.id.navigation_home, R.id.navigation_rank,
                    R.id.navigation_video, R.id.profile_nav_graph
                )
            )
            navView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == com.example.presentation.R.id.registerFragment ||
                    destination.id == com.example.presentation.R.id.onboardingFragment ||
                    destination.id == com.example.presentation.R.id.splashScreenFragment ||
                    destination.id == com.example.profile_presenter.R.id.cameraFragment
                ) {
                    navView.visibility = View.GONE
                } else {
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }