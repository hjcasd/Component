package com.hjc.library_widget.bar

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hjc.library_widget.R

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:32
 * @Description: 自定义标题栏(代替Toolbar使用)
 */
class TitleBar @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(
    mContext, attrs, defStyleAttr
) {
    private lateinit var tvTitle: TextView
    private lateinit var ivLeftImg: ImageView
    private lateinit var ivRightImg: ImageView
    private lateinit var titleLine: View

    private var mTitleText: String? = null
    private var mTitleSize = 0f
    private var mTitleColor = 0
    private var mLeftImage = 0
    private var mRightImage = 0
    private var isShowLine = false

    private var mClickListener: OnViewClickListener? = null
    private var mLeftClickListener: OnViewLeftClickListener? = null
    private var mRightClickListener: OnViewRightClickListener? = null

    init {
        initTypeArray(attrs)
        initView()
        initData()
        addListener()
    }

    private fun initTypeArray(attrs: AttributeSet?) {
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        mLeftImage = ta.getResourceId(R.styleable.TitleBar_leftImage, 0)
        mTitleText = ta.getString(R.styleable.TitleBar_titleText)
        mTitleSize = ta.getDimensionPixelSize(R.styleable.TitleBar_titleSize, sp2px(18f)).toFloat()
        mTitleColor = ta.getColor(R.styleable.TitleBar_titleColor, Color.BLACK)
        mRightImage = ta.getResourceId(R.styleable.TitleBar_rightImage, 0)
        isShowLine = ta.getBoolean(R.styleable.TitleBar_isShowLine, false)
        ta.recycle()
    }

    private fun initView() {
        LayoutInflater.from(mContext).inflate(R.layout.widget_layout_title_bar, this)
        ivLeftImg = findViewById(R.id.iv_left_img)
        tvTitle = findViewById(R.id.tv_title)
        ivRightImg = findViewById(R.id.iv_right_img)
        titleLine = findViewById(R.id.title_line)
    }

    private fun initData() {
        //左图标
        if (mLeftImage != 0) {
            ivLeftImg.visibility = VISIBLE
            ivLeftImg.setImageResource(mLeftImage)
        } else {
            ivLeftImg.visibility = GONE
        }

        //标题
        if (!TextUtils.isEmpty(mTitleText)) {
            tvTitle.visibility = VISIBLE
            tvTitle.text = mTitleText
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, px2sp(mTitleSize).toFloat())
            tvTitle.setTextColor(mTitleColor)
        } else {
            tvTitle.visibility = GONE
        }

        //右图标
        if (mRightImage != 0) {
            ivRightImg.visibility = VISIBLE
            ivRightImg.setImageResource(mRightImage)
        } else {
            ivRightImg.visibility = GONE
        }
        titleLine.visibility = if (isShowLine) VISIBLE else GONE
    }

    private fun addListener() {
        ivLeftImg.setOnClickListener { v ->
            if (ivRightImg.visibility == VISIBLE) {
                mClickListener?.leftClick(v)
            } else {
                mLeftClickListener?.leftClick(v)
            }
        }
        ivRightImg.setOnClickListener { v ->
            if (ivLeftImg.visibility == VISIBLE) {
                mClickListener?.rightClick(v)
            } else {
                mRightClickListener?.rightClick(v)
            }
        }
    }

    fun setTitle(title: String?) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.visibility = VISIBLE
            tvTitle.text = title
        } else {
            tvTitle.visibility = GONE
        }
    }

    fun setTitleSize(size: Int) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
    }

    fun setTitleTextColor(color: Int) {
        tvTitle.setTextColor(color)
    }

    private fun sp2px(spValue: Float): Int {
        val fontScale = mContext.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    private fun px2sp(pxValue: Float): Int {
        val fontScale = mContext.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun setOnViewClickListener(listener: OnViewClickListener?) {
        mClickListener = listener
    }

    fun setOnViewLeftClickListener(listener: OnViewLeftClickListener?) {
        mLeftClickListener = listener
    }

    fun setOnViewRightClickListener(listener: OnViewRightClickListener?) {
        mRightClickListener = listener
    }

}