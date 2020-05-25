package br.com.caelum.mamute.user.domain;

import org.springframework.data.jpa.domain.Specification;

public interface SearchCriteria {

    Specification<UserEntity> toSpecification();

}
