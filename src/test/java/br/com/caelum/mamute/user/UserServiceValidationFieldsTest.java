package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@SpringBootTest
public class UserServiceValidationFieldsTest {

    @Autowired
    private SignupService signupService;

    @Test
    public void testAllEmptyFields() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            signupService.signup(new UserEntity(SanitizedText.fromTrustedText(""), ""));
        });
    }

    @Test
    public void testEmailAndPasswordEmpty() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            signupService.signup(new UserEntity(SanitizedText.fromTrustedText("Regis"), ""));
        });
    }

    @Test
    public void testLoginMethodEmpty() {
        Assertions.assertThrows(ValidationException.class, () -> {
            signupService.signup(new UserEntity(SanitizedText.fromTrustedText("Regis"), "regis@email.com"));
        });
    }
}
