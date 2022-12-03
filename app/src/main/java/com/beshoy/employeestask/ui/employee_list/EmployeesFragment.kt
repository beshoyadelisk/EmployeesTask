package com.beshoy.employeestask.ui.employee_list

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beshoy.employeestask.R
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.databinding.FragmentEmployeesBinding
import com.freelapp.flowlifecycleobserver.collectWhileResumed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesFragment : Fragment() {
    private lateinit var binding: FragmentEmployeesBinding
    private val viewModel: EmployeesListViewModel by viewModels()
    private lateinit var adapter: EmployeesAdapter
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEmployeesBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadEmployees()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EmployeesAdapter(::onEdit, ::onDelete)
        binding.rvEmployees.adapter = adapter
        checkStoragePermission()
        binding.btnAddEmployee.setOnClickListener {
            findNavController().navigate(R.id.action_employeesFragment_to_addViewEmployeeFragment)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.filterList(text)
                return false
            }

        })
        with(viewModel) {
            message.collectWhileResumed(viewLifecycleOwner, ::handleMessage)
            employees.collectWhileResumed(viewLifecycleOwner, ::handleEmployees)
        }
    }

    private fun checkStoragePermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
            }
            else -> {
                requestPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun handleEmployees(employees: List<EmployeeWithSkills>) {
        adapter.updateList(employees)
    }

    private fun handleMessage(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onEdit(employee: EmployeeWithSkills) {
        val action =
            EmployeesFragmentDirections.actionEmployeesFragmentToAddViewEmployeeFragment(employee)
        findNavController().navigate(action)
    }

    private fun onDelete(employee: EmployeeWithSkills) {
        viewModel.deleteEmployee(employee)
    }
}