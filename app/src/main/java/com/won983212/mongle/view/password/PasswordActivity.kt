package com.won983212.mongle.view.password

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.won983212.mongle.R
import com.won983212.mongle.databinding.ActivityPasswordBinding

enum class PasswordActivityMode {
    SET_PASSWORD, AUTH_PASSWORD
}

class PasswordActivity : AppCompatActivity(), View.OnClickListener, PasswordFullListener {
    private lateinit var pwdIndicatorButtons: Array<RadioButton>
    private val pwdMemory = PasswordMemory(4)
    private var mode = PasswordActivityMode.AUTH_PASSWORD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mode = (intent.getSerializableExtra(MODE)
            ?: PasswordActivityMode.AUTH_PASSWORD) as PasswordActivityMode

        when (mode) {
            PasswordActivityMode.SET_PASSWORD -> {
                binding.textPwdTitle.text = resources.getString(R.string.pwd_set_title)
            }
            PasswordActivityMode.AUTH_PASSWORD -> {
                binding.textPwdTitle.text = resources.getString(R.string.pwd_auth_title)
                binding.textPwdSubtitle.visibility = View.GONE
            }
        }

        pwdIndicatorButtons = arrayOf(
            binding.btnPwd1,
            binding.btnPwd2,
            binding.btnPwd3,
            binding.btnPwd4
        )

        pwdMemory.setOnFullListener(this)
        initEvents(binding)
    }

    private fun initEvents(binding: ActivityPasswordBinding) {
        arrayOf(
            binding.btnNumpad0,
            binding.btnNumpad1,
            binding.btnNumpad2,
            binding.btnNumpad3,
            binding.btnNumpad4,
            binding.btnNumpad5,
            binding.btnNumpad6,
            binding.btnNumpad7,
            binding.btnNumpad8,
            binding.btnNumpad9,
            binding.btnNumpadDelete
        ).forEach { it.setOnClickListener(this) }
    }

    private fun appendPassword(digit: Char) {
        val length = pwdMemory.pushDigit(digit)
        if (length > 0) {
            pwdIndicatorButtons[length - 1].isChecked = true
        } else {
            pwdIndicatorButtons.forEach { it.isChecked = false }
        }
    }

    private fun removePassword() {
        val length = pwdMemory.popDigit()
        pwdIndicatorButtons[length].isChecked = false
    }

    override fun onClick(view: View?) {
        if (view is Button) {
            appendPassword(view.text[0])
        } else if (view is ImageButton) {
            removePassword()
        }
    }

    override fun onFull(password: String) {
        // TODO Implement match password
    }

    companion object {
        const val MODE = "mode"
    }
}