package com.program.uasdpm

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EntriDataDosen : AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_entry)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("nama") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("golongan_pangkat")
                && intent.hasExtra("Pendidikan") && intent.hasExtra("Keahlian")
                && intent.hasExtra("Program_studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNmDosen = findViewById<EditText>(R.id.etNamaDosen)
        val spnJbt = findViewById<Spinner>(R.id.spnJabatan)
        val spnGol = findViewById<Spinner>(R.id.spnGolongan)
        val rdS2 = findViewById<RadioButton>(R.id.rds2)
        val rdS3 = findViewById<RadioButton>(R.id.rds3)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etBK = findViewById<EditText>(R.id.etBK)
        val etProgstd = findViewById<EditText>(R.id.etProgstd)
        val jbt = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor",
                             "Lektor Kepala", "Guru Besar")
        val gol = arrayOf("III/a - Penata Muda", "III/b Penata Muda Tingkat 1",
                             "III/c - Penata", "III/d - Penata Tingkat 1",
                             "IV/a - Pembina", "IV/b - Pembina - Tingkat 1",
                             "IV/c - Pembina Utama Muda", "IV/d - Pembina Utama Madya",
                             "IV/e - Pembina Utama")

        val adpJbt = ArrayAdapter(
            this@EntriDataDosen,
            android.R.layout.simple_spinner_dropdown_item, jbt
        )
        val adpGol = ArrayAdapter(
            this@EntriDataDosen,
            android.R.layout.simple_spinner_dropdown_item, gol
        )
        spnJbt.adapter = adpJbt
        spnGol.adapter = adpGol

        if(modeEdit) {
            val nidn = intent.getStringExtra("NIDN")
            val namaDosen = intent.getStringExtra("nama_dosen")
            val jabatan = intent.getStringExtra("Jabatan")
            val golongan = intent.getStringExtra("golongan_pangkat")
            val pendidikan = intent.getStringExtra("Pendidikan")
            val bidangKeahlian = intent.getStringExtra("Keahlian")
            val programStudi = intent.getStringExtra("Program_studi")

            etNIDN.setText(nidn)
            etNmDosen.setText(namaDosen)
            spnJbt.setSelection(jbt.indexOf(jabatan))
            spnGol.setSelection(gol.indexOf(golongan))
            etBK.setText(bidangKeahlian)
            etProgstd.setText(programStudi)
            if(pendidikan == "S2") rdS2.isChecked = true
            else rdS3.isChecked = true
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener{
            if("${etNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                "${spnJbt.selectedItem}".isNotEmpty() && "${spnGol.selectedItem}".isNotEmpty() &&
                "${etBK.text}".isNotEmpty() && "${etProgstd.text}".isNotEmpty() &&
                 (rdS2.isChecked || rdS3.isChecked)) {
                val db = DBHelper(this@EntriDataDosen)
                db.nidn = "${etNIDN.text}"
                db.namaDosen = "${etNmDosen.text}"
                db.jabatan = spnJbt.selectedItem as String
                db.golongan = spnGol.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.keahlian = "${etBK.text}"
                db.progstd = "${etProgstd.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")){
                    Toast.makeText(
                        this@EntriDataDosen,
                        "Data Dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntriDataDosen,
                        "Data Dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@EntriDataDosen,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}