package com.example.newsjsonviewer.features.newslist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.newsjsonviewer.R

class CountryListDialogFragment(
    private val countryList: List<String>,
    private val clickListener: (String) -> Unit
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.country_dialog_title)
                .setItems(countryList.toTypedArray()
                ) { _, which ->
                    clickListener(countryList[which])
                }
                .create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}