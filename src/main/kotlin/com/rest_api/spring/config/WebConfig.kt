package com.rest_api.spring.config

import com.rest_api.spring.serilization.converter.YamlJackson2HttpMessageConverter
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    private final val MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml")

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        // Configuração para servir XML pelo query param
//        configurer.favorParameter(true)
//            .parameterName("mediaType")
//            .ignoreAcceptHeader(true)
//            .useRegisteredExtensionsOnly(false)
//            .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML)

        // Configuração para servir XML pelo hearder
        configurer.favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
            .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML)
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(YamlJackson2HttpMessageConverter())
    }
}