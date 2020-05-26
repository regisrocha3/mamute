package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import br.com.caelum.mamute.user.domain.UserEntity;
import br.com.caelum.mamute.user.domain.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;

@SpringBootTest
public class UserServiceValidationFieldsTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAllEmptyFields() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(new UserEntity(SanitizedText.fromTrustedText(""), ""));
        });
    }

    @Test
    public void testEmailAndPasswordEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(new UserEntity(SanitizedText.fromTrustedText("Regis"), ""));
        });
    }

    @Test
    public void testLoginMethodEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(new UserEntity(SanitizedText.fromTrustedText("Regis"), "regis@email.com"));
        });
    }
}
