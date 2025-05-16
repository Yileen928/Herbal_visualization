package cn.herbal.visualization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://www.herbvis.cn") // 允许前端域名
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // 必须显式允许所有 headers
                .exposedHeaders("Content-Length", "Content-Range") // 暴露这些头
                .allowCredentials(true)
                .maxAge(3600);
    }
}
