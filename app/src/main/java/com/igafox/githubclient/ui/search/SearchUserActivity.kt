package com.igafox.githubclient.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.igafox.githubclient.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchUserFragment.newInstance())
                .commitNow()
        }
    }
}