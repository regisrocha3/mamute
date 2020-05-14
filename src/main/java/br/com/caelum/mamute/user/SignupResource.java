package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;

import javax.validation.constraints.NotEmpty;

public class SignupResource {

    @NotEmpty(message = "{signup.message.validation.name}")
    public String name;
    //@NotEmpty(message = "{signup.message.validation.email}")
    public String email;
    //@NotEmpty(message = "{signup.message.validation.password}")
    public String password;
    //@NotEmpty(message = "{signup.message.validation.confirm.password}")
    public String confirmPassword;

    public UserEntity toEntity() {
        final UserEntity newUser = new UserEntity(SanitizedText.fromTrustedText(this.name), this.email);
        newUser.add(LoginMethod.brutalLogin(newUser, this.email, this.password));
        return newUser;
    }
}
