package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;
import java.util.List;

@SpringBootTest
public class UserServiceBusinessTest {

    @Autowired
    private SignupService signupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginMethodRepository loginMethodRepository;

    @Test
    public void testValidationEmailExists() {
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), "regisEmailExists@email.com");
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, "regisEmailExists@email.com", "1234567",
                user);
        user.add(loginMethod);

        final UserEntity userCreated = this.signupService.signup(user);
        Assertions.assertNotNull(userCreated.getId());

        final UserEntity userDuplicated = new UserEntity(SanitizedText.fromTrustedText("Regis"),
                "regisEmailExists@email.com");
        final LoginMethod loginMethodDuplicated = new LoginMethod(MethodType.BRUTAL, "regisEmailExists@email.com",
                "1234567", user);
        userDuplicated.add(loginMethodDuplicated);

        Assertions.assertThrows(ValidationException.class, () -> {
            this.signupService.signup(userDuplicated);
        });
    }

    @Test
    public void testValidationLoginMethodFilled() {
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), "regis@email.com");
        Assertions.assertThrows(ValidationException.class, () -> {
            final UserEntity userCreated = this.signupService.signup(user);
        }, "Login method is blank");
    }

    @Test
    public void testCreateUser() {
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), "regis@email.com");
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, "regis@email.com", "1234567", user);
        user.add(loginMethod);

        final UserEntity userCreated = this.signupService.signup(user);
        Assertions.assertNotNull(userCreated);
        Assertions.assertNotNull(userCreated.getId());
        this.assertUserInfoTestCreateUser(userCreated);
        this.assertLoginMethodInfoTestCreateUser(userCreated.getLoginMethods());
    }

    private void assertLoginMethodInfoTestCreateUser(final List<LoginMethod> loginMethods) {
        loginMethods.forEach(l -> {
            final LoginMethod loginMethodFound = this.loginMethodRepository.findById(l.getId()).get();
            Assertions.assertNotNull(loginMethodFound);
            Assertions.assertEquals(MethodType.BRUTAL, loginMethodFound.getType());
            Assertions.assertEquals("regis@email.com", loginMethodFound.getUser().getEmail());
        });
    }

    private void assertUserInfoTestCreateUser(final UserEntity userCreated) {
        final UserEntity userFound = this.userRepository.findById(userCreated.getId()).get();
        Assertions.assertNotNull(userFound);
        Assertions.assertEquals("Regis", userFound.getName());
        Assertions.assertEquals("regis@email.com", userFound.getEmail());
    }

}
