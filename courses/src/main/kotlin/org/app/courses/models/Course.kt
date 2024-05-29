package org.app.courses.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Table(name = "courses")
@Entity
open class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:Long?,

    @Column(name = "name", nullable = false)
    open var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id",nullable = false)

    @JsonIgnore
    open var departments: Department


) {
    constructor() : this(null,"",Department())
}

//    @JsonBackReference