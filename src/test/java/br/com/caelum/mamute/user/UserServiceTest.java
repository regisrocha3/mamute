package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private SignupService signupService;

    @Test
    public void test() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            signupService.signup(new UserEntity(SanitizedText.fromTrustedText(""), "", ""));
        });
    }
}
