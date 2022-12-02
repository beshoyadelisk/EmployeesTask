package com.beshoy.employeestask.ui.add_employee

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmployeeViewModel(
    private val employeeRepository: EmployeeRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmployeeUiState())
    val uiState = _uiState.asStateFlow()

//    init {
//        stateHandle.get<Employee>("employee")?.let { employee ->
//            viewModelScope.launch {
//                _uiState.update { it.copy(employee = employee) }
//            }
//        }
//    }


}