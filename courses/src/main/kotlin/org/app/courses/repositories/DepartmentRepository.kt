package org.app.courses.repositories

import org.app.courses.models.Department
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface DepartmentRepository:JpaRepository<Department,Long>{

    fun findDepartmentById(id:Long):Department
}