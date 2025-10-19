package plain.bookshelf.domain.member.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import plain.bookshelf.domain.email.entity.Email;
import plain.bookshelf.domain.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberSignupResponseDto {

    private final Long userId;
    private final String userName;
    private final String nickName;
    private final String authority;
    private final List<EmailDto> emails;

    @Getter
    public static class EmailDto {
        private final Long emailId;
        private final String address;
        private final boolean verified;
        private final boolean delivered;

        @Builder
        public EmailDto(Long emailId, String address, boolean verified, boolean delivered) {
            this.emailId = emailId;
            this.address = address;
            this.verified = verified;
            this.delivered = delivered;
        }

        public static EmailDto fromEntity(Email email) {
            return EmailDto.builder()
                    .emailId(email.getId())
                    .address(email.getAddress())
                    .verified(email.isVerified())
                    .build();
        }
    }

    @Builder
    private MemberSignupResponseDto(Long userId, String userName, String nickName, String authority, List<EmailDto> emails) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.authority = authority;
        this.emails = emails;
    }

    public static MemberSignupResponseDto of(Member member) {
        List<EmailDto> emailDto = member.getEmails().stream()
                .map(EmailDto::fromEntity)
                .collect(Collectors.toList());

        return MemberSignupResponseDto.builder()
                .userId(member.getId())
                .userName(member.getUserName())
                .nickName(member.getNickName())
                .authority(member.getAuthority().name())
                .emails(emailDto)
                .build();
    }
}