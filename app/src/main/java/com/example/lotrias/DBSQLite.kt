package com.example.lotrias

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DBSQLite(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "lottery.db"
        private const val DATABASE_VERSION = 1

        // Nome da tabela
        const val TABLE_NAME = "loteria_nums"

        // Colunas
        const val COLUMN_ID = "id"
        const val COLUMN_N1 = "n1"
        const val COLUMN_N2 = "n2"
        const val COLUMN_N3 = "n3"
        const val COLUMN_N4 = "n4"
        const val COLUMN_N5 = "n5"
        const val COLUMN_N6 = "n6"
    }

    // Cria a tabela quando o banco de dados é criado
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_N1 INTEGER,
                $COLUMN_N2 INTEGER,
                $COLUMN_N3 INTEGER,
                $COLUMN_N4 INTEGER,
                $COLUMN_N5 INTEGER,
                $COLUMN_N6 INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    // Atualiza o banco de dados (não é necessário neste caso)
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}

fun insertLotteryNumbers(db: SQLiteDatabase, n1: Int, n2: Int, n3: Int, n4: Int, n5: Int, n6: Int) {
    // Cria um objeto ContentValues para armazenar os valores das colunas
    val values = ContentValues().apply {
        put(DBSQLite.COLUMN_N1, n1)
        put(DBSQLite.COLUMN_N2, n2)
        put(DBSQLite.COLUMN_N3, n3)
        put(DBSQLite.COLUMN_N4, n4)
        put(DBSQLite.COLUMN_N5, n5)
        put(DBSQLite.COLUMN_N6, n6)
    }

    // Insere os valores na tabela loteria_nums
    db.insert(DBSQLite.TABLE_NAME, null, values)
}

// Função para realizar a consulta na tabela `loteria_nums`
fun selectLotteryNumbers(db: SQLiteDatabase): List<String> {
    val lotteryNumbersList = mutableListOf<String>()

    // Consulta SQL para selecionar todas as linhas da tabela `loteria_nums`
    val query = "SELECT * FROM ${DBSQLite.TABLE_NAME}"
    val cursor: Cursor = db.rawQuery(query, null)

    // Itera sobre os resultados da consulta
    if (cursor.moveToFirst()) {
        do {
            // Obtém os valores das colunas
            val n1 = cursor.getInt(cursor.getColumnIndexOrThrow(DBSQLite.COLUMN_N1))
            val n2 = cursor.getInt(cursor.getColumnIndexOrThrow(DBSQLite.COLUMN_N2))
            val n3 = cursor.getInt(cursor.getColumnIndexOrThrow(DBSQLite.COLUMN_N3))
            val n4 = cursor.getInt(cursor.getColumnIndexOrThrow(DBSQLite.COLUMN_N4))
            val n5 = cursor.getInt(cursor.getColumnIndexOrThrow(DBSQLite.COLUMN_N5))
            val n6 = cursor.getInt(cursor.getColumnIndexOrThrow(DBSQLite.COLUMN_N6))

            // Formata a linha como uma string
            val line = "Números: $n1, $n2, $n3, $n4, $n5, $n6"
            lotteryNumbersList.add(line)
        } while (cursor.moveToNext())
    }

    // Feche o cursor para liberar recursos
    cursor.close()

    return lotteryNumbersList
}
