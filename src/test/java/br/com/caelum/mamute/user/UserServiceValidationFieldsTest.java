package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import br.com.caelum.mamute.user.domain.UserEntity;
import br.com.caelum.mamute.user.domain.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    @Test
    public void testValidationPagingData() {
        final int DEFAULT_PAGE_SIZE = 20;
        final Page<UserEntity> response = this.userService.findUserByFilter(null, PageRequest.of(0, 20));
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getContent());
        Assertions.assertEquals(DEFAULT_PAGE_SIZE, response.getSize());
    }
}
