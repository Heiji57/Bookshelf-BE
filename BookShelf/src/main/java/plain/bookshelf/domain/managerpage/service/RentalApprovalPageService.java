package plain.bookshelf.domain.managerpage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.managerpage.presentation.dto.response.RentalApprovalResponseDto;
import plain.bookshelf.domain.book.entity.BookDetail;
import plain.bookshelf.domain.book.entity.repository.BookDetailRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalApprovalPageService {
    private final BookDetailRepository bookDetailRepository;

    public List<RentalApprovalResponseDto> getRentalApprovalResponseDto(Pageable pageable) {

        List<BookDetail> bookDetails = bookDetailRepository.findByRentalRequestStatusTrue(pageable);

        return bookDetails.stream()
                .map(RentalApprovalResponseDto::of)
                .toList();
    }
}
