package com.facom.sharehoodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.facom.sharehoodapp.R
import com.facom.sharehoodapp.model.Lending
import com.facom.sharehoodapp.model.User
import com.facom.sharehoodapp.util.DateUtil
import java.util.*
import kotlin.collections.ArrayList


class LendingAdapter (private val context: Context,
                      private val dataSource: ArrayList<Lending>,
                      private val loggedUser: User) : BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_lending, parent, false)
        val txtViewListItemLendingNome = rowView.findViewById(R.id.txtViewListItemLendingNome) as TextView
        val txtViewListItemLendingUsuario = rowView.findViewById(R.id.txtViewListItemLendingUsuario) as TextView
        val txtViewListItemLendingDataEmprestimo = rowView.findViewById(R.id.txtViewListItemLendingDataEmprestimo) as TextView
        val txtViewListItemLendingDataDevolucao = rowView.findViewById(R.id.txtViewListItemLendingDataDevolucao) as TextView
        val imgViewListItemLendingIcon = rowView.findViewById(R.id.imgViewListItemLendingIcon) as ImageView



        val item = getItem(position) as Lending
        txtViewListItemLendingNome.text = item.request.name
        if(item.lender.id == loggedUser.id) {
            txtViewListItemLendingUsuario.text = "Para: ${item.borrower.name}"
            imgViewListItemLendingIcon.setImageResource(R.drawable.ic_upload_black_24dp)
        } else txtViewListItemLendingUsuario.text = "De: ${item.lender.name}"
        txtViewListItemLendingDataEmprestimo.text = "Desde: ${DateUtil.toBRFormat(DateUtil.fromISO8601UTC(item.createdDate))}"
//        if(item.lender.id == loggedUser.id) txtViewListItemLendingDataEmprestimo.text = "Pegar devolta: ${DateUtil.toBRFormat(DateUtil.fromISO8601UTC(item.createdDate))}"
//        else txtViewListItemLendingDataEmprestimo.text = "Devolver: ${DateUtil.toBRFormat(DateUtil.fromISO8601UTC(item.createdDate))}"

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