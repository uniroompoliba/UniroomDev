package com.example.uniroomf.utilityClasses.ourAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.uniroomf.utilityClasses.PrenInfo

// Da fare adapter inserendo il row layout dello stud
class PrenAdapterStud constructor(var t : Context, p : ArrayList<PrenInfo>): BaseAdapter()
{
    // Elementi dell'adapter
    private var pren : ArrayList<PrenInfo> = p
    private var context : Context? = t

    // Elemento di mezzo fra la lista e gli elementi della lista stessa - Funzione base dello storico prenotazioni
    override fun getCount(): Int {
        return pren!!.size
    }

    override fun getItem(p0: Int): Any {
        return pren!!.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return pren!!.get(p0).hashCode().toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}