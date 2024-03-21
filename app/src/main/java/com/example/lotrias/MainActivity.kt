package com.example.lotrias

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isAnimating = false
    private var randomNumber: Int = 0
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // armazena em variaveis a referencia do textView
        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.textView5)
        val textView6 = findViewById<TextView>(R.id.textView6)

        // armazena em uma variavei a referencia do botão
        val buttonGenerate = findViewById<Button>(R.id.button)

        // Seta um método que espera uma interação de click com o botão
        buttonGenerate.setOnClickListener {
            // Gera um número aleatório para cada variavel
            if (!isAnimating) {
                startAnimation(textView1, textView2, textView3, textView4, textView5, textView6)
            }
        }
    }

    private fun startAnimation(textView1: TextView, textView2: TextView, textView3: TextView, textView4: TextView, textView5: TextView, textView6: TextView) {
        isAnimating = true
        handler.postDelayed(object : Runnable {
            override fun run() {
                // Gera um número aleatório para cada variável
                randomNumber = (1..99).random()
                val randomNumber1 = (1..99).random()
                val randomNumber2 = (1..99).random()
                val randomNumber3 = (1..99).random()
                val randomNumber4 = (1..99).random()
                val randomNumber5 = (1..99).random()
                val randomNumber6 = (1..99).random()
                // Insere o número nas variáveis
                textView1.text = "$randomNumber1"
                textView2.text = "$randomNumber2"
                textView3.text = "$randomNumber3"
                textView4.text = "$randomNumber4"
                textView5.text = "$randomNumber5"
                textView6.text = "$randomNumber6"

                if (isAnimating) {
                    handler.postDelayed(this, 50) // Altere o valor para ajustar a velocidade da animação
                }

                // Condição de parada da animação (pode ser qualquer condição desejada)
                if (randomNumber == 50) {
                    stopAnimation()
                }
            }
        }, 0)
    }

    private fun stopAnimation() {
        isAnimating = false
        handler.removeCallbacksAndMessages(null)
    }
}