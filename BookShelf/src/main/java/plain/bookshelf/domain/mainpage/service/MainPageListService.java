package plain.bookshelf.domain.mainpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plain.bookshelf.domain.book.entity.Book;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.domain.book.entity.repository.BookRepository;
import plain.bookshelf.domain.mainpage.presentation.dto.response.BookPopularityListResponseDto;
import plain.bookshelf.domain.mainpage.presentation.dto.response.BookRecentListResponseDto;
import plain.bookshelf.domain.mainpage.presentation.dto.response.MainListResponseDto;
import plain.bookshelf.domain.member.entity.Member;
import plain.bookshelf.domain.member.service.GetCurrentMemberService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class  MainPageListService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    private final GetCurrentMemberService getCurrentMemberService;

    public MainListResponseDto responseRecentList() {
        Member currentMember = getCurrentMemberService.getCurrentMember();
        List<Book> popularEntities = bookDetailRepository.findAllOrderByCombinedCounts(PageRequest.of(0, 100));

        List<BookPopularityListResponseDto> popularList = popularEntities.stream()
                .map(BookPopularityListResponseDto::of)
                .toList();

        Sort sortRecent = Sort.by(Sort.Direction.DESC, "bookDate");

        Pageable pageableRecent = PageRequest.of(0, 100, sortRecent);

        List<Book> recentEntities = bookRepository.findAll(pageableRecent).getContent();

        List<BookRecentListResponseDto> recentList = recentEntities.stream()
                .map(BookRecentListResponseDto::of)
                .toList();

        return MainListResponseDto.of(
                currentMember.getId(),
                currentMember.getProfilePicture(),
                popularList,
                recentList
        );
    }
}
