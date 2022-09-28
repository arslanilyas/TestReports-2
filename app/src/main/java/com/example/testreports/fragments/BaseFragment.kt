package com.example.testreports.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.testreports.utils.DialogUtils


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    lateinit var dialog: AlertDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = DialogUtils.getProgressDialog(requireContext())


    }



    abstract fun initViews()

    abstract fun attachViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        attachViewModel()
    }


    fun showProgressDialog(show: Boolean) {

        if (dialog != null && show) {
            if (!dialog.isShowing)
                dialog.apply {
                    show()
                }
        } else if (dialog != null && !show) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }





    protected fun onBackPress() {
        requireActivity().onBackPressed()
    }


}
