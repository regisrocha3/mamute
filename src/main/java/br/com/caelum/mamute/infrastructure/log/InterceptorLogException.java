package br.com.caelum.mamute.infrastructure.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Aspect
@Component
public class InterceptorLogException {

    @AfterThrowing(value = "execution(@br.com.caelum.mamute.infrastructure.log.LogException * *(..))", throwing = "t")
    public void logExecutionTime(final JoinPoint joinPoint, final Throwable t) throws Throwable {
        final List<Method> listMethods = this.getMethods(joinPoint);
        listMethods.stream().filter(m -> m.getName().equals(joinPoint.getSignature().getName()))
                .map(m -> m.getAnnotation(LogException.class)).map(LogException::exceptions).forEach(exx -> {
                    final List<Class<? extends Throwable>> listException = Arrays.asList(exx);
                    listException.forEach(lEx -> log.error("Error on execution {} , " + "throwing error: {} ",
                            joinPoint.getSignature(), t));
                });
    }

    /**
     * Obtem os metodos a serem interceptados
     *
     * @param joinPoint
     *            - JoinPoint
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
