package br.com.caelum.mamute.user.domain.service;

import br.com.caelum.mamute.user.domain.SearchCriteria;
import br.com.caelum.mamute.user.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity signup(UserEntity user);

    Page<UserEntity> findUserByFilter(SearchCriteria request, Pageable pageable);
}
