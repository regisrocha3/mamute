package br.com.caelum.mamute.common;

import br.com.caelum.mamute.user.UserEntity;
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

    default void isEmpty(final Collection<?> collection) throws ValidationException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ValidationException("Object are empty");
        }
    }

    default void isEmpty(final Collection<?> collection, final String message) throws ValidationException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ValidationException(message);
        }
    }

    default void isNull(final Object object, final String message) throws ValidationException {
        if (object == null) {
            throw new ValidationException(message);
        }
    }

    default void isNotNull(final Object object, final String message) throws ValidationException {
        if (object != null) {
            throw new ValidationException(message);
        }
    }
}
