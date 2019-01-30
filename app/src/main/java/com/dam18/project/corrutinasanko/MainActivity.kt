package com.dam18.project.corrutinasanko

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import org.jetbrains.anko.UI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    var jobCuentaAtras: Job? = null
    val logtag = "EJEMPLO-->"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCuentaAtras.setOnClickListener{
            // Cuando le damos al boton empezamos la corutina
            cuentaAtras()
        }


        btnCancelar.setOnClickListener{
            // como la variable puede ser null, para no probocar null pointer object
            // kotlin me obliga a comprobar que no es un null
            // asi mi aplicacion no rompe
            if (jobCuentaAtras != null) cancelar(jobCuentaAtras)
            else Log.d(logtag, "jobCuentaAtras es NULL")
        }
    }

    fun cuentaAtras(){
        //Al poner UI la corutina se ejecuta en el hilo principal
        //y no necesito poner uiThread
        val jobCuentaAtras = doAsync() {
            uiThread {
                for (i in 50 downTo 1) {
                    // actualiza TextView
                    txtMiTexto.text = "$i"
                    // paramos la corutina 0,5sg
                    // ATENCIÓN: No para el hilo principal
                    //El delay da error pero la corutina funciona
                    //delay(500)
                }
                txtMiTexto.text = "Boom!"
            }
        }
    }

    //NO probe el cancelar
    fun cancelar(job: Job?) {
        job?.cancel()
    }

}
