package org.app.users.services

import org.app.users.dto.UserDto
import org.app.users.models.Students
import org.app.users.repositories.UserRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Service
class UserService(
    private val usersRepository: UserRepository,
    val kafkaService: KafkaService,
    private val kafkaTemplate: KafkaTemplate<Any, Any>
) {

    private val topic = "students-topic"

    fun saveUser(userDto: UserDto){
        val user = Students(
            id = userDto.id,
            name = userDto.name,
            admNo = userDto.admNo,
        )
        val objectMapper = jacksonObjectMapper()
        val userJson = objectMapper.writeValueAsString(userDto)

        kafkaTemplate.send(topic,userJson)
//        usersRepository.save(user)
    }


    fun getUsersByIds(ids: List<Long>): MutableList<Students> {
        return usersRepository.findAllById(ids)
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

    fun getUserById(id: String): Optional<Students> {
        return usersRepository.findById(id.toLong())
    }

}