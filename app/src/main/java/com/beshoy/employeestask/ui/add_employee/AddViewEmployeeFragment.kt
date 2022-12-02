package com.beshoy.employeestask.ui.add_employee

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beshoy.employeestask.R
import com.beshoy.employeestask.data.entity.Skill
import com.beshoy.employeestask.databinding.FragmentAddViewEmployeeBinding
import com.freelapp.flowlifecycleobserver.collectWhileResumed
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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
                cameraContract.launch(imageUri)
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.cant_open_camera_no_permission,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val cameraContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { gotImage ->
            if (gotImage) {
                viewModel.setEmployeeImageUri(imageUri.toString())
            }
        }

    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            viewModel.setEmployeeImageUri(it.toString())
        }
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
        with(viewModel) {
            imageUri.collectWhileResumed(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    binding.profileImage.setImageResource(R.drawable.ic_image)
                } else {
                    binding.profileImage.setImageURI(it.toUri())
                    binding.profileImage.borderWidth = 2
                }
            }
            uiState.collectWhileResumed(viewLifecycleOwner, ::handleUiState)
            skills.collectWhileResumed(viewLifecycleOwner, ::fillSkillsAdapter)
            newSkill.collectWhileResumed(viewLifecycleOwner, ::createChip)
            isSaved.collectWhileResumed(viewLifecycleOwner) {
                if (it)
                    Toast.makeText(requireContext(), R.string.saved, Toast.LENGTH_SHORT).show()
            }
        }
        initListeners()
    }

    private fun initListeners() {
        binding.btnCamera.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) -> {
                    //launch camera
                    cameraContract.launch(imageUri)
                }
                else -> {
                    requestPermissionsLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }

        binding.btnGallery.setOnClickListener {
            galleryContract.launch("image/*")
        }
        binding.etSkills.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedSkill = adapterView.getItemAtPosition(position) as Skill
            viewModel.addSkill(selectedSkill)
        }
        binding.btnSave.setOnClickListener {
            viewModel.saveEmployee()
        }
    }

    private fun handleUiState(uiState: EmployeeUiState) {
        binding.textInputLayout2.isErrorEnabled = uiState.invalidMail

        if (uiState.invalidMail) {
            binding.textInputLayout2.error = getString(R.string.invalid_mail)
        } else {
            binding.textInputLayout2.error = null
        }

        if (uiState.messageRes != null) {
            Toast.makeText(requireContext(), uiState.messageRes, Toast.LENGTH_SHORT).show()
            viewModel.messageHandled()
        }
    }

    private fun fillSkillsAdapter(skills: List<Skill>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            skills
        )
        binding.etSkills.setAdapter(adapter)
    }

    private fun createImageUri(): Uri {
        val image = File(requireContext().filesDir, "${Calendar.getInstance().time}.png")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.beshoy.employeestask.fileProvider",
            image
        )
    }

    private fun createChip(skill: Skill) {
        val chip = Chip(requireContext()).apply {
            text = skill.skillName
            isCloseIconVisible = true
            setChipBackgroundColorResource(R.color.primary_color)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            setCloseIconTintResource(R.color.white)
            setOnClickListener {
                viewModel.removeSkill((it as Chip).text)
                (it.parent as ChipGroup).removeView(it)
            }
        }
        binding.cgTags.addView(chip)
    }

}