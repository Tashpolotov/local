    package com.example.register_presentation.ui.register_fragment


    import android.util.Log
    import android.view.View
    import android.widget.Toast
    import androidx.core.net.toUri
    import androidx.core.view.isVisible
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.lifecycleScope
    import androidx.navigation.NavDeepLinkRequest
    import androidx.navigation.fragment.findNavController
    import by.kirich1409.viewbindingdelegate.viewBinding
    import com.example.core_utils.Resource
    import com.example.core_utils.SharedPref
    import com.example.core_utils.SoundManager
    import com.example.core_utils.base.BaseFragment
    import com.example.presentation.R
    import com.example.presentation.databinding.FragmentRegisterBinding
    import com.example.register_presentation.ui.register_fragment.viewmodel.UserRegisterViewModel
    import dagger.hilt.android.AndroidEntryPoint
    import kotlinx.coroutines.flow.launchIn
    import kotlinx.coroutines.flow.onEach
    import kotlinx.coroutines.launch

    @AndroidEntryPoint
    class RegisterFragment : BaseFragment(R.layout.fragment_register) {

        private val binding by viewBinding(FragmentRegisterBinding::bind)
        private val viewModel by viewModels<UserRegisterViewModel>()
        private lateinit var soundManager: SoundManager
        private val name: String = "Передача данных любых"
        private lateinit var sharedPref: SharedPref


        override fun initialize() {
            soundManager = SoundManager.getInstance(requireContext())

            binding.btnNext.setOnClickListener {
                if (binding.etName.text.toString().isNotEmpty())
                    viewModel.loadUser(binding.etName.text.toString())
                else
                    binding.inputEt.error = "Укажите ваше имя"
                soundManager.playButton()

            }
        }

        override fun initSubscribers() {
            sharedPref = SharedPref(requireContext())
            val uri = "example.feature://home?name=${name}".toUri()
            val request = NavDeepLinkRequest.Builder
                .fromUri(uri)
                .build()

            lifecycleScope.launch {
                viewModel.registerUser.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            // Здесь можно обработать успешный результат
                            // например, сохранить токены в хранилище
                            resource.data?.let { tokens ->
                                viewModel.setAccessTokenInShared(tokens)
                            }
                            findNavController().navigate(request)
                        }
                        is Resource.Error -> {
                            // Здесь можно обработать ошибку
                            Log.e("RegisterFragment", "Registration error: ${resource.message}")
                        }
                        is Resource.Loading -> {
                            // Здесь можно обработать состояние загрузки
                        }

                        else -> {}
                    }
                }
            }
        }
    }