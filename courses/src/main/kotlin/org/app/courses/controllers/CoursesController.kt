package org.app.courses.controllers

import jakarta.validation.Valid
import org.app.courses.dto.CourseDto
import org.app.courses.models.Course
import org.app.courses.services.CourseService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("api/v1/courses")
@CrossOrigin("http://localhost:3000")
@PreAuthorize("hasAnyAuthority('courses_role')")
class CoursesController(
 private val courseService: CourseService
) {

    @GetMapping("")
    fun getAllCourses():MutableList<Course>{
        return courseService.getAllCourses()
    }


    @PostMapping("save")
    fun saveCourse(@RequestBody @Valid courseDto: CourseDto):ResponseEntity<String>{
        courseService.saveCourse(courseDto)
        return ResponseEntity.ok("Course Created")
    }
}