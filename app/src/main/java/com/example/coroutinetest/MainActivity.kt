package com.example.coroutinetest

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        CoroutineScope(Dispatchers.IO+ SupervisorJob()).launch {
            supervisorScope {
                val handler = CoroutineExceptionHandler { _, ex ->
                    println("Exception handler caught: $ex")
                }
                launch { getLikes() }.join()

                launch(handler) {
                    try {
                        getComment()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                    throw RuntimeException("Task failed")
                }.join()

                launch { getSuperLike() }.join()

            }
        }
    }


    private suspend fun getLikes():Int {
        delay(3000)
        Log.e("fist exit","asdasd   1")
        return 10


    }
    private suspend fun getComment():Int
    {
        delay(1500)
        Log.e("second exit","asdasd 2")
        throw IllegalStateException("Error in coroutine 2")
        return 15
    }96

    private suspend fun getSuperLike():Int
    {
        delay(2000)
        Log.e("fist third","asdasd, 3")
        return 67
    }

}