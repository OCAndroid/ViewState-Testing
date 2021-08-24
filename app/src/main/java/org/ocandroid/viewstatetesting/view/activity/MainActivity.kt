package org.ocandroid.viewstatetesting.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.ocandroid.viewstatetesting.R
import org.ocandroid.viewstatetesting.view.fragment.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment::class.java, null, ListFragment::class.simpleName)
            .commitAllowingStateLoss()
    }
}