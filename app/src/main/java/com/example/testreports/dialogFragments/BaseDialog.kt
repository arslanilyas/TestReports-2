package com.example.testreports.dialogFragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.testreports.R



abstract class BaseDialog(): DialogFragment() {


    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.GeneralDialogStyle);
        val builder = AlertDialog.Builder(requireContext(), R.style.GeneralDialogStyle)
        val inflater = requireActivity().layoutInflater
        val dialog = inflater.inflate(getLayoutId(), null)

        initViews(dialog)
        builder.setView(dialog)
        return builder.create()
    }


    abstract fun initViews(view: View)

    abstract fun getLayoutId():Int

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0.8f
        window?.attributes = windowParams
    }




}