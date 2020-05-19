package br.com.caelum.mamute.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import static br.com.caelum.mamute.user.UserPersonalInfoValidator.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LoginMethod {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * password or user's password
     */
    private String token;

    @Size(max = EMAIL_MAX_LENGTH, message = EMAIL_LENGTH_MESSAGE)
    @Email(message = EMAIL_NOT_VALID)
    private String serviceEmail;

    @ManyToOne
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private MethodType type;

    public LoginMethod(MethodType type, String email, String password, UserEntity user) {
        this.type = type;
        this.serviceEmail = email;
        this.user = user;
        type.setPassword(this, password);

    }

    public void updateForgottenPassword(String password) {
        type.updateForgottenPassword(password, this);
    }

    void setToken(String token) {
        this.token = token;
    }

    public static LoginMethod brutalLogin(UserEntity user, String email, String password) {
        return new LoginMethod(MethodType.BRUTAL, email, password, user);
    }

    public static LoginMethod facebookLogin(UserEntity user, String email, String token) {
        return new LoginMethod(MethodType.FACEBOOK, email, token, user);
    }

    public static LoginMethod newLogin(UserEntity user, String email, String token, MethodType methodType) {
        return new LoginMethod(methodType, email, token, user);
    }

    public boolean isBrutal() {
        return type.equals(MethodType.BRUTAL);
    }

    public boolean isFacebook() {
        return type.equals(MethodType.FACEBOOK);
    }

    public String getToken() {
        return token;
    }
}
