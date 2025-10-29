package plain.bookshelf.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                .csrf(csrf -> csrf.disable())

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/email/send", "/email/verify").permitAll()
                        .requestMatchers("/api/manager/**", "/manager/**").hasRole("ROLE_MANAGER")
                        .requestMatchers("/mypage/**").hasAnyRole("ROLE_USER", "ROLE_MANAGER",  "ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(AbstractHttpConfigurer::disable
                )

        // JwtFilter 직접 등록
        .addFilterBefore(jwtAuthenticationFilter.jwtAuthenticationFilter(jwtTokenProvider, tokenBlackListService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
