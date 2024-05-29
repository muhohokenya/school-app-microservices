package org.app.courses.repositories

import org.app.courses.models.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CourseRepository:JpaRepository<Course,Long>