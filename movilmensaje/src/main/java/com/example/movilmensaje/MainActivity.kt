package com.example.movilmensaje

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.wearable.Wearable

class MainActivity : AppCompatActivity() {
    lateinit var ctMensaje: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        ctMensaje = findViewById(R.id.ctMensaje)
        val btnEnviar: Button = findViewById(R.id.btnEnviar)

        btnEnviar.setOnClickListener{
            val mensaje = ctMensaje.text.toString()
            if (mensaje.isNotEmpty()){
                enviarMensajeAlReloj(mensaje)
            }
            else{
                Toast.makeText(this, "Escribe un mensaje a enviar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarMensajeAlReloj(mensaje: String){
        Wearable.getNodeClient(this).connectedNodes.addOnSuccessListener { nodes ->
           for (node in nodes){
               Wearable.getMessageClient(this).sendMessage(
                   node.id,
                   "/mensaje_path",
                   mensaje.toByteArray()
               ).addOnSuccessListener {
                   Toast.makeText(this, "Enviado con exito", Toast.LENGTH_SHORT).show()
               }.addOnFailureListener {
                   Toast.makeText(this, "Error al enviar", Toast.LENGTH_SHORT).show()
               }
           }
        }
    }
}