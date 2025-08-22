package plain.bookshelf.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import plain.bookshelf.domain.user.entity.User;
import plain.bookshelf.domain.user.entity.repository.UserRepository;
import plain.bookshelf.domain.user.presentation.dto.UserSignupRequestDto;
import plain.bookshelf.email.entity.Email;
import plain.bookshelf.global.exception.ExistUserException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserSignupRequestDto userSignupRequestDto) {
        if (userRepository.existsByUsername(userSignupRequestDto.getUsername())) {
            throw new ExistUserException(userSignupRequestDto.getUsername());
        }

        User user = User.builder()
                .userName(userSignupRequestDto.getUsername())
                .password(userSignupRequestDto.getPassword())
                .userRole("USER")
                .build();

        userSignupRequestDto.getEmails().forEach(addr -> {
            Email email = Email.builder()
                    .address(addr)
                    .verified(false)
                    .delivered(false)
                    .build();

            user.addEmail(email);
        });
        return userRepository.save(user);
    }
}
