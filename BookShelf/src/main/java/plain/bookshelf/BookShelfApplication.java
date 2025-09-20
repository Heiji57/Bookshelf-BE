package plain.bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;

@SpringBootApplication
@EnableRedisRepositories(basePackageClasses = RefreshTokenRepository.class)
public class BookShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShelfApplication.class, args);
    }

}
