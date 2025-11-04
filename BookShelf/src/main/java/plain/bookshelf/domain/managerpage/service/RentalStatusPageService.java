package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.managerpage.presentation.dto.response.RentalStatusPageResponseDto;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.global.dto.TotalPageResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalStatusPageService {
    private final BookDetailRepository bookDetailRepository;

    public TotalPageResponseDto getRentalAllStatusPage(Pageable pageable) {
        Page<BookDetail> bookDetails = bookDetailRepository.findByRentalStatusTrue(pageable);

        List<RentalStatusPageResponseDto> rentalStatusPageResponseDtos = bookDetails.stream()
                .map(RentalStatusPageResponseDto::of)
                .toList();

        return TotalPageResponseDto.of(
                bookDetails.getTotalPages(),
                bookDetails.getTotalElements(),
                rentalStatusPageResponseDtos
        );
    }

    public List<RentalStatusPageResponseDto> getRentalNickNameStatusPage(String nickName) {
        List<BookDetail> bookDetails = bookDetailRepository.findByMemberNickName(nickName);

        return bookDetails.stream()
                .map(RentalStatusPageResponseDto::of)
                .toList();
    }

    public TotalPageResponseDto getRentalOverDueStatusPage(Pageable pageable) {
        Page<BookDetail> bookDetails = bookDetailRepository.findByOverDueStatusTrue(pageable);

        List<RentalStatusPageResponseDto> rentalStatusPageResponseDtos = bookDetails.stream()
                .map(RentalStatusPageResponseDto::of)
                .toList();

        return TotalPageResponseDto.of(
                bookDetails.getTotalPages(),
                bookDetails.getTotalElements(),
                rentalStatusPageResponseDtos
        );
    }
}
