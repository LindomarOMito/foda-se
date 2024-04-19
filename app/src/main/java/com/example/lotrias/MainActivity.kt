package com.example.lotrias

import android.app.AlertDialog
import android.media.MediaPlayer
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
    private var mediaPlayer = MediaPlayer();
    private var dbHelper: DBSQLite? = null;

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

        // Cria a variavel que armazena a música
        mediaPlayer = MediaPlayer.create(this, R.raw.piao)

        // armazena em variaveis as referencias dos botões
        val buttonGenerate = findViewById<Button>(R.id.button)
        val alert = findViewById<Button>(R.id.alert)

        alert.setOnClickListener {
            // Crie uma instância de LotteryDatabaseHelper para gerenciar o banco de dados
            val dbHelper = DBSQLite(this)

            // Obtenha uma referência ao banco de dados para leitura
            val db = dbHelper.readableDatabase

            // Realize a consulta para obter os números da tabela `loteria_nums`
            val lotteryNumbersList = selectLotteryNumbers(db)

            // Feche o banco de dados após a consulta
            db.close()

            // Crie uma string para exibir os números
            val numbersString = lotteryNumbersList.joinToString("\n")

            // Crie um alert dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Números da Loteria")
            builder.setMessage(numbersString)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            // Exiba o alert dialog
            builder.create().show()
        }

        // Seta um método que espera uma interação de click com o botão
        buttonGenerate.setOnClickListener {
            mediaPlayer.start()
            dbHelper = DBSQLite(this)

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
                    // Para a música
                    if (mediaPlayer.isPlaying) mediaPlayer.pause();
                    // Para a animação
                    stopAnimation()
                    // Salva no Banco de dados
                    val list: List<Int> = listOf(randomNumber1, randomNumber2, randomNumber3, randomNumber4, randomNumber5, randomNumber6)
                    var db = dbHelper?.writableDatabase
                    if (db != null) {
                        insertLotteryNumbers(db, randomNumber1, randomNumber2, randomNumber3, randomNumber4, randomNumber5, randomNumber6)
                    };
                }
            }
        }, 0)
    }

    private fun stopAnimation() {
        isAnimating = false
        handler.removeCallbacksAndMessages(null)
    }
}