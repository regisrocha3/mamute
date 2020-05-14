package br.com.caelum.mamute.user;

import br.com.caelum.mamute.infrastructure.log.LogException;
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

    @LogException(exceptions = {Exception.class})
    public UserEntity signup(UserEntity user) {
        final UserEntity userPersisted = this.userRepository.save(user);
        this.loginMethodRepository.save(userPersisted.getLoginMethods().get(FIRST_INDEX));
        return userPersisted;
    }
}
