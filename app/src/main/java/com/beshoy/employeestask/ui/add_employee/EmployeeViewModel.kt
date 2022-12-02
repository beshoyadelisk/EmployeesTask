package com.beshoy.employeestask.ui.add_employee

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import com.beshoy.employeestask.domain.repository.SkillRepository
import com.beshoy.employeestask.util.TextUtil.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val skillRepository: SkillRepository,
    private val dispatcher: CoroutineDispatcher,
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmployeeUiState())
    val uiState = _uiState.asStateFlow()

    val fullName = MutableStateFlow("")
    val email = MutableStateFlow("")

    val isValid = combine(fullName, email) { name, mail ->
        return@combine if (name.isNotEmpty()) {
            if (mail.isNotEmpty() && mail.isValidEmail().not()) {
                viewModelScope.launch {
                    _uiState.update { it.copy(invalidMail = true) }
                }
                false
            } else {
                true
            }
        } else {
            viewModelScope.launch {
                _uiState.update { it.copy(invalidName = true) }
            }
            false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


//    init {
//        stateHandle.get<Employee>("employee")?.let { employee ->
//            viewModelScope.launch {
//                _uiState.update { it.copy(employee = employee) }
//            }
//        }
//    }


}