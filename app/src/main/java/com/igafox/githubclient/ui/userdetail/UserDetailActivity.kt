package com.igafox.githubclient.ui.userdetail

import android.content.Context
import android.content.Intent
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

        val userId = intent?.getStringExtra(PARAMS_USER_ID)
        userId ?: return

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserDetailFragment.newInstance(userId))
                .commitNow()
        }
    }

    companion object {

        private const val PARAMS_USER_ID = "user"

        fun createIntent(context: Context, userId:String): Intent {
            return Intent(context,UserDetailActivity::class.java).also { intent ->
                intent.putExtra(PARAMS_USER_ID, userId)
            }
        }

    }

}