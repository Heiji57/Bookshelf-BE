package plain.bookshelf.domain.search.document.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.search.document.BookDocument;

@Repository
public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, String> {

}
