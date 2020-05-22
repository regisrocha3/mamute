package br.com.caelum.mamute.user;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@OpenAPIDefinition
@RequestMapping(value = "/api/v1/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public interface SignupApi {

    @PostMapping
    ResponseEntity<Void> signup(@Valid @RequestBody SignupResource request);
}
