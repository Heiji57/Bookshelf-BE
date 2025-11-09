package plain.bookshelf.domain.mainpage.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.mainpage.presentation.dto.request.BookDocument;
import plain.bookshelf.domain.book.entity.repository.BookSearchRepository;
import plain.bookshelf.domain.book.presentation.dto.response.BookSearchResultResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;
    private final BookSearchRepository bookSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public void indexBook() {
        List<Book> books = bookRepository.findAll();
        List<BookDocument> bookDocuments = books.stream()
                .map(BookDocument::of).toList();

        bookSearchRepository.saveAll(bookDocuments);
    }

    public BookSearchResultResponseDto searchBooks(String keyword, int page, int size) {
        List<Query> shouldQueries = List.of(
                Query.of(q -> q.match(m -> m.field("bookName").query(keyword).boost(5.0f))),

                Query.of(q -> q.term(m -> m.field("author").value(keyword).boost(8.0f))),

                Query.of(q -> q.match(m -> m.field("bookIntroduction").query(keyword).boost(1.0f))),

                Query.of(q -> q.match(m -> m.field("bookType").query(keyword).boost(3.0f))),

                Query.of(q -> q.term(m -> m.field("publisher").value(keyword).boost(8.0f)))
        );

        NativeQuery nativeQueryBuilder = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(shouldQueries) //Function<Builder>, Object<Query>
                                .minimumShouldMatch("1")
                        )
                )
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<BookDocument> searchHits = elasticsearchOperations.search(nativeQueryBuilder, BookDocument.class);

        List<BookDocument> results = searchHits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new BookSearchResultResponseDto(searchHits.getTotalHits(), results);
    }
}
