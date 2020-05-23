package br.com.caelum.mamute.user.domain.service;

import br.com.caelum.mamute.common.BusinessValidation;
import br.com.caelum.mamute.infrastructure.log.LogException;
import br.com.caelum.mamute.user.domain.LoginMethod;
import br.com.caelum.mamute.user.domain.UserEntity;
import br.com.caelum.mamute.user.domain.repository.LoginMethodRepository;
import br.com.caelum.mamute.user.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SignupService {

    private static final int FIRST_INDEX = 0;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginMethodRepository loginMethodRepository;

    @LogException(exceptions = { Exception.class })
    public UserEntity signup(final UserEntity user) {
        this.validate(user);
        final UserEntity userPersisted = this.userRepository.save(user);
        final LoginMethod loginMethodCreated = this.loginMethodRepository
                .save(userPersisted.getLoginMethods().get(FIRST_INDEX));

        return userPersisted;
    }

    private void validate(final UserEntity user) {
        final BusinessValidation validLoginMethod = (loginMethod) -> {
        };
        validLoginMethod.isEmpty(user.getLoginMethods(), "Login method is blank");

        final UserEntity duplicateUserForEmail = this.userRepository.findUserByEmail(user.getEmail());
        validLoginMethod.isNotNull(duplicateUserForEmail, "Email Address is Already Registered");
    }
}
