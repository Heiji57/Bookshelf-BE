package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.managerpage.presentation.dto.response.RentalStatusPageResponseDto;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalStatusPageService {
    private final BookDetailRepository bookDetailRepository;

    public List<RentalStatusPageResponseDto> getRentalAllStatusPage(Pageable pageable) {
        List<BookDetail> bookDetails = bookDetailRepository.findByRentalStatusTrue(pageable);

        return bookDetails.stream()
                .map(RentalStatusPageResponseDto::of)
                .toList();
    }

    public List<RentalStatusPageResponseDto> getRentalNickNameStatusPage(String nickName) {
        List<BookDetail> bookDetails = bookDetailRepository.findByMemberNickName(nickName);

        return bookDetails.stream()
                .map(RentalStatusPageResponseDto::of)
                .toList();
    }

    public List<RentalStatusPageResponseDto> getRentalOverDueStatusPage(Pageable pageable) {
        List<BookDetail> bookDetails = bookDetailRepository.findByOverDueStatusTrue(pageable);

        return bookDetails.stream()
                .map(RentalStatusPageResponseDto::of)
                .toList();
    }
}
