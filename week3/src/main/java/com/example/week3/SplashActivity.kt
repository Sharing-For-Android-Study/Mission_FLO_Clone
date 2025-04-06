package com.example.week3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // handler 객체를 생성하여, 메인 스레드의 Looper를 가져옴.
        // 메인 스레드에서 실행되어야 하는 작업 예약을 위해 사용.
        val handler = Handler(Looper.getMainLooper())
        // handler.postDelayed({람다}, 밀리세컨드)
        // 지정한 밀리세컨드 후에 람다 블록에서의 작업 수행
        // 2초 후에 MainActivity로 넘어가는 작업을 수행
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}