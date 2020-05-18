package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class SignupResource {

    @NotBlank(message = "{signup.message.validation.name}")
    @NotEmpty(message = "{signup.message.validation.name}")
    public String name;

    @NotBlank(message = "{signup.message.validation.email}")
    @NotEmpty(message = "{signup.message.validation.email}")
    public String email;

    @NotBlank(message = "{signup.message.validation.password}")
    @NotEmpty(message = "{signup.message.validation.password}")
    public String password;

    @NotBlank(message = "{signup.message.validation.confirm.password}")
    @NotEmpty(message = "{signup.message.validation.confirm.password}")
    public String confirmPassword;

    public UserEntity toEntity() {
        final UserEntity newUser = new UserEntity(SanitizedText.fromTrustedText(this.name), this.email);
        newUser.add(LoginMethod.brutalLogin(newUser, this.email, this.password));
        return newUser;
    }
}
