package org.app.courses.services

import org.app.courses.dto.DepartmentDto
import org.app.courses.models.Department
import org.app.courses.repositories.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(
    private val departmentRepository: DepartmentRepository
) {


    fun getAllDepartments():MutableList<Department>{
        return departmentRepository.findAll()
    }


    fun saveDepartment(departmentDto: DepartmentDto){
        val department = Department(
            null,
            departmentDto.name
        )
        departmentRepository.save(department)
    }


    fun getDepartmentById(id:Long):Department {
        return departmentRepository.findDepartmentById(id)
    }
}