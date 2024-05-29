package org.app.courses.services

import org.app.courses.dto.CourseDto
import org.app.courses.models.Course
import org.app.courses.repositories.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val departmentService: DepartmentService,
){

    fun getAllCourses():MutableList<Course>{
        return courseRepository.findAll()
    }

    fun saveCourse(courseDto: CourseDto){

        val department = departmentService.getDepartmentById(courseDto.departmentId.toLong())

        println(department.name)
        val course = Course(
            null,
            courseDto.name,
            department
        )
        courseRepository.save(course)
    }
}