package br.com.caelum.mamute.user.domain.service;

import br.com.caelum.mamute.user.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface UserService {

    UserEntity signup(UserEntity user);

    Page<UserEntity> findUserByFilter(UserEntity filter,
            @NotNull(message = "{user.errors.pageable.required}") Pageable pageable);

    void remove(String email);
}
