package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import br.com.caelum.mamute.user.domain.LoginMethod;
import br.com.caelum.mamute.user.domain.MethodType;
import br.com.caelum.mamute.user.domain.UserEntity;
import br.com.caelum.mamute.user.domain.repository.LoginMethodRepository;
import br.com.caelum.mamute.user.domain.repository.UserRepository;
import br.com.caelum.mamute.user.domain.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

@SpringBootTest
public class UserServiceBusinessTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginMethodRepository loginMethodRepository;

    @Test
    public void testValidationEmailExists() {
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), "regisEmailExistss@email.com");
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, "regisEmailExistss@email.com", "1234567",
                user);
        user.add(loginMethod);

        final UserEntity userCreated = this.userService.signup(user);
        Assertions.assertNotNull(userCreated.getId());

        final UserEntity userDuplicated = new UserEntity(SanitizedText.fromTrustedText("Regis"),
                "regisEmailExistss@email.com");
        final LoginMethod loginMethodDuplicated = new LoginMethod(MethodType.BRUTAL, "regisEmailExistss@email.com",
                "1234567", user);
        userDuplicated.add(loginMethodDuplicated);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            this.userService.signup(userDuplicated);
        });
    }

    @Test
    public void testValidationLoginMethodFilled() {
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), "regis@email.com");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final UserEntity userCreated = this.userService.signup(user);
        }, "Login method is blank");
    }

    @Test
    public void testCreateUser() {
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), "regis@email.com");
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, "regis@email.com", "1234567", user);
        user.add(loginMethod);

        final UserEntity userCreated = this.userService.signup(user);
        Assertions.assertNotNull(userCreated);
        Assertions.assertNotNull(userCreated.getId());
        this.assertUserInfoTestCreateUser(userCreated);
        this.assertLoginMethodInfoTestCreateUser(userCreated.getLoginMethods());
    }

    @Test
    public void testFindUserByName() {
        final String EMAIL = "regis1@email.com";
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis"), EMAIL);
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, EMAIL, "1234567", user);
        user.add(loginMethod);

        final UserEntity userCreated = this.userService.signup(user);
        Assertions.assertNotNull(userCreated);
        Assertions.assertNotNull(userCreated.getId());

        final UserEntity filter = new UserEntity();
        filter.setName(SanitizedText.fromTrustedText("Regis"));
        Page<UserEntity> userByFilter = this.userService.findUserByFilter(filter, null);
        Assertions.assertNotNull(userByFilter);
        Assertions.assertEquals("Regis", userByFilter.getContent().get(0).getName());
        Assertions.assertEquals(EMAIL, userByFilter.getContent().get(0).getEmail());
        Assertions.assertEquals(1, userByFilter.getTotalElements());
    }

    @Test
    public void testFindUserByLikeName() {
        final String EMAIL = "regis2@email.com";
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("XXXXXX yyasd UUUUUU"), EMAIL);
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, EMAIL, "1234567", user);
        user.add(loginMethod);

        final UserEntity userCreated = this.userService.signup(user);
        Assertions.assertNotNull(userCreated);
        Assertions.assertNotNull(userCreated.getId());

        final UserEntity filter = new UserEntity();
        filter.setName(SanitizedText.fromTrustedText("yyasd"));
        Page<UserEntity> userByFilter = this.userService.findUserByFilter(filter, null);
        Assertions.assertNotNull(userByFilter);
        Assertions.assertNotNull(userByFilter.getContent());
        Assertions.assertEquals("XXXXXX yyasd UUUUUU", userByFilter.getContent().get(0).getName());
        Assertions.assertEquals(EMAIL, userByFilter.getContent().get(0).getEmail());
        Assertions.assertEquals(1, userByFilter.getTotalElements());
    }

    @Test
    public void testFindUserByEmail() {
        final String EMAIL = "regis3@email.com";
        final UserEntity user = new UserEntity(SanitizedText.fromTrustedText("Regis 3"), EMAIL);
        final LoginMethod loginMethod = new LoginMethod(MethodType.BRUTAL, EMAIL, "1234567", user);
        user.add(loginMethod);

        final UserEntity userCreated = this.userService.signup(user);
        Assertions.assertNotNull(userCreated);
        Assertions.assertNotNull(userCreated.getId());

        final UserEntity filter = new UserEntity();
        filter.setEmail(EMAIL);
        Page<UserEntity> userByFilter = this.userService.findUserByFilter(filter, null);
        Assertions.assertNotNull(userByFilter);
        Assertions.assertEquals("Regis 3", userByFilter.getContent().get(0).getName());
        Assertions.assertEquals(EMAIL, userByFilter.getContent().get(0).getEmail());
        Assertions.assertEquals(1, userByFilter.getTotalElements());
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
