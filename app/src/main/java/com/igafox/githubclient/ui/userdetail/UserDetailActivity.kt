package com.igafox.githubclient.ui.userdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.igafox.githubclient.R
import com.igafox.githubclient.ui.userdetail.UserDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserDetailFragment.newInstance())
                .commitNow()
        }
    }
}