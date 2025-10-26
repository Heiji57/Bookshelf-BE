package plain.bookshelf.domain.search.document.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import plain.bookshelf.domain.search.document.BookDocument;

import java.util.List;

@EnableElasticsearchRepositories
public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, String> {
    List<BookDocument> findByAuthor(String author);
}
