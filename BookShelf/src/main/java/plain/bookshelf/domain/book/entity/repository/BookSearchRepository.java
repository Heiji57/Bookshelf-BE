package plain.bookshelf.domain.book.entity.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.book.presentation.dto.request.BookDocument;

@Repository
public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, String> {

}
