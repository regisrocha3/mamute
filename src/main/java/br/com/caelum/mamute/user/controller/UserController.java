package br.com.caelum.mamute.user.controller;

import br.com.caelum.mamute.user.api.UserSignupResource;
import br.com.caelum.mamute.user.api.UserApi;
import br.com.caelum.mamute.user.api.UserFilterResource;
import br.com.caelum.mamute.user.api.UserResource;
import br.com.caelum.mamute.user.domain.UserEntity;
import br.com.caelum.mamute.user.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity signup(final UserSignupResource request) {
        this.validate(request);
        final UserEntity response = this.userService.signup(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Page<UserResource>> find(final UserFilterResource request, final Pageable pageable) {
        final Page<UserEntity> userSearch = this.userService.findUserByFilter(request.toEntity(), pageable);
        final PageImpl<UserResource> response = new PageImpl<>(
                UserResource.convertResponseFromUserEntity(userSearch.getContent()), userSearch.getPageable(),
                userSearch.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    // @PreAuthorize("hasRole")
    public ResponseEntity<Void> remove(final String email) {
        this.userService.remove(email);
        return ResponseEntity.ok().build();
    }

    private void validate(final UserSignupResource request) {
        Assert.isTrue(request.getPassword().equals(request.getConfirmPassword()),
                "Password and confirmPassword are not equals");
    }
}
