package plain.bookshelf.domain.mainpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.mainpage.presentation.dto.response.BookPopularityListResponseDto;
import plain.bookshelf.domain.mainpage.presentation.dto.response.BookRecentListResponseDto;
import plain.bookshelf.domain.mainpage.presentation.dto.response.MainListResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainPageListService {

    private final BookRepository bookRepository;

    public MainListResponseDto responseRecentList() {
        Sort sortPopularity = Sort.by(Sort.Direction.DESC, "rentalCount");

        Pageable pageablePopularity = PageRequest.of(0, 100, sortPopularity);

        List<Book> popularityEntities = bookRepository.findAll(pageablePopularity).getContent();

        List<BookPopularityListResponseDto> popularList = popularityEntities.stream()
                .map(BookPopularityListResponseDto::of)
                .toList();

        Sort sortRecent = Sort.by(Sort.Direction.DESC, "bookDate");

        Pageable pageableRecent = PageRequest.of(0, 100, sortRecent);

        List<Book> recentEntities = bookRepository.findAll(pageableRecent).getContent();

        List<BookRecentListResponseDto> recentList = recentEntities.stream()
                .map(BookRecentListResponseDto::of)
                .toList();

        return MainListResponseDto.of(
                popularList,
                recentList
        );
    }
}
