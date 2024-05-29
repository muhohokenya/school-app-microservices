package org.app.courses.security


import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component

@Component
class JwtAuthConverter : Converter<Jwt, AbstractAuthenticationToken> {

//    @Value("\${spring.application.foo}")
    private val serviceName = "students-service"

    private val claims = "resource_access"


    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val resourceAccess = jwt.claims[claims] as? Map<*, *> ?: emptyMap<Any, Any>()
        val roles = (resourceAccess[serviceName] as? Map<*, *>)?.get("roles") as? List<*> ?: emptyList<Any>()
        val grantedAuthorities = roles
            .mapNotNull { roleName -> roleName.toString().let { SimpleGrantedAuthority(it) } }

        return JwtAuthenticationToken(jwt, grantedAuthorities)
    }
}
