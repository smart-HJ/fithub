package com.fithub.smart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").addResourceLocations("C:/jaeryul/ssafit/ssafit/src/main/resources/static/image");
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").addResourceLocations("C:/HJR/ssafit/ssafit/src/main/resources/static/image");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").addResourceLocations("C:/Users/최정윤/jung_spring/ssafit/src/main/resources/static/image");
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*")
				.allowedMethods("POST", "GET", "PUT", "DELETE")
				.allowedOrigins("http://localhost:5173" ,"https://www.googleapis.com")
//				.allowCredentials(true)
				;
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 인터셉터 구현부
	}
}
