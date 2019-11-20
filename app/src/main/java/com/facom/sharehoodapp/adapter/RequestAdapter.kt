package com.facom.sharehoodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.facom.sharehoodapp.R
import com.facom.sharehoodapp.model.Request

class RequestAdapter (private val context: Context,
                      private val dataSource: ArrayList<Request>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_request, parent, false)
        val txtViewListItemRequestNome = rowView.findViewById(R.id.txtViewListItemRequestNome) as TextView
        val txtViewListItemRequestNomePedinte = rowView.findViewById(R.id.txtViewListItemRequestNomePedinte) as TextView
        val txtViewListItemRequestDuracaoDias = rowView.findViewById(R.id.txtViewListItemRequestDuracaoDias) as TextView
        val txtViewListItemRequestMotivo = rowView.findViewById(R.id.txtViewListItemRequestMotivo) as TextView

        val request = getItem(position) as Request
        txtViewListItemRequestNome.text = request.name
        txtViewListItemRequestNomePedinte.text = request.user.name
        txtViewListItemRequestDuracaoDias.text = "Por ${request.duration} dias"
        txtViewListItemRequestMotivo.text = request.reason

        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}