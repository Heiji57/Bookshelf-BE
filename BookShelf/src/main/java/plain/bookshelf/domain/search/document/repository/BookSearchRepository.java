package plain.bookshelf.domain.search.document.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import plain.bookshelf.domain.search.document.BookDocument;

import java.util.List;

@Repository
public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, String> {
    List<BookDocument> findByAuthor(String author);
}
