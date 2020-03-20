package com.tlioylc.client.module.login

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.tlioylc.client.R
import com.tlioylc.client.module.base.BaseActivity
import com.tlioylc.client.module.main.MainActivity
import com.tlioylc.client.module.main.MainActivityBuilder
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private var textWatcher : TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val b : Boolean =  run{
                val phone = _login_activity_phone_number.text.toString().trim()
                val str = if(_login_activity_select_login_type.isSelected){
                    _login_activity_pwd.text.toString().trim()
                }else{
                    _login_activity_code.text.toString().trim()
                }
                !TextUtils.isEmpty(phone) and !TextUtils.isEmpty(str)
            }

            _phone_login_activity_confirm_login.setBackgroundResource(
                    if(b)
                        R.drawable.bg_confirm_primary_20_radius_clickable
                    else
                        R.drawable.bg_e2e2e2_25_radius
            )
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        _login_title.paint.typeface = Typeface.DEFAULT_BOLD

        val spannable : Spannable = Spannable.Factory.getInstance().newSpannable("点击登录即同意用户协议与隐私条款")
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View?) {
                ToastUtils.showShort("????")
            }
            override fun updateDrawState(ds: TextPaint?) {
                ds?.color = Color.parseColor("#07CC9B")
                ds?.bgColor =  Color.parseColor("#00000000")
                ds?.linkColor =  Color.parseColor("#07CC9B")
                ds?.flags = Paint.UNDERLINE_TEXT_FLAG
                ds?.isAntiAlias = true
            }
        },7,16, Spanned.SPAN_MARK_MARK)

        _login_activity_confirm_aggrement.movementMethod = LinkMovementMethod.getInstance()
        _login_activity_confirm_aggrement.text = spannable

        _login_activity_select_login_type.setOnClickListener {switchLoginType()}

        _login_activity_phone_number.addTextChangedListener(textWatcher)
        _login_activity_code.addTextChangedListener(textWatcher)
        _login_activity_pwd.addTextChangedListener(textWatcher)


        _phone_login_activity_confirm_login.setOnClickListener { login() }
    }

    private fun login(){
        MainActivityBuilder.init("登录成功").open(this)
    }

    private fun switchLoginType(){
        if(_login_activity_select_login_type.isSelected){
            _login_activity_select_login_type.isSelected = false
            _login_activity_code_ly.visibility = View.VISIBLE
            _login_activity_pwd_ly.visibility = View.GONE
            _phone_login_activity_get_code.visibility = View.VISIBLE
            _phone_login_activity_code_lineline.visibility = View.VISIBLE
        }else {
            _login_activity_select_login_type.isSelected = true
            _login_activity_code_ly.visibility = View.INVISIBLE
            _login_activity_pwd_ly.visibility = View.VISIBLE
            _phone_login_activity_get_code.visibility = View.GONE
            _phone_login_activity_code_lineline.visibility = View.GONE
        }
    }
}
