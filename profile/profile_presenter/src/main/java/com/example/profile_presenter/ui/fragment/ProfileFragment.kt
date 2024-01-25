package com.example.profile_presenter.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core_utils.LocalLangManager
import com.example.core_utils.SharedPref
import com.example.core_utils.SoundManager
import com.example.core_utils.addTextChange
import com.example.core_utils.base.BaseFragment
import com.example.core_utils.setupColorStateList
import com.example.profile_presenter.R
import com.example.profile_presenter.databinding.CustomDialogNameProfileBinding
import com.example.profile_presenter.databinding.CustomDialogProfileBinding
import com.example.profile_presenter.databinding.FragmentProfileBinding
import com.example.profile_presenter.ui.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogUser: Dialog
    private lateinit var soundManager: SoundManager
    private lateinit var sharedPref: SharedPref

    override fun initSubscribers() {
        sharedPref = SharedPref(requireContext())

        val accessToken = sharedPref.accessToken
        if (accessToken != null) {
            Log.d("Token", "Access Token: $accessToken")
        } else {
            Log.d("Token", "Access Token is not available.")
        }

        viewModel.profile.collectUIState (
            state = {
                binding.progressBar.isVisible = true
            },
            onSuccess = {
                binding.progressBar.isVisible = false
                binding.tvNameUser.text = it.user.toString()
                binding.tvAllBall.text = it.points.toString()
                binding.tvBallTest.text = it.completedTest.toString()
                binding.tvRankUser.text = it.rank
                binding.tvNextRank.text = it.rank
                binding.tvScore.text = it.points.toString()
                binding.tvLessonBall.text = it.completedLesson.toString()
            }
        )
        viewModel.loadProfile()
    }

    override fun initialize() {
        binding.imgCamera.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_cameraFragment)

        }

        soundManager = SoundManager.getInstance(requireContext())
        sharedPref = SharedPref(requireContext())

        val savedName = sharedPref.selectedName
        binding.tvNameUser.text = savedName ?: "Ваше имя"
        val selectedLanguage = sharedPref.selectedLanguage
        val isSoundEnable = sharedPref.isSoundEnabled

        val savedPhotoUriString = sharedPref.photoUri
        if (savedPhotoUriString != null) {
            val savedPhotoUri = Uri.parse(savedPhotoUriString.toString())
            binding.ivLogo.setImageURI(savedPhotoUri)
        }


        binding.imgPencil.setOnClickListener {
            showChangeNameDialog()
        }

        val bottomSheetCustom = layoutInflater.inflate(R.layout.custom_dialog_settin, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(bottomSheetCustom)


        val rButtonRu = bottomSheetCustom.findViewById<RadioButton>(R.id.radio_button_ru)
        val rButtonKg = bottomSheetCustom.findViewById<RadioButton>(R.id.radio_button_kg)
        val switch = bottomSheetCustom.findViewById<SwitchCompat>(R.id.switchcompat)
        val img_yes_sound = bottomSheetCustom.findViewById<ImageView>(R.id.img_yes_sound)
        val img_no_sound = bottomSheetCustom.findViewById<ImageView>(R.id.img_no_sound)

        if (isSoundEnable) {
            soundManager.enableSound()
            switch.isChecked = true
            img_yes_sound.visibility = View.INVISIBLE
            img_no_sound.visibility = View.VISIBLE
        } else {
            soundManager.disableSound()
            switch.isChecked = false
            img_no_sound.visibility = View.INVISIBLE
            img_yes_sound.visibility = View.VISIBLE
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                soundManager.enableSound()
                sharedPref.isSoundEnabled = true
                img_yes_sound.visibility = View.INVISIBLE
                img_no_sound.visibility = View.VISIBLE
            } else {
                soundManager.disableSound()
                sharedPref.isSoundEnabled = false
                img_no_sound.visibility = View.INVISIBLE
                img_yes_sound.visibility = View.VISIBLE
            }
        }

        binding.imgSetting.setOnClickListener {
            soundManager.playButtonSetting()
            dialog.show()
        }

        rButtonRu.setupColorStateList()
        rButtonKg.setupColorStateList()

        if (sharedPref.selectedLanguage.isNullOrEmpty()) {
            rButtonRu.isChecked = true
            sharedPref.selectedLanguage = "ru"
            setLocale("ru")
        }


        when (selectedLanguage) {
            "ru" -> rButtonRu.isChecked = true
            "ky" -> rButtonKg.isChecked = true
        }

        rButtonRu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.selectedLanguage = "ru"
                setLocale("ru")
            }
        }

        rButtonKg.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.selectedLanguage = "ky"
                setLocale("ky")
            }
        }

        binding.tvDeleteAccount.setOnClickListener {
            showConfirmationDialog("Вы действительно хотите удалить аккаунт?")
        }
    }

    private fun showConfirmationDialog(questionText: String) {
        val dialogDelete = CustomDialogProfileBinding.inflate(layoutInflater)
        dialogUser = Dialog(requireContext())

        dialogDelete.tvText.text = questionText

        dialogUser.setContentView(dialogDelete.root)
        dialogUser.setCancelable(true)
        dialogUser.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogUser.show()

        dialogDelete.btnYes.setOnClickListener {
            // тоже вьюмодел для удаления и для выхода из аккаунта проработать логику надо
            dialogUser.dismiss()
        }

        dialogDelete.btnNo.setOnClickListener {
            dialogUser.dismiss()
        }
    }

    private fun showChangeNameDialog() {
        val dialogChangeNameBinding = CustomDialogNameProfileBinding.inflate(layoutInflater)
        val newNameEt = dialogChangeNameBinding.etName
        val btnYes = dialogChangeNameBinding.btnYes
        val btnNo = dialogChangeNameBinding.btnNo
        val btnFalse = dialogChangeNameBinding.btnYesFalse

        val dialogChangeName = Dialog(requireContext())
        dialogChangeName.setContentView(dialogChangeNameBinding.root)
        dialogChangeName.setCancelable(true)
        dialogChangeName.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Обработчик события для кнопки "Отмена"
        btnNo.setOnClickListener {
            dialogChangeName.dismiss()
        }

        // Обработчик события для кнопки "Нет"
        btnFalse.setOnClickListener {
            dialogChangeName.dismiss()
        }

        dialogChangeNameBinding.etName.addTextChange(btnYes, btnFalse)

        // Обработчик события для кнопки "ОК"
        btnYes.setOnClickListener {
            val newNameText = newNameEt.text.toString().trim()

            if (newNameText.isNotEmpty()) {
                binding.tvNameUser.text = newNameText
                sharedPref.selectedName = newNameText
                dialogChangeName.dismiss()
            } else {
                // Если новое имя пусто, вы можете вывести сообщение об ошибке или выполнить другие действия
                Toast.makeText(requireContext(), "Введите новое имя", Toast.LENGTH_SHORT).show()
            }
        }

        dialogChangeName.show()
    }


    private fun setLocale(languageCode: String) {
        LocalLangManager.setLocale(requireContext(), languageCode)
        requireActivity().recreate()
    }
}