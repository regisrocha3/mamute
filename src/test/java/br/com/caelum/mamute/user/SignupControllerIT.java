package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.api.data.ErrorDetails;
import br.com.caelum.mamute.user.api.UserSignupResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupControllerIT {

    private static final String ENDPOINT_SIGNUP = "http://localhost:%d/api/v1/signup";

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSignupWithNameNotFilled() throws JsonProcessingException {
        final int NUMBER_VALIDATION_MESSAGES = 1;
        final UserSignupResource request = new UserSignupResource();
        request.setEmail("regis@email.com");
        request.setPassword("1234567890");
        request.setConfirmPassword("1234567890");

        final ResponseEntity<ErrorDetails[]> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, ErrorDetails[].class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        Assertions.assertEquals(NUMBER_VALIDATION_MESSAGES, response.getBody().length);
        Stream.of(response.getBody()).forEach(e -> {
            Assertions.assertEquals("name: Name can't be empty", e.getMessage());
        });
    }

    @Test
    public void testSignupWithEmailNotFilled() throws JsonProcessingException {
        final int NUMBER_VALIDATION_MESSAGES = 1;
        final UserSignupResource request = new UserSignupResource();
        request.setName("Regis");
        request.setPassword("1234567890");
        request.setConfirmPassword("1234567890");

        final ResponseEntity<ErrorDetails[]> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, ErrorDetails[].class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        Assertions.assertEquals(NUMBER_VALIDATION_MESSAGES, response.getBody().length);
        Stream.of(response.getBody()).forEach(e -> {
            Assertions.assertEquals("email: Email can't be empty", e.getMessage());
        });
    }

    @Test
    public void testSignupWithPasswordNotFilled() throws JsonProcessingException {
        final int NUMBER_VALIDATION_MESSAGES = 1;
        final UserSignupResource request = new UserSignupResource();
        request.setName("Regis");
        request.setEmail("regis@email.com");
        request.setConfirmPassword("1234567890");

        final ResponseEntity<ErrorDetails[]> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, ErrorDetails[].class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        Assertions.assertEquals(NUMBER_VALIDATION_MESSAGES, response.getBody().length);
        Stream.of(response.getBody()).forEach(e -> {
            Assertions.assertEquals("password: Password can't be empty", e.getMessage());
        });
    }

    @Test
    public void testSignupWithConfirPasswordNotFilled() throws JsonProcessingException {
        final int NUMBER_VALIDATION_MESSAGES = 1;
        final UserSignupResource request = new UserSignupResource();
        request.setName("Regis");
        request.setEmail("regis@email.com");
        request.setPassword("1234567890");

        final ResponseEntity<ErrorDetails[]> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, ErrorDetails[].class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        Assertions.assertEquals(NUMBER_VALIDATION_MESSAGES, response.getBody().length);
        Stream.of(response.getBody()).forEach(e -> {
            Assertions.assertEquals("confirmPassword: Confirmation password can't be empty", e.getMessage());
        });
    }

    @Test
    public void testSignupWithPasswordLessThan6Characters() throws JsonProcessingException {
        final int NUMBER_VALIDATION_MESSAGES = 1;
        final UserSignupResource request = new UserSignupResource();
        request.setName("Regis");
        request.setEmail("regis@email.com");
        request.setPassword("12345");
        request.setConfirmPassword("12345");

        final ResponseEntity<ErrorDetails[]> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, ErrorDetails[].class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        Assertions.assertEquals(NUMBER_VALIDATION_MESSAGES, response.getBody().length);
        Stream.of(response.getBody()).forEach(e -> {
            Assertions.assertEquals("password: Your password must have between 6 and 100 characters.", e.getMessage());
        });
    }

    @Test
    public void testSignupWithPasswordMoreThan100Characters() throws JsonProcessingException {
        final int NUMBER_VALIDATION_MESSAGES = 1;
        final UserSignupResource request = new UserSignupResource();
        request.setName("Regis");
        request.setEmail("regis@email.com");
        request.setPassword(RandomStringUtils.randomAlphabetic(120));
        request.setConfirmPassword(request.getPassword());

        final ResponseEntity<ErrorDetails[]> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, ErrorDetails[].class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        Assertions.assertEquals(NUMBER_VALIDATION_MESSAGES, response.getBody().length);
        Stream.of(response.getBody()).forEach(e -> {
            Assertions.assertEquals("password: Your password must have between 6 and 100 characters.", e.getMessage());
        });
    }

    @Test
    public void testSuccessSignup() {
        final UserSignupResource request = new UserSignupResource();
        request.setName("regis");
        request.setEmail("regis@email.com");
        request.setPassword("1234567890");
        request.setConfirmPassword("1234567890");
        final ResponseEntity<UserSignupResource> response = this.restTemplate
                .postForEntity(String.format(ENDPOINT_SIGNUP, this.port), request, UserSignupResource.class);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    }
}
