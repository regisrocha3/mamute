package br.com.caelum.mamute.common;

import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ValidationException;
import java.util.Collection;

@FunctionalInterface
public interface BusinessValidation {

    void validate(Object t) throws ValidationException;

    default void equals(Object t, Object u) throws ValidationException {
        if (!t.equals(u)) {
            throw new ValidationException("Objects are not equals");
        }
    }

    default void isEmpty(Collection<?> collection) throws ValidationException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ValidationException("Object are empty");
        }
    }
}
