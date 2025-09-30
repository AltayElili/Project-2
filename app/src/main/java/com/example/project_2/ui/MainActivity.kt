package com.example.project_2.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.project_2.databinding.ActivityMainBinding
import com.example.project_2.R



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> findNavController(R.id.fragmentContainerView).navigate(R.id.menuHome)
                R.id.menuFavories -> findNavController(R.id.fragmentContainerView).navigate(R.id.menuFavories)
                R.id.menuProfile -> findNavController(R.id.fragmentContainerView).navigate(R.id.menuProfile)
                else -> false
            }
            true
        }
    }
}
