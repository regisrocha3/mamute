package br.com.caelum.mamute.infrastructure.log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class InterceptorLogWhenExceptionThrow {
    @AfterThrowing(value = "execution(@br.com.caelum.mamute.infrastructure.log.LogWhenExceptionThrow * *(..))", throwing = "t")
    public void logExecutionTime(final JoinPoint joinPoint, final Throwable t) throws Throwable {
        final List<Method> listMethods = this.getMethods(joinPoint);
        listMethods.stream().filter(m -> m.getName().equals(joinPoint.getSignature().getName()))
                .map(m -> m.getAnnotation(LogWhenExceptionThrow.class))
                .map(LogWhenExceptionThrow::exceptions)
                .forEach(exx -> {
                    final List<Class<? extends Throwable>> listException = Arrays.asList(exx);
                    listException.forEach(lEx -> log.error("Error on execution {} , "
                            + "throwing error: {} ", joinPoint.getSignature(), t));
                });
    }

    /**
     * Obtem os metodos a serem interceptados
     *
     * @param joinPoint - JoinPoint
     *
     * @return List<Method>
     */
    private List<Method> getMethods(final JoinPoint joinPoint) {
        if (joinPoint.getSignature() != null && joinPoint.getSignature().getDeclaringType() != null) {
            return Arrays.asList(joinPoint.getSignature().getDeclaringType().getMethods());
        }

        return Collections.emptyList();
    }
}
