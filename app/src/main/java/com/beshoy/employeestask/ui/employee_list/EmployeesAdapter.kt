package com.beshoy.employeestask.ui.employee_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beshoy.employeestask.R
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.databinding.LayoutEmployeeItemBinding
import com.bumptech.glide.Glide

class EmployeesAdapter(
    private val onEdit: (EmployeeWithSkills) -> Unit,
    private val onDelete: (EmployeeWithSkills) -> Unit
) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    private var employees: List<EmployeeWithSkills> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutEmployeeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = employees.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(employees[position])

    fun updateList(employees: List<EmployeeWithSkills>) {
        this.employees = employees
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: LayoutEmployeeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var employeeWithSkills: EmployeeWithSkills
        fun bind(employeeWithSkills: EmployeeWithSkills) {
            this.employeeWithSkills = employeeWithSkills
            binding.apply {
                tvName.text = employeeWithSkills.employee.fullName
                tvMail.text = employeeWithSkills.employee.email
                btnEdit.setOnClickListener {
                    onEdit(employeeWithSkills)
                }
                btnDelete.setOnClickListener {
                    onDelete(employeeWithSkills)
                }

                Glide.with(root.context)
                    .load(employeeWithSkills.employee.photo ?: return@apply)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(profileImage)
            }
        }
    }


}