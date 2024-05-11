package org.app.users.services

import org.app.users.dto.UserDto
import org.app.users.models.Students
import org.app.users.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val usersRepository:UserRepository
) {

    fun saveUser(userDto: UserDto){
        val user = Students(
            id = null,
            name = userDto.name,
            admNo = userDto.admNo,
        )
        usersRepository.save(user)
    }


    fun getUsers():MutableList<Students>{
        return usersRepository.findAll()
    }

    fun getUserByAdmNo(admNo:String):Students{
        return usersRepository.findByAdmNo(admNo)
    }

    fun studentExistsByAdmNo(admNo: String):Boolean{
        return usersRepository.existsByAdmNo(admNo)
    }

}