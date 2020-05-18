package br.com.caelum.mamute.user;

import br.com.caelum.mamute.common.BusinessValidation;
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
        this.validate(request);
        final UserEntity response = this.signupService.signup(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void validate(final SignupResource request) {
        final BusinessValidation validationConfirmationPassword = (password) -> {};
        validationConfirmationPassword.equals(request.password, request.confirmPassword);

        // TODO: Verificar se usuario ja foi cadastrado
    }
}