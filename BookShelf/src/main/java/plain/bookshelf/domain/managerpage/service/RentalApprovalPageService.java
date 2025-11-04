package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.managerpage.presentation.dto.response.RentalApprovalResponseDto;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;
import plain.bookshelf.global.dto.TotalPageResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalApprovalPageService {
    private final BookDetailRepository bookDetailRepository;

    public TotalPageResponseDto getRentalApprovalResponseDto(Pageable pageable) {

        Page<BookDetail> bookDetails = bookDetailRepository.findByRentalRequestStatusTrue(pageable);

        List<RentalApprovalResponseDto> rentalApprovalResponseDtos = bookDetails.stream()
                .map(RentalApprovalResponseDto::of)
                .toList();

        return TotalPageResponseDto.of(
                bookDetails.getTotalPages(),
                bookDetails.getTotalElements(),
                rentalApprovalResponseDtos
        );
    }
}
