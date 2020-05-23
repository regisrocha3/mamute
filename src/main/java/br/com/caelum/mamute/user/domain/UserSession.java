package br.com.caelum.mamute.user.domain;

import br.com.caelum.mamute.user.domain.UserEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserSession {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Column(unique = true)
    private String sessionKey;

    @Getter
    @ManyToOne
    private UserEntity user;

    private final LocalDateTime createdAt = LocalDateTime.now();

    public UserSession(UserEntity user, String sessionKey) {
        this.user = user;
        this.sessionKey = sessionKey;
    }
}
