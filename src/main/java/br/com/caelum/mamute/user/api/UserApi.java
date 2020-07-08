package br.com.caelum.mamute.user.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@OpenAPIDefinition
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {

    @PostMapping
    ResponseEntity<Void> signup(@Valid @RequestBody UserSignupResource request);

    @GetMapping
    ResponseEntity<Page<UserResource>> find(UserFilterResource request, Pageable pageable);

    @DeleteMapping("/{email}")
    ResponseEntity<Void> remove(@PathVariable("email") String email);
}
