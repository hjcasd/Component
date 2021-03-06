package com.hjc.module_senior.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_common.router.RoutePath
import com.hjc.module_senior.R
import com.hjc.module_senior.databinding.SeniorFragmentBinding
import com.hjc.module_senior.viewmodel.SeniorViewModel

/**
 * @Author: HJC
 * @Date: 2019/7/26 10:42
 * @Description: SeniorFragment
 */
@Route(path = RoutePath.Senior.FRAGMENT)
class SeniorFragment : BaseFragment<SeniorFragmentBinding, SeniorViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.senior_fragment
    }

    override fun createViewModel(): SeniorViewModel {
        return ViewModelProvider(this)[SeniorViewModel::class.java]
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.senior_color)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel?.show()
    }

    override fun addListeners() {
        mBindingView.onClickListener = this
    }

    override fun onSingleClick(v: View?) {

    }
}