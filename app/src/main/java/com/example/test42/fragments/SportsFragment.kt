package com.example.test42.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.test42.R
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*
import kotlinx.android.synthetic.main.fragment_sports.*

/**
 * Created by bjarne on 01/12/2017.
 */
class SportsFragment : Fragment(), Validator.ValidationListener {
    @NotEmpty
    @Email
    private var emailEditText: EditText? = null

    @NotEmpty
    @Password(min = 6)
    private var passwordEditText: EditText? = null

    @ConfirmPassword
    private var repeatPasswordEditText: EditText? = null

    @NotEmpty
    @Max(200)
    @Min(0)
    private var integerEditText: EditText? = null

    @NotEmpty
    @DecimalMax(2.0)
    @DecimalMin(0.0)
    private var decimalEditText: EditText? = null

    @Checked(message = "You must agree to the terms.")
    private var termsCheckBox: CheckBox? = null

    lateinit private var validator:Validator
/*
    private var _hej = 5
    var hej:Int
        get() = _hej
        set(value) = {
        this._hej = value
    }
*/
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // https://www.journaldev.com/9266/android-fragment-lifecycle
        // validation based on https://github.com/ragunathjawahar/android-saripaar

        val view = inflater.inflate(R.layout.fragment_sports, container, false)


        validator = Validator(this)
        validator.setValidationListener(this)


        continueButton.setOnClickListener {
            validator.validate()


        }

        return view
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        if(errors == null) { return }
        //auto-unwrap of errors occuring here..

        for(error in errors) {
            val view = error.view
            val message = error.getCollatedErrorMessage(activity)

            if(view is EditText) {
                //auto-cast of view...
                view.error = message
            } else {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onValidationSucceeded() {
        Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
    }
}