package plain.bookshelf.global.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcFilter implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 api 요청에 cors 규칙을 적용하겠다는 뜻 그냥 /** 쓰면 될 듯
                .allowedOrigins("http://localhost:8080", "http://localhost:9200", "http://13.125.65.240:8080", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                .allowCredentials(true) // JWT 인증 시 필수 Authorization 헤더가 (JWT)를 포함하거나 쿠키를 주고받을 수 있도록 허용

                // Preflight 요청 결과(OPTIONS 메서드를 통한 사전 통신)를 3600초(1시간) 동안 캐시하여, 매 요청마다 Preflight 통신을 하는 오버헤드를 줄임.
                // PUT, DELETE 요청이나 JWT를 포함하는 모든 요청 (대부분 Authorization 헤더를 사용하므로) Preflight 요청을 발생시킴
                .maxAge(3600);
    }
}
