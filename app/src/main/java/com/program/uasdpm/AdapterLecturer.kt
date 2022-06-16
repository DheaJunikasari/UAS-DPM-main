package com.program.uasdpm

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class AdapterLecturer(
    private val getContext: Context,
    private val customListItem: ArrayList<Lecturer>
) : ArrayAdapter<Lecturer>(getContext, 0, customListItem){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder){
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvNIDN = listLayout.findViewById(R.id.tvNIDN)
                tvProgstd = listLayout.findViewById(R.id.tvProgstd)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.namaDosen)
        holder.tvNIDN!!.setText(listItem.nidn)
        holder.tvProgstd!!.setText(listItem.progstd)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("NIDN", listItem.nidn)
            i.putExtra("nama", listItem.namaDosen)
            i.putExtra("Jabatan", listItem.jabatan)
            i.putExtra("golongan_pangkat", listItem.golongan)
            i.putExtra("Pendidikan", listItem.pendidikan)
            i.putExtra("Keahlian", listItem.keahlian)
            i.putExtra("Program_studi", listItem.progstd)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener{
            val db = DBHelper(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvNIDN!!.text
            val nama = holder.tvNmDosen!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda yakin akan menghapus data ini?
                    
                    $nama 
                    [$nidn]
                """.trimIndent())
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data Dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmDosen: TextView? = null
        internal var tvNIDN: TextView? = null
        internal var tvProgstd: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}