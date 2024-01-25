package com.example.core_utils.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core_utils.Resource
import com.example.core_utils.showToast
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

abstract class BaseFragment(
    @LayoutRes layoutRes: Int,
) : Fragment(layoutRes) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequests()
        setupSubscribers()
        setupListeners()
        initSubscribers()
    }

    open fun initialize() {}

    open fun setupRequests() {}

    open fun setupSubscribers() {}

    open fun setupListeners() {}
    open fun initSubscribers() {}

    /**
     * for @
     */

    /**
     * For what
     *
     * @param state FSf
     * @param onSuccess fs
     */
    protected fun <T> StateFlow<Resource<T>>.collectUIState(
        state: ((Resource<T>) -> Unit)? = null,
        onSuccess: (data: T) -> Unit,
    ) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectUIState.collect {
                    state?.invoke(it)
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Error -> {
                            context?.showToast(it.message)
                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            it.data?.let { it1 -> onSuccess(it1) }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}