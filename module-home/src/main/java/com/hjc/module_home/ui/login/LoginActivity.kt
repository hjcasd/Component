package com.hjc.module_home.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.router.RoutePath
import com.hjc.library_widget.bar.OnViewLeftClickListener
import com.hjc.module_home.R
import com.hjc.module_home.databinding.HomeActivityLoginBinding
import com.hjc.module_home.viewmodel.login.LoginViewModel

/**
 * @Author: HJC
 * @Date: 2021/1/8 11:29
 * @Description: 登录
 */
@Route(path = RoutePath.Home.LOGIN)
class LoginActivity : BaseActivity<HomeActivityLoginBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.home_activity_login
    }

    override fun createViewModel(): LoginViewModel {
        return ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.home_color)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBindingView.loginViewModel = mViewModel
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            loginData.observe(this@LoginActivity) { result ->
                LogUtils.e("result: ${result.username}")
                mBindingView.etPhone.clearFocus()
                mBindingView.etCode.clearFocus()
            }
        }
    }

    override fun addListeners() {
        mBindingView.onClickListener = this

        mBindingView.titleBar.setOnViewLeftClickListener(object : OnViewLeftClickListener {

            override fun leftClick(view: View?) {
                finish()
            }

        })
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> mViewModel?.login()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.home_login_close_enter, R.anim.home_login_close_exit)
    }

}