package com.beshoy.employeestask.ui.employee_list

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import com.beshoy.employeestask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesListViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _employees = MutableStateFlow(emptyList<EmployeeWithSkills>())
    val employees = _employees.asStateFlow()
    private val _message = MutableSharedFlow<Int>()
    val message = _message.asSharedFlow()
    val isLoading = MutableStateFlow(false)

    init {
        loadEmployees()
    }

    private fun loadEmployees() {
        viewModelScope.launch(dispatcher) {
            employeeRepository.getEmployeesWithSKills().collect { resource ->
                when (resource) {
                    is Resource.Error -> handleError(resource.message ?: return@collect)
                    is Resource.Loading -> isLoading.emit(resource.isLoading)
                    is Resource.Success -> {
                        _employees.emit(resource.data ?: emptyList())
                    }
                }
            }
        }
    }

    private fun handleError(@StringRes message: Int) {
        viewModelScope.launch {
            _message.emit(message)
        }
    }

    fun deleteEmployee(employee: EmployeeWithSkills) {
        viewModelScope.launch(dispatcher) {
            employeeRepository.deleteEmployee(employee.employee)
            loadEmployees()
        }
    }
}