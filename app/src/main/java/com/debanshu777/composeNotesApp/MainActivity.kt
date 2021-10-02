package com.debanshu777.composeNotesApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.debanshu777.composeNotesApp.ui.theme.ComposeNotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNotesAppTheme {
                
            }
        }
    }
}
