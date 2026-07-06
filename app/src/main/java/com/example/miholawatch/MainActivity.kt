package com.example.miholawatch

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable

class MainActivity : AppCompatActivity(), MessageClient.OnMessageReceivedListener {
    lateinit var ctTexto: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        ctTexto=findViewById(R.id.ctMensaje)
    }

    override fun onResume(){
        super.onResume()
        Wearable.getMessageClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()
        Wearable.getMessageClient(this).removeListener(this)
    }

    override fun onMessageReceived(nodoMensaje: MessageEvent) {
        if (nodoMensaje.path == "/mensaje_path") {
            val mensajeRecibido = String(nodoMensaje.data)
            runOnUiThread {
                ctTexto.text = mensajeRecibido
            }
        }
    }
}