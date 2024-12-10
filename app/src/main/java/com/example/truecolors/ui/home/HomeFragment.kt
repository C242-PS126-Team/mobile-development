package com.example.truecolors.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.truecolors.data.remote.retrofit.ApiConfig
import com.example.truecolors.data.remote.repository.DetectionRepository
import com.example.truecolors.databinding.FragmentHomeBinding
import com.example.truecolors.ui.factory.ViewModelFactory
import com.example.truecolors.ui.result.ScanResultActivity
import com.example.truecolors.utils.getImageUri
import com.example.truecolors.utils.uriToFile
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private lateinit var homeViewModel: HomeViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentImageUri?.let { uri ->
            outState.putParcelable("current_image_uri", uri)
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val apiService = ApiConfig.getApiService()
        val homeRepository = DetectionRepository(apiService)
        val factory = ViewModelFactory(homeRepository)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        homeViewModel.currentImageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.ivResult.setImageURI(it)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            showToast(errorMessage)
        }

        homeViewModel.predictions.observe(viewLifecycleOwner) { response ->
            val result = response.predictions
            val intent = Intent(requireContext(), ScanResultActivity::class.java).apply {
                putExtra("predictions", Gson().toJson(result))
            }
            startActivity(intent)
            requireActivity().finish()
        }

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnUpload.setOnClickListener { startGallery() }
        binding.btnAnalyzeWrapper.setOnClickListener { analyzeImage() }
    }

    private fun analyzeImage() {
        homeViewModel.currentImageUri.value?.let { uri ->
            val imageFile = uriToFile(uri, requireContext())
            homeViewModel.analyzeImage(imageFile)
        } ?: showToast("No image selected!")
    }

    private fun showImage() {
        homeViewModel.currentImageUri.value?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivResult.setImageURI(it)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbScan.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startCamera() {
        if (allPermissionsGranted()) {
            currentImageUri = getImageUri(requireContext())
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri)
            }
            launcherIntentCamera.launch(intent)
            Log.d("AddStoryActivity", "Camera permission granted")
        } else {
            requestPermissions()
            Log.d("AddStoryActivity", "Camera permission not granted")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            homeViewModel.setImageUri(currentImageUri)
            showImage()
        } else {
            homeViewModel.setImageUri(null)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            homeViewModel.setImageUri(it)
            showImage()
        } ?: Log.d("Photo Picker", "No media selected")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}