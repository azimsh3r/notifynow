package com.azimsh3r.notificationservice.service

import com.azimsh3r.notificationservice.dto.TemplateDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class TemplateService @Autowired constructor(val redisTemplate : RedisTemplate<String, TemplateDTO>) {

    fun cacheTemplate(template: TemplateDTO) {
        redisTemplate.opsForValue().set(template.id.toString(), template);
    }

    fun findTemplateById(id : Int) : TemplateDTO? {
        return redisTemplate.opsForValue().get(id.toString())
    }

    fun delete(key: String?) {
        redisTemplate.delete(key!!)
    }
}
