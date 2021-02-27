package com.hjc.library_common.router

/**
 * @Author: HJC
 * @Date: 2021/1/8 16:24
 * @Description: 管理路由路径
 */
object RoutePath {

    // 主模块
    object Main {
        private const val MODULE_MAIN = "/module_main"

        // Splash页面
        const val SPLASH = "$MODULE_MAIN/splash"

        // 主界面
        const val MAIN = "$MODULE_MAIN/main"

        // Web页面
        const val WEB = "$MODULE_MAIN/web"
    }


    // 首页模块
    object Home {
        private const val MODULE_HOME = "/module_home"

        // Home页面
        const val HOME = "$MODULE_HOME/home"

        // HomeFragment
        const val FRAGMENT = "$MODULE_HOME/fragment"

        // Login页面
        const val LOGIN = "$MODULE_HOME/login"

        // Coroutines页面
        const val COROUTINES = "$MODULE_HOME/coroutines"
    }


    // 框架模块
    object Frame {
        private const val MODULE_FRAME = "/module_frame"

        // Frame页面
        const val FRAME = "$MODULE_FRAME/frame"

        // FrameFragment
        const val FRAGMENT = "$MODULE_FRAME/fragment"

        // LoadSir页面
        const val LOAD_SIR = "$MODULE_FRAME/list"
    }


    // 高级模块
    object Senior {
        private const val MODULE_SENIOR = "/module_senior"

        // Senior页面
        const val SENIOR = "$MODULE_SENIOR/senior"

        // SeniorFragment
        const val FRAGMENT = "$MODULE_SENIOR/fragment"
    }


    // 其他模块
    object Other {
        private const val MODULE_OTHER = "/module_other"

        // Other页面
        const val OTHER = "$MODULE_OTHER/other"

        // OtherFragment
        const val FRAGMENT = "$MODULE_OTHER/fragment"
    }

}