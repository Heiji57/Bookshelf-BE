package plain.bookshelf.global.config;

import brave.Tracing;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Filter;

@Component
@Order(1)   // 가장 먼저 실행되도록 설정
public class MdcLoggingFilter implements Filter {
    private final static String REQUEST_ID_KEY = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestId = Tracing.current().toString();
    }
}
