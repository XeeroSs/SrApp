package com.xeross.srapp.ui.categoryform.types

import androidx.fragment.app.Fragment
import com.xeross.srapp.R
import com.xeross.srapp.ui.categoryform.category.CategoryFormActivity.Companion.EXTRA_INPUT_TEXT_CATEGORY_FRAGMENT
import com.xeross.srapp.ui.categoryform.category.CategoryFormActivity.Companion.EXTRA_INPUT_TEXT_SUBCATEGORY_FRAGMENT
import com.xeross.srapp.ui.categoryform.category.CategoryFormActivity.Companion.EXTRA_UPLOAD_IMAGE_CATEGORY_FRAGMENT
import com.xeross.srapp.ui.categoryform.category.CategoryFormActivity.Companion.EXTRA_UPLOAD_IMAGE_SUBCATEGORY_FRAGMENT
import com.xeross.srapp.ui.categoryform.fragment.CategoryFormIllustrationFragment
import com.xeross.srapp.ui.categoryform.fragment.CategoryFormInputTextFragment
import com.xeross.srapp.ui.categoryform.fragment.CategoryFormUploadImageFragment
import com.xeross.srapp.ui.categoryform.interfaces.ICategoryFormType

enum class CategoryFormPageType(val position: Int, private val fragments: Fragment) : ICategoryFormType {
    
    CREATE_YOUR_CATEGORY(0, CategoryFormIllustrationFragment.newInstance(R.string.add_your_categories_title, R.string.add_your_categories_label, R.drawable.ill_gaming_amico)),
    CHOICE_TITLE_FOR_CATEGORY(1, CategoryFormInputTextFragment.newInstance(EXTRA_INPUT_TEXT_CATEGORY_FRAGMENT, R.string.add_your_categories_name_title, R.string.add_your_categories_name_label, R.string.name_of_category)),
    UPLOAD_YOUR_IMAGE_FOR_CATEGORY(2, CategoryFormUploadImageFragment.newInstance(EXTRA_UPLOAD_IMAGE_CATEGORY_FRAGMENT, R.string.add_your_categories_image_title, R.string.add_your_categories_image_label, R.drawable.ill_image_upload_amico)),
    
    CREATE_YOUR_SUBCATEGORY(3, CategoryFormIllustrationFragment.newInstance(R.string.add_your_subcategories_title, R.string.add_your_subcategories_label, R.drawable.ill_fitness_stats_amico)),
    CHOICE_TITLE_FOR_SUBCATEGORY(4, CategoryFormInputTextFragment.newInstance(EXTRA_INPUT_TEXT_SUBCATEGORY_FRAGMENT, R.string.add_your_subcategories_name_title, R.string.add_your_subcategories_name_label, R.string.name_of_subcategory)),
    UPLOAD_YOUR_IMAGE_FOR_SUBCATEGORY(5, CategoryFormUploadImageFragment.newInstance(EXTRA_UPLOAD_IMAGE_SUBCATEGORY_FRAGMENT, R.string.add_your_subcategories_image_title, R.string.add_your_subcategories_image_label, R.drawable.ill_image_upload_amico)), ;
    
    override fun getFragmentPosition() = position
    override fun getFragment() = fragments
    
}