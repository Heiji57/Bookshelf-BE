package plain.bookshelf.global.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import plain.bookshelf.domain.member.entity.Member;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public String getUsername() {
        return member.getUserName();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 설정 로직 ("예: ROLE_USER")
        return Collections.singletonList(new SimpleGrantedAuthority(member.getAuthority().name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; } // 계정 만료를 다룸
    @Override
    public boolean isAccountNonLocked() { return true; } // 계정을 잠그는 것을 다룸
    @Override
    public boolean isCredentialsNonExpired() { return true; } // 자격 증명 만료를 다룸
    @Override
    public boolean isEnabled() { return true; } // 계정 비활성화를 다룸
}
