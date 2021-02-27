package com.hjc.module_home.viewmodel.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_base.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.LoginBean

class LoginViewModel(application: Application) : KotlinViewModel(application) {

    val loginData = MutableLiveData<LoginBean>()
    val usernameData = MutableLiveData<String>()
    val passwordData = MutableLiveData<String>()

    fun login() {
        if (StringUtils.isEmpty(usernameData.value)) {
            ToastUtils.showShort("请输入用户名")
            return
        }
        if (StringUtils.isEmpty(passwordData.value)) {
            ToastUtils.showShort("请输入密码")
            return
        }
        launchWrapper({
            RetrofitClient.getApiService1().login(usernameData.value, passwordData.value)
        }, { result ->
            loginData.value = result
        }, isShowLoading = true)
    }
}