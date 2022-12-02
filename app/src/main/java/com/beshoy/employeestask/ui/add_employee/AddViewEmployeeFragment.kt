package com.beshoy.employeestask.ui.add_employee

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beshoy.employeestask.R
import com.beshoy.employeestask.databinding.FragmentAddViewEmployeeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class AddViewEmployeeFragment : Fragment() {
    private lateinit var binding: FragmentAddViewEmployeeBinding
    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var imageUri: Uri
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                contract.launch(imageUri)
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.cant_open_camera_no_permission,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        binding.profileImage.setImageURI(imageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddViewEmployeeBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUri = createImageUri()
        binding.profileImage.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) -> {
                    //launch camera
                    contract.launch(imageUri)
                }
                else -> {
                    requestPermissionsLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }
    }

    private fun createImageUri(): Uri {
        val image = File(requireContext().filesDir, "${Calendar.getInstance().time}.png")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.beshoy.employeestask.fileProvider",
            image
        )
    }

}