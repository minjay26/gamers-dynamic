package org.minjay.gamers.dynamic.resource.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("org.minjay.dynamic.rest.controller")
public class RestMvcConfig implements WebMvcConfigurer {


}
