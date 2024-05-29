package org.app.courses.models

import jakarta.persistence.*

@Table(name = "departments")
@Entity
open class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:Long?,

    @Column(name = "name", nullable = false)
    open var name: String,


    @OneToMany(mappedBy = "departments") //(mappedBy = "department", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    open var courses: MutableList<Course> = mutableListOf()

) {
    constructor() : this(null,"") {

    }
}