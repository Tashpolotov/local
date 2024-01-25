    package com.example.profile_presenter.ui.fragment

    import android.app.Activity
    import android.content.Context
    import android.content.Intent
    import android.content.pm.PackageManager
    import android.graphics.Bitmap
    import android.net.Uri
    import android.os.Bundle
    import android.provider.MediaStore
    import android.provider.Settings
    import android.util.Log
    import androidx.camera.core.CameraSelector
    import androidx.camera.core.ImageCapture
    import androidx.camera.core.ImageCaptureException
    import androidx.camera.core.Preview
    import androidx.camera.lifecycle.ProcessCameraProvider
    import androidx.core.app.ActivityCompat
    import androidx.core.content.ContextCompat
    import androidx.lifecycle.lifecycleScope
    import androidx.navigation.fragment.findNavController
    import by.kirich1409.viewbindingdelegate.viewBinding
    import com.example.core_utils.Constants
    import com.example.core_utils.SharedPref
    import com.example.core_utils.base.BaseFragment
    import com.example.core_utils.showToast
    import com.example.profile_presenter.R
    import com.example.profile_presenter.databinding.FragmentCameraBinding
    import com.google.android.material.dialog.MaterialAlertDialogBuilder
    import kotlinx.coroutines.launch
    import java.io.ByteArrayOutputStream
    import java.io.File
    import java.text.SimpleDateFormat
    import java.util.Locale

    class CameraFragment : BaseFragment(R.layout.fragment_camera) {

        private val binding by viewBinding(FragmentCameraBinding::bind)
        private var imageCapture: ImageCapture? = null
        private lateinit var outputDirectory: File
        private lateinit var sharedPref: SharedPref
        private val SELECT_PICTURE = 1
        private var isFrontCameraSelected = false
        private var isPermissionsGranted = false
        private var isReturningFromSettings = false
        private var shouldStartCamera = false

        override fun initialize() {

            if (!CameraPermissions()) {
                ActivityCompat.requestPermissions(
                    requireActivity(), Constants.CAMERA_REQUIRED_PERMISSIONS,
                    Constants.REQUEST_CODE_PERMISSIONS
                )
            }

            sharedPref = SharedPref(requireContext())
            outputDirectory = getOutputDirectory()


            binding.btnTakePhoto.setOnClickListener {
                takePhoto()
            }
            binding.btnSwitchCamera.setOnClickListener {
                isFrontCameraSelected = !isFrontCameraSelected
                startCamera()
            }
            showImageSourceSelectionDialog()
        }


        private fun getOutputDirectory(): File {
            val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let { mFile ->
                File(mFile, resources.getString(R.string.all_ball)).apply {
                    mkdirs()
                }
            }
            return mediaDir ?: requireActivity().filesDir
        }

        private fun takePhoto() {
            val imageCapture = imageCapture ?: return
            val photoFile = File(
                outputDirectory,
                SimpleDateFormat(
                    Constants.FILE_NAME_FROMAT,
                    Locale.getDefault()
                ).format(System.currentTimeMillis()) + ".jpg"
            )
            val outPutOption = ImageCapture.OutputFileOptions.Builder(photoFile)
                .build()

            imageCapture.takePicture(
                outPutOption, ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = Uri.fromFile(photoFile)
                        val msg = "Photo capture succeeded: $savedUri"
                        context?.showToast(msg)
                        Log.d(Constants.TAG, msg)
                        sharedPref.photoUri = savedUri

                        val bundle = Bundle().apply {
                            putParcelable("photoUri", savedUri)
                        }

                        findNavController().navigate(
                            R.id.action_cameraFragment_to_profileFragment,
                            bundle
                        )
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e(Constants.TAG, "error: ${exception.message}", exception)
                    }
                }
            )
        }

        private fun startGallery() {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, SELECT_PICTURE)
        }


        private fun startCamera() {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

            cameraProviderFuture.addListener({

                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also { mPreview ->
                        lifecycleScope.launch {
                            mPreview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                        }
                    }
                val cameraSelector = if (isFrontCameraSelected) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }

                imageCapture = ImageCapture.Builder()
                    .build()

                try {
                    cameraProvider.unbindAll()
                    lifecycleScope.launch {
                        cameraProvider.bindToLifecycle(
                            this@CameraFragment, cameraSelector, preview, imageCapture
                        )
                    }
                } catch (e: Exception) {
                    Log.d(Constants.TAG, "startCamera Failed:", e)
                }

            }, ContextCompat.getMainExecutor(requireContext()))
        }


        private fun handleImageFromGallery(uri: Uri) {
            if (uri != null) {
                val bitmap: Bitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                val tempUri: Uri = getImageUriFromBitmap(requireContext(), bitmap)
                sharedPref.photoUri = tempUri
                navigateToProfileFragmentWithUri(tempUri)
            } else {
                context?.showToast("Error: Selected image is null")
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            when (requestCode) {
                SELECT_PICTURE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        data?.data?.let { uri ->
                            sharedPref.photoUri = uri
                            handleImageFromGallery(uri)
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        findNavController().popBackStack()
                    }
                }

                // Удалим часть кода, относящуюся к Constants.REQUEST_CODE_PERMISSIONS

                // Добавим новый блок для обработки возвращения из настроек
                Constants.REQUEST_CODE_SETTINGS -> {
                    if (CameraPermissions()) {
                        startCamera()
                    } else if (GalleryPermissions()) {
                        startGallery()
                    } else {
                        showPermissionDeniedDialog()
                    }
                }
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            when (requestCode) {
                Constants.REQUEST_CODE_PERMISSIONS -> {
                    if (CameraPermissions()) {
                        startCamera()
                    } else {
                        Log.d(
                            Constants.TAG,
                            "Permissions not granted by the user for camera or gallery"
                        )
                        context?.showToast("Permissions not granted by the user for camera or gallery")
                        showPermissionDeniedDialog()
                        Log.d(Constants.TAG, "User denied permissions for camera or gallery")
                    }
                }

                Constants.REQUEST_CODE_PERMISSIONS_GALLERY -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startGallery()
                    } else {
                        Log.d(Constants.TAG, "Permissions not granted by the user for gallery")
                        context?.showToast("Permissions not granted by the user for gallery")
                        showPermissionDeniedDialog()
                        Log.d(Constants.TAG, "User denied permissions for gallery")
                    }
                }
            }
        }

        private fun showImageSourceSelectionDialog() {
            val items = arrayOf(getString(R.string.camera), getString(R.string.gallery))

            MaterialAlertDialogBuilder(requireContext())
                .setItems(items) { _, which ->
                    when (which) {
                        0 -> {
                            if (CameraPermissions()) {
                                startCamera()
                            } else {
                                showPermissionDeniedDialog()
                            }
                        }

                        1 -> {
                            if (GalleryPermissions()) {
                                startGallery()
                            } else {
                                showPermissionDeniedDialog()
                            }
                        }
                    }
                }
                .show()

        }

        private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path: String = MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                "File",
                null
            )
            return Uri.parse(path)
        }

        private fun navigateToProfileFragmentWithUri(uri: Uri) {
            val bundle = Bundle().apply {
                putParcelable("photoUri", uri)
            }

            findNavController().navigate(
                R.id.action_cameraFragment_to_profileFragment,
                bundle
            )
        }

        private fun openAppSettings() {
            Log.d(Constants.TAG, "Opening app settings")
            val currentActivity = activity
            if (currentActivity != null) {
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", currentActivity.packageName, null)
                }
                startActivityForResult(intent,  Constants.REQUEST_CODE_SETTINGS)
            }
        }

        private fun showPermissionDeniedDialog() {

            val currentActivity = activity
            if (currentActivity != null) {
                MaterialAlertDialogBuilder(currentActivity)
                    .setTitle("Разрешения отклонены")
                    .setMessage("Чтобы использовать камеру или галерею, необходимо предоставить разрешения.")
                    .setPositiveButton("Предоставить разрешения") { _, _ ->
                        openAppSettings()
                    }
                    .setNegativeButton("Отмена") { _, _ ->
                        findNavController().popBackStack()
                    }
                    .show()
            }
        }

        private fun CameraPermissions() =
            Constants.CAMERA_REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    requireContext(), it
                ) == PackageManager.PERMISSION_GRANTED
            }

        private fun GalleryPermissions() =
            Constants.GALLERY_REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    requireContext(), it
                ) == PackageManager.PERMISSION_GRANTED
            }

    }