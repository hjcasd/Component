package com.hjc.component

import com.hjc.library_base.BaseApplication
import com.hjc.library_common.config.ModuleLifecycleConfig

/**
 * @Author: HJC
 * @Date: 2021/1/9 15:17
 * @Description: Application
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
    }
}