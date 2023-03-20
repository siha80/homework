package io.siha.homework.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@Configuration
@Aspect
public class LoggingConfig {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.repeat("-", 39) + ">");

    @Pointcut("within(io.siha.homework.*) " +
            "&& (" +
            "within(@org.springframework.web.bind.annotation.RestController *) " +
            "|| within(@org.springframework.web.bind.annotation.RestControllerAdvice *) " +
            ")" +
            "&& !execution(* io.siha.homework.controller.HealthCheckController.*(..) )"
    )
    public void restApis() {
    }

    @Pointcut("within(io.siha.homework..*) " +
            "&& (within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Component *) )"
    )
    public void services() {
    }

    @Around(value = "services()")
    public Object loggingServices(ProceedingJoinPoint jp) throws Throwable {
        final String endPhrase = "[CALLED - SERVICE]" + " %s [REQUEST] : %s | [RETURN] : %s";
        return loggingAop(jp, endPhrase);
    }

    @Around(value = "restApis()")
    public Object loggingRestApis(ProceedingJoinPoint jp) throws Throwable {
        final String uri = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attr -> ((ServletRequestAttributes) attr).getRequest().getRequestURI())
                .orElse("NOT-HTTP-REQUEST");

        final String endPhrase = "[CALLED - API]" + String.format("[URI: %s]", uri) + " %s [REQUEST] : %s | [RETURN] : %s";
        return loggingAop(jp, endPhrase);
    }

    private Object loggingAop(ProceedingJoinPoint jp, final String endPhrase) throws Throwable {
        final Object[] args = jp.getArgs();

        final String method = jp.getSignature().toShortString();
        final String argString = args.length > 0 ? Arrays.stream(jp.getArgs()).map(String::valueOf)
                .collect(joining(", ")) : "NONE";

        final Object ret = jp.proceed(args);

        final String retString = Optional.ofNullable(ret).map(String::valueOf).orElse("VOID");
        logger.info(String.format(endPhrase, method, argString, retString));

        return ret;
    }

    @AfterThrowing(value = "restApis() || services()", throwing = "e")
    public void loggingWebThrowing(JoinPoint jp, Throwable e) {
        final String log = String.format("[EXCEPT] %s | [PARAM] : %s", jp.getSignature().toShortString(),
                jp.getArgs().length > 0 ? Arrays.stream(jp.getArgs()).map(String::valueOf)
                        .collect(joining(", ")) : "NONE");
        logger.warn(log);
        final String exceptLog = String.format("[EXCEPT] %s | [TRACE] : %s", jp.getSignature().toShortString(), e != null ? e : "NULL");
        logger.error(exceptLog, e);
    }

}