package plain.bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "plain.bookshelf.domain")
@EnableElasticsearchRepositories(basePackages = "plain.bookshelf.domain.book")
@EnableRedisRepositories(basePackageClasses = RefreshTokenRepository.class)
@EnableScheduling
public class BookShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShelfApplication.class, args);
    }

}
