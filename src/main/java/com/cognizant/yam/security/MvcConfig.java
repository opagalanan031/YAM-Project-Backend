package com.cognizant.yam.security;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	public void addResoruceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("member-files", registry);
	}
	
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**")
			.allowedOrigins("http://localhost:4200")
			.allowedMethods("*")
			.maxAge(3600L)
			.allowedHeaders("*")
			.exposedHeaders("Authorization")
			.allowCredentials(true);
	}
	
	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);
		String uploadPath = uploadDir.toFile().getAbsolutePath();
		
		if(dirName.startsWith("../")) dirName = dirName.replace("../", "");
		
		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
	}
}
