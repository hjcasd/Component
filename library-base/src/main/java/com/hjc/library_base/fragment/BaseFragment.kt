package com.hjc.library_base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.hjc.library_base.base.BaseActionEvent
import com.hjc.library_base.base.IBaseView
import com.hjc.library_base.base.IViewModelAction
import com.hjc.library_base.dialog.LoadingDialog
import com.hjc.library_base.loadsir.EmptyCallback
import com.hjc.library_base.loadsir.ErrorCallback
import com.hjc.library_base.loadsir.ProgressCallback
import com.hjc.library_base.loadsir.TimeoutCallback
import com.hjc.library_base.utils.ClickUtils
import com.hjc.library_base.viewmodel.BaseViewModel
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @Author: HJC
 * @Date: 2019/1/4 14:58
 * @Description: Fragment基类
 */
abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel> : Fragment(), IBaseView,
    View.OnClickListener {

    // Fragment对应的Activity(避免使用getActivity()导致空指针异常)
    protected lateinit var mContext: Context

    // ViewDataBinding
    protected lateinit var mBindingView: VDB

    // ViewModel
    protected var mViewModel: VM? = null

    private var mLoadService: LoadService<*>? = null
    private var mLoadingDialog: LoadingDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindingView = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBindingView.lifecycleOwner = this
        return mBindingView.root
    }

    /**
     * 获取布局的ID
     */
    abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        initData(savedInstanceState)
        observeLiveData()
        addListeners()
    }

    private fun initViewModel() {
        if (mViewModel == null) {
            mViewModel = createViewModel()
        }

        mViewModel?.let {
            val viewModelAction: IViewModelAction = it
            viewModelAction.getActionLiveData()
                .observe(viewLifecycleOwner, { baseActionEvent: BaseActionEvent? ->
                    baseActionEvent?.let { event ->
                        when (event.action) {
                            BaseActionEvent.SHOW_LOADING_DIALOG -> showLoading()

                            BaseActionEvent.DISMISS_LOADING_DIALOG -> dismissLoading()

                            BaseActionEvent.SHOW_PROGRESS -> showProgress()

                            BaseActionEvent.SHOW_CONTENT -> showContent()

                            BaseActionEvent.SHOW_EMPTY -> showEmpty()

                            BaseActionEvent.SHOW_ERROR -> showError()

                            BaseActionEvent.SHOW_TIMEOUT -> showTimeout()

                            else -> {
                            }
                        }
                    }
                })
        }
    }

    /**
     * 获取viewModel
     */
    abstract fun createViewModel(): VM?

    /**
     * 初始化View
     */
    open fun initView() {
        getImmersionBar()?.init()
        ARouter.getInstance().inject(this)
    }

    /**
     * 初始化沉浸式
     */
    protected open fun getImmersionBar(): ImmersionBar? {
        return null
    }

    /**
     * 初始化数据
     */
    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 监听LiveData
     */
    open fun observeLiveData() {}

    /**
     * 设置监听器
     */
    abstract fun addListeners()

    /**
     * 设置点击事件
     */
    abstract fun onSingleClick(v: View?)

    override fun onClick(v: View) {
        //避免快速点击
        if (ClickUtils.isFastClick()) {
            ToastUtils.showShort("点的太快了,歇会呗!")
            return
        }
        onSingleClick(v)
    }

    /**
     * 注册LoadSir
     *
     * @param view 状态视图
     */
    protected fun setLoadSir(view: View?) {
        if (mLoadService == null) {
            mLoadService = LoadSir.getDefault().register(view) { v: View? -> onRetryBtnClick(v) }
        }
    }

    /**
     * 失败重试,重新加载事件
     */
    open fun onRetryBtnClick(v: View?) {}

    override fun showLoading() {
        if (mLoadingDialog == null){
            mLoadingDialog = LoadingDialog.newInstance()
        }
        mLoadingDialog?.showDialog(childFragmentManager)
    }

    override fun dismissLoading() {
        mLoadingDialog?.let {
            val dialog = it.dialog
            if (dialog != null && dialog.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun showContent() {
        mLoadService?.showSuccess()
    }

    override fun showProgress() {
        mLoadService?.showCallback(ProgressCallback::class.java)
    }

    override fun showEmpty() {
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    override fun showError() {
        mLoadService?.showCallback(ErrorCallback::class.java)
    }

    override fun showTimeout() {
        mLoadService?.showCallback(TimeoutCallback::class.java)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            getImmersionBar()?.init()
        }
    }

}