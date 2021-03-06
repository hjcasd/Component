package com.hjc.module_other.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_base.utils.ActivityHelper
import com.hjc.library_common.router.RoutePath
import com.hjc.module_other.R
import com.hjc.module_other.databinding.OtherFragmentBinding
import com.hjc.module_other.viewmodel.OtherViewModel

/**
 * @Author: HJC
 * @Date: 2019/7/26 10:42
 * @Description: OtherFragment
 */
@Route(path = RoutePath.Other.FRAGMENT)
class OtherFragment : BaseFragment<OtherFragmentBinding, OtherViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.other_fragment
    }

    override fun createViewModel(): OtherViewModel {
        return ViewModelProvider(this)[OtherViewModel::class.java]
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.other_color)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mViewModel?.show()
    }

    override fun addListeners() {
        mBindingView.onClickListener = this
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.btn_exit -> {
                AlertDialog.Builder(mContext)
                    .setTitle("确认退出App吗")
                    .setNegativeButton("取消") { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
                    .setPositiveButton("确定") { dialog: DialogInterface?, _: Int ->
                        dialog?.dismiss()
                        ActivityHelper.finishAllActivities()
                    }
                    .show()
            }
        }
    }
}