package plain.bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import plain.bookshelf.global.security.entity.repository.RefreshTokenRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "plain.bookshelf.domain")
@EnableElasticsearchRepositories(basePackages = "plain.bookshelf.domain.search")
@EnableRedisRepositories(basePackageClasses = RefreshTokenRepository.class)
public class BookShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookShelfApplication.class, args);
    }

}
