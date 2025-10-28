package plain.bookshelf.global.security.entity.repository;

import org.springframework.data.repository.CrudRepository;
import plain.bookshelf.global.security.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
