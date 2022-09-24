package com.xeross.srapp.ui.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity
import com.xeross.srapp.data.models.ProfileStatistic
import com.xeross.srapp.data.models.User
import com.xeross.srapp.databinding.ActivityProfileBinding
import com.xeross.srapp.ui.adapters.ProfileStatisticAdapter
import com.xeross.srapp.ui.profile.listener.IProfileStatistic
import com.xeross.srapp.ui.profile.types.ProfileStatisticType

class ProfileActivity : BaseActivity<ActivityProfileBinding>(), IProfileStatistic {
    
    override fun attachViewBinding(): ViewBinding {
        return ActivityProfileBinding.inflate(layoutInflater)
    }
    
    override fun getViewModelClass() = ProfileViewModel::class.java
    private var viewModel: ProfileViewModel? = null
    
    // Dialogs
    private var dialogView: View? = null
    private var dialog: AlertDialog? = null
    
    private var adapter: ProfileStatisticAdapter? = null
    private val profileStatistics = ArrayList<ProfileStatistic>()
    
    override fun getTotalTimes(viewModel: ProfileViewModel, user: User) {
        viewModel.getTotalTimes(user).observe(this, { value ->
            with(ProfileStatisticType.TOTAL_TIMES) {
                val profileStatistic = ProfileStatistic(resNameId, value, resColorId, resIconId)
                profileStatistics.add(profileStatistic)
                adapter?.notifyDataSetChanged()
                
            }
        })
    }
    
    override fun getBestRate(viewModel: ProfileViewModel, user: User) {
        viewModel.getBestRate(user).observe(this, { value ->
            with(ProfileStatisticType.PB_RATE) {
                val profileStatistic = ProfileStatistic(resNameId, value, resColorId, resIconId)
                profileStatistics.add(profileStatistic)
                adapter?.notifyDataSetChanged()
            }
        })
    }
    
    override fun getTotalBest(viewModel: ProfileViewModel, user: User) {
        viewModel.getTotalBest(user).observe(this, { value ->
            with(ProfileStatisticType.TOTAL_PB) {
                val profileStatistic = ProfileStatistic(resNameId, value, resColorId, resIconId)
                profileStatistics.add(profileStatistic)
                adapter?.notifyDataSetChanged()
            }
        })
    }
    
    override fun getLastBest(viewModel: ProfileViewModel, user: User) {
        viewModel.getLastBest(user).observe(this, { value ->
            with(ProfileStatisticType.LAST_PB_AT) {
                val profileStatistic = ProfileStatistic(resNameId, value, resColorId, resIconId)
                profileStatistics.add(profileStatistic)
                adapter?.notifyDataSetChanged()
            }
        })
    }
    
    override fun getAccountCreatedAt(viewModel: ProfileViewModel, user: User) {
        viewModel.getAccountCreatedAt(user).observe(this, { value ->
            with(ProfileStatisticType.ACCOUNT_CREATED_AT) {
                val profileStatistic = ProfileStatistic(resNameId, value, resColorId, resIconId)
                profileStatistics.add(profileStatistic)
                adapter?.notifyDataSetChanged()
            }
        })
    }
    
    private fun setUpDialogs() {
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_text, null, false).also {
            
            it.findViewById<MaterialButton>(R.id.dismiss_button)?.setOnClickListener { _ ->
                dialog?.dismiss()
            }
            
            dialog = MaterialAlertDialogBuilder(this, R.style.WrapEverythingDialog).setBackground(ColorDrawable(Color.TRANSPARENT)).setCancelable(true).setView(it).create()
        }
    }
    
    private fun editPseudo() {
        dialogView?.findViewById<MaterialButton>(R.id.submit_button)?.setOnClickListener { _ ->
        }
        
        dialog?.show()
    }
    
    private fun setProfileImage(viewModel: ProfileViewModel, user: User?) {
        setHeaderImage(viewModel.getProfileImage(user))
    }
    
    private fun setPseudo(viewModel: ProfileViewModel, user: User?) {
        binding.textName.text = getString(R.string.your_name, viewModel.getPseudo(user))
    }
    
    private fun getProfileStatistics() {
        viewModel?.let { viewModel ->
            viewModel.getObjectUser().observe(this, { user ->
                setProfileImage(viewModel, user)
                setPseudo(viewModel, user)
                if (user == null) return@observe
                getTotalTimes(viewModel, user)
                getBestRate(viewModel, user)
                getTotalBest(viewModel, user)
                getLastBest(viewModel, user)
                getAccountCreatedAt(viewModel, user)
            })
        }
    }
    
    override fun setUp() {
        viewModel = vm as ProfileViewModel
        viewModel?.build()
    }
    
    /**
     * Reduce size image based on content shape size for create a border
     */
    private fun setHeaderImage(uriProfileImage: String?) {
        val image = binding.imageHeader
        val imageContent = binding.contentImageHeader
        
        // Get new height and width for image
        val borderSize = resources.getDimension(R.dimen.activity_game_details_header_image_border_size)
        val height: Int = (imageContent.height - borderSize.toInt())
        val width: Int = (imageContent.width - borderSize.toInt())
        
        // Set image size
        val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(height, width)
        image.layoutParams = params
        image.requestLayout()
        
        // Set image to image header with glide. Also allows rounded image
        // TODO("Images customs")
        loadImageToHeader(uriProfileImage)
    }
    
    private fun loadImageToHeader(uriProfileImage: String?) {
        uriProfileImage?.let {
            // TODO("Uri")
            Glide.with(this).load(it).centerInside().circleCrop().into(binding.imageHeader)
            return
        }
        Glide.with(this).load(R.drawable.pingu).centerInside()
            .circleCrop().into(binding.imageHeader)
        return
    }
    
    override fun ui() {
        buildHeader(binding.headerToolbar, binding.headerTitle, R.string.profile, 35f)
        
        adapter = ProfileStatisticAdapter(this, profileStatistics).also { a ->
            binding.listProfileStats.let {
                val linearLayoutManager = GridLayoutManager(this, 2)
                it.setHasFixedSize(true)
                it.layoutManager = linearLayoutManager
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = a
            }
        }
        
        
        setStatusBarTransparent()
        setUpDialogs()
        getProfileStatistics()
    }
    
    override fun onClick() {
        
        binding.buttonEditName.setOnClickListener {
            editPseudo()
        }
    }
}