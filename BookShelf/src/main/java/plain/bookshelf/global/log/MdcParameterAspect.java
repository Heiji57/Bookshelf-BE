package plain.bookshelf.global.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class MdcParameterAspect {

    @Around("execution(public * *(..)) && within(@org.springframework.web.bind.annotation.RestController *)")
    public Object setMdcContext(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();

        Map<String, String> mdcContext = new HashMap<>();

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if(parameters[i].isAnnotationPresent(LoggableParam.class)) {
                String key = parameters[i].getAnnotation(LoggableParam.class).value();
                String value = args[i] != null ? args[i].toString() : "";

                MDC.put(key, value);
                mdcContext.put(key, value);
            }
        }

        try {
            return joinPoint.proceed();
        } finally {
            mdcContext.keySet().forEach(MDC::remove); // clear는 다른 값들도 지울 수 있기에 remove로 만든 값들만 삭제
        }
    }
}
