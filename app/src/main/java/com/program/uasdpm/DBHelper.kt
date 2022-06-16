package com.program.uasdpm

import android.content.*
import android.database.Cursor
import android.database.sqlite.*

class DBHelper (context: Context): SQLiteOpenHelper(context, "campuss", null, 1) {
    var nidn = ""
    var namaDosen = ""
    var jabatan = ""
    var golongan = ""
    var pendidikan = ""
    var keahlian = ""
    var progstd = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            nidn char(10) primary key,
            nama_dosen varchar(50) not null,
            jabatan varchar(15) not null,
            golongan_pangkat varchar(30) not null,
            pendidikan char(2) not null,
            keahlian varchar(30) not null,
            program_studi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nidn", nidn)
            put("nama_dosen", namaDosen)
            put("jabatan", jabatan)
            put("golongan_pangkat", golongan)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("program_studi", progstd)
        }

        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }

    fun ubah (kode: String): Boolean {
        val db = writableDatabase
        val cv =  ContentValues()
        with(cv){
            put("nidn", nidn)
            put("nama_dosen", namaDosen)
            put("jabatan", jabatan)
            put("golongan_pangkat", golongan)
            put("pendidikan", pendidikan)
            put("keahlian", keahlian)
            put("program_studi", progstd)
        }
        val cmd = db.update(tabel, cv, "nidn = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }

    fun hapus(kode: String):Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "nidn = ?", arrayOf(kode))
        return  cmd != -1
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }
}