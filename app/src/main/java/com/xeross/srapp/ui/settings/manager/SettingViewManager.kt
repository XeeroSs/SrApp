package com.xeross.srapp.ui.settings.manager

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.xeross.srapp.R
import com.xeross.srapp.ui.category.management.types.SettingSizeViewType
import java.util.function.Function


@Suppress("PrivatePropertyName")
class SettingViewManager(private val context: Context, private val content: LinearLayout) {
    
    private val MARGIN_START_CATEGORY_TEXT_VIEW = 20.toDp()
    private val MARGIN_BOTTOM_CATEGORY_TEXT_VIEW = 20.toDp()
    private val MARGIN_TOP_CATEGORY_TEXT_VIEW = 50.toDp()
    private val TEXT_SIZE_CATEGORY_TEXT_VIEW = 18f
    private val TEXT_SIZE_TITLE_SUBCATEGORY_TEXT_VIEW = 15f
    private val TEXT_SIZE_DESCRIPTION_SUBCATEGORY_TEXT_VIEW = 12f
    
    private val MARGIN_END_TEXT_SUBCATEGORY = 20.toDp()
    
    private var top = true
    
    fun category(resStringId: Int) {
        textCategory(resStringId)
        divider()
    }
    
    fun subcategoryWithIcon(resStringId: Int, resDrawableId: Int, textIsClickable: Boolean, function: Function<Void?, Boolean>) {
        val icon = getIcon(resDrawableId)
        val width = SettingSizeViewType.ICON.widthInPixel.toDp()
        val height = SettingSizeViewType.ICON.heightInPixel.toDp()
        textSubcategoryWithIcon(resStringId, icon, width, height, textIsClickable, function)
        divider()
    }
    
    fun subcategoryWithIcon(resTitleId: Int, resDescriptionId: Int, resDrawableId: Int, textIsClickable: Boolean, function: Function<Void?, Boolean>) {
        val icon = getIcon(resDrawableId)
        val width = SettingSizeViewType.ICON.widthInPixel.toDp()
        val height = SettingSizeViewType.ICON.heightInPixel.toDp()
        textSubcategoryWithIcon(resTitleId, resDescriptionId, icon, width, height, textIsClickable, function)
        divider()
    }
    
    fun subcategoryWithSwitch(resStringId: Int, defaultChecked: Boolean, textIsClickable: Boolean, function: Function<Void?, Boolean>): SwitchMaterial {
        
        val switchMaterial = getSwitch(defaultChecked)
        
        val width = SettingSizeViewType.SWITCH.widthInPixel
        val height = SettingSizeViewType.SWITCH.heightInPixel
        
        textSubcategoryWithIcon(resStringId, switchMaterial, width, height, textIsClickable, function)
        divider()
        
        return switchMaterial
    }
    
    fun subcategoryWithSwitch(resTitleId: Int, resDescriptionId: Int, defaultChecked: Boolean, textIsClickable: Boolean, function: Function<Void?, Boolean>): SwitchMaterial {
        
        val switchMaterial = getSwitch(defaultChecked)
        val width = SettingSizeViewType.SWITCH.widthInPixel
        val height = SettingSizeViewType.SWITCH.heightInPixel
        
        textSubcategoryWithIcon(resTitleId, resDescriptionId, switchMaterial, width, height, textIsClickable, function)
        divider()
        
        return switchMaterial
    }
    
    fun subcategoryWithCustomView(resTitleId: Int, view: View, customViewWidth: Int, customViewHeight: Int, textIsClickable: Boolean, function: Function<Void?, Boolean>) {
        textSubcategoryWithIcon(resTitleId, view, customViewWidth, customViewHeight, textIsClickable, function)
        divider()
    }
    
    fun subcategoryWithCustomView(resTitleId: Int, resDescriptionId: Int, view: View, customViewWidth: Int, customViewHeight: Int, textIsClickable: Boolean, function: Function<Void?, Boolean>) {
        textSubcategoryWithIcon(resTitleId, resDescriptionId, view, customViewWidth, customViewHeight, textIsClickable, function)
        divider()
    }
    
    fun build() {
        content.requestLayout()
    }
    
    private fun getSwitch(isChecked: Boolean): SwitchMaterial {
        
        val switch = SwitchMaterial(context)
        
        switch.isChecked = isChecked
        switch.backgroundTintList = ContextCompat.getColorStateList(context, R.color.pink_gradient)
        
        return switch
    }
    
    private fun getText(resStringId: Int, resColorId: Int, textSize: Float, isAllCaps: Boolean, maxLines: Int = 1,
                        fontFamily: Typeface? = ResourcesCompat.getFont(context, R.font.gilroy_light)): TextView {
        val textView = TextView(context)
        textView.text = context.getString(resStringId)
        textView.textSize = textSize
        textView.typeface = fontFamily
        textView.isAllCaps = isAllCaps
        textView.maxLines = maxLines
        textView.ellipsize = TextUtils.TruncateAt.END
        textView.setTextColor(getColor(resColorId))
        return textView
    }
    
    private fun textCategory(resStringId: Int) {
        
        val textView = getText(resStringId, R.color.light_gray, TEXT_SIZE_CATEGORY_TEXT_VIEW, true)
        
        val textViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (!top) textViewParams.topMargin = MARGIN_TOP_CATEGORY_TEXT_VIEW else top = false
        textViewParams.gravity = Gravity.CENTER_VERTICAL
        textView.layoutParams = textViewParams
        
        content.addView(textView)
    }
    
    private fun textSubcategoryWithIcon(resTitleId: Int, resDescriptionId: Int, view: View, widthView: Int, heightView: Int, textIsClickable: Boolean, function: Function<Void?, Boolean>) {
        
        val relativeLayoutContent = getContent(textIsClickable, function)
        
        val linearLayoutContent = LinearLayout(context)
        linearLayoutContent.orientation = LinearLayout.VERTICAL
        relativeLayoutContent.addView(linearLayoutContent)
        
        view.id = View.generateViewId()
        relativeLayoutContent.addView(view)
        
        val iconParams = RelativeLayout.LayoutParams(widthView, heightView)
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL)
        view.layoutParams = iconParams
        
        val linearLayoutContentParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayoutContentParams.marginEnd = MARGIN_END_TEXT_SUBCATEGORY
        linearLayoutContentParams.addRule(RelativeLayout.START_OF, view.id)
        linearLayoutContentParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        linearLayoutContent.layoutParams = linearLayoutContentParams
        
        val textView = getText(resTitleId, R.color.on_surface, TEXT_SIZE_TITLE_SUBCATEGORY_TEXT_VIEW, true)
        linearLayoutContent.addView(textView)
        
        val textViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = textViewParams
        
        val textViewDescription = getText(resDescriptionId, R.color.light_gray, TEXT_SIZE_DESCRIPTION_SUBCATEGORY_TEXT_VIEW, false, maxLines = 2)
        linearLayoutContent.addView(textViewDescription)
        
        val textViewDescriptionParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textViewDescription.layoutParams = textViewDescriptionParams
        
        content.addView(relativeLayoutContent)
    }
    
    private fun textSubcategoryWithIcon(resStringId: Int, view: View, widthView: Int, heightView: Int, textIsClickable: Boolean, function: Function<Void?, Boolean>) {
        
        val relativeLayoutContent = getContent(textIsClickable, function)
        
        val linearLayoutContent = LinearLayout(context)
        relativeLayoutContent.addView(linearLayoutContent)
        
        val linearLayoutContentParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayoutContentParams.marginEnd = MARGIN_END_TEXT_SUBCATEGORY
        linearLayoutContentParams.addRule(RelativeLayout.ALIGN_PARENT_START)
        linearLayoutContent.layoutParams = linearLayoutContentParams
        
        val textView = getText(resStringId, R.color.on_surface, TEXT_SIZE_TITLE_SUBCATEGORY_TEXT_VIEW, true)
        linearLayoutContent.addView(textView)
        
        val textViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textViewParams.gravity = Gravity.CENTER_VERTICAL
        textView.layoutParams = textViewParams
        
        view.id = View.generateViewId()
        relativeLayoutContent.addView(view)
        
        val iconParams = RelativeLayout.LayoutParams(widthView, heightView)
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL)
        view.layoutParams = iconParams
        
        content.addView(relativeLayoutContent)
    }
    
    private fun getIcon(resDrawableId: Int): ImageView {
        val icon = ImageView(context)
        
        icon.setImageDrawable(ContextCompat.getDrawable(context, resDrawableId))
        icon.setColorFilter(ContextCompat.getColor(context, R.color.on_surface), PorterDuff.Mode.SRC_IN)
        return icon
    }
    
    private fun getContent(textIsClickable: Boolean, function: Function<Void?, Boolean>): RelativeLayout {
        val relativeLayoutContent = RelativeLayout(context)
        
        val relativeLayoutContentParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        relativeLayoutContentParams.topMargin = MARGIN_BOTTOM_CATEGORY_TEXT_VIEW
        relativeLayoutContent.layoutParams = relativeLayoutContentParams
        
        if (textIsClickable) setViewClickable(relativeLayoutContent, function)
        return relativeLayoutContent
    }
    
    private fun setViewClickable(view: View, function: Function<Void?, Boolean>) {
        view.isClickable = true
        view.isFocusable = true
        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
        view.setBackgroundResource(outValue.resourceId)
        view.setOnClickListener { function.apply(null) }
    }
    
    private fun divider() {
        val divider = LinearLayout(context)
        
        divider.setBackgroundColor(getColor(R.color.super_light_gray))
        
        val dividerParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2.toDp())
        dividerParams.topMargin = MARGIN_BOTTOM_CATEGORY_TEXT_VIEW
        divider.layoutParams = dividerParams
        
        content.addView(divider)
    }
    
    private fun Int.toDp() = this * 3
    
    private fun getColor(resColorId: Int) = ContextCompat.getColor(context, resColorId)
    
}