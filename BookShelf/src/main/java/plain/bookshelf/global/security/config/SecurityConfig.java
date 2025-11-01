package plain.bookshelf.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import plain.bookshelf.global.security.service.TokenBlackListService;
import plain.bookshelf.global.security.jwt.JwtAuthenticationFilter;
import plain.bookshelf.global.security.jwt.JwtAuthenticationEntryPoint;
import plain.bookshelf.global.security.jwt.JwtTokenProvider;
import plain.bookshelf.global.security.jwt.JwtAccessDeniedHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlackListService tokenBlackListService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/signup", "/api/auth/login", "/api/email/send", "/api/email/verify", "/api/auth/find-id/**", "/api/auth/find-password/**").permitAll()
                        .requestMatchers("/api/manager/**", "/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers("/mypage/**").hasAnyRole("USER", "MANAGER",  "ADMIN")
                        .anyRequest().hasAnyRole("USER", "MANAGER", "ADMIN")
                )
                .logout(AbstractHttpConfigurer::disable
                )

        // JwtFilter 직접 등록
        .addFilterBefore(jwtAuthenticationFilter.jwtAuthenticationFilter(jwtTokenProvider, tokenBlackListService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
