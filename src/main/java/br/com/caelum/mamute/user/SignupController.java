package br.com.caelum.mamute.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SignupController implements SignupApi {

    @Autowired
    private SignupService signupService;

    @Override
    public ResponseEntity signup(final SignupResource request) {
        final UserEntity response = this.signupService.signup(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}