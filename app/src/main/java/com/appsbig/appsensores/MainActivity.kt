package com.appsbig.appsensores

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.hardware.*
import android.media.MediaPlayer
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mensaje: TextView = findViewById(R.id.txtMensaje)
        val fondo: ConstraintLayout = findViewById(R.id.layout)

        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val sensor =sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        var sonido1: MediaPlayer= MediaPlayer.create(this, R.raw.s1)
        var sonido2: MediaPlayer= MediaPlayer.create(this, R.raw.s2)

        if(sensor==null){
            mensaje.text = "El dispositivo no cuenta con el sensor"
        }
        else{
            val evento = object : SensorEventListener{
                override fun onSensorChanged(sensorEvent: SensorEvent) {
                    val x = sensorEvent.values[0]
                    val y = sensorEvent.values[1]
                    val z =sensorEvent.values[2]

                    when{
                        x > 6 -> {
                            fondo.setBackgroundColor(Color.RED)
                            sonido1.start()
                        }
                        x < -6 -> {
                            fondo.setBackgroundColor(Color.BLUE)
                            sonido2.start()
                        }
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

                }
            }
            sm.registerListener(evento,sensor,SensorManager.SENSOR_DELAY_NORMAL)
        }

    }


}