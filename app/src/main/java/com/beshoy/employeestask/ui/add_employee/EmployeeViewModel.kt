package com.beshoy.employeestask.ui.add_employee

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beshoy.employeestask.R
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.data.entity.Skill
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import com.beshoy.employeestask.domain.repository.SkillRepository
import com.beshoy.employeestask.util.Resource
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
    private val currentEmployee: EmployeeWithSkills? = stateHandle["EmpWithSkills"]
    private val _uiState = MutableStateFlow(EmployeeUiState())
    val uiState = _uiState.asStateFlow()
    private val _imageUri = MutableStateFlow<Bitmap?>(null)
    val imageUri = _imageUri.asSharedFlow()
    val fullName = MutableStateFlow("")
    val email = MutableStateFlow("")
    private val _skills = MutableStateFlow(emptyList<Skill>())
    val skills = _skills.asStateFlow()
    private val _addedSkills = mutableListOf<Skill>()
    private val _newSkill = MutableSharedFlow<Skill>()
    val newSkill = _newSkill.asSharedFlow()
    private val _isSaved = MutableSharedFlow<Boolean>()
    val isSaved = _isSaved.asSharedFlow()
    val isValid = combine(fullName, email) { name, mail ->
        return@combine if (name.isNotEmpty()) {
            if (mail.isNotEmpty() && mail.isValidEmail().not()) {
                viewModelScope.launch {
                    _uiState.update { it.copy(invalidMail = true) }
                }
                false
            } else {
                viewModelScope.launch {
                    _uiState.update { it.copy(invalidMail = false) }
                }
                true
            }
        } else {
            false
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        loadSkills()

    }

    private fun loadDataFromEmployee(employeeWithSkills: EmployeeWithSkills) {
        viewModelScope.launch {
            employeeWithSkills.skills.forEach {
                _newSkill.emit(it)
            }
            fullName.emit(employeeWithSkills.employee.fullName)
            email.emit(employeeWithSkills.employee.email ?: "")
            _addedSkills.addAll(employeeWithSkills.skills)
            _imageUri.emit(employeeWithSkills.employee.photo ?: return@launch)

        }
    }

    private fun loadSkills() {
        viewModelScope.launch(dispatcher) {
            skillRepository.getSkills().collect { resource ->
                when (resource) {
                    is Resource.Error -> handleError(resource.message)
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val list = resource.data ?: emptyList()
                        _skills.emit(list)
                        currentEmployee?.let {
                            loadDataFromEmployee(it)
                        }
                    }
                }
            }
        }
    }

    private fun handleError(@StringRes message: Int?) {
        _uiState.update { it.copy(messageRes = message) }
    }

    fun setEmployeeImageUri(bitmap: Bitmap) {
        viewModelScope.launch {
            _imageUri.emit(bitmap)
        }
    }

    fun messageHandled() {
        _uiState.update { it.copy(messageRes = null) }
    }

    fun addSkill(selectedSkill: Skill) {
        if (_addedSkills.contains(selectedSkill)) {
            _uiState.update { it.copy(messageRes = R.string.skill_already_added) }
        } else {
            _addedSkills.add(selectedSkill)
            viewModelScope.launch {
                _newSkill.emit(selectedSkill)
            }
        }
    }

    fun removeSkill(skillName: CharSequence) {
        val index = _addedSkills.indexOfFirst { it.skillName == skillName }
        if (index != -1)
            _addedSkills.removeAt(index)
    }

    fun saveEmployee() {
        if (currentEmployee != null) {
            currentEmployee.employee.fullName = fullName.value
            currentEmployee.employee.email = email.value
            currentEmployee.employee.photo = _imageUri.value
            currentEmployee.skills = _addedSkills
            updateEmployee(currentEmployee)
        } else {
            val employee = Employee(fullName.value, email.value, _imageUri.value)
            val employeeWithSkills = EmployeeWithSkills(employee, _addedSkills)
            viewModelScope.launch(dispatcher) {
                employeeRepository.insertEmployeeWithSkill(employeeWithSkills).collect { resource ->
                    when (resource) {
                        is Resource.Error -> handleError(resource.message)
                        is Resource.Loading -> Unit
                        is Resource.Success -> {
                            _isSaved.emit(true)
                        }
                    }
                }
            }
        }
    }

    private fun updateEmployee(employee: EmployeeWithSkills) {
        viewModelScope.launch(dispatcher) {
            employeeRepository.updateEmployee(employee).collect { resource ->
                when (resource) {
                    is Resource.Error -> handleError(resource.message)
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        _isSaved.emit(true)
                    }
                }
            }
        }
    }


//    init {
//        stateHandle.get<Employee>("employee")?.let { employee ->
//            viewModelScope.launch {
//                _uiState.update { it.copy(employee = employee) }
//            }
//        }
//    }


}