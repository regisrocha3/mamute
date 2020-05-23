package br.com.caelum.mamute.user.domain.repository;

import br.com.caelum.mamute.user.domain.LoginMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMethodRepository extends CrudRepository<LoginMethod, Long> {
}
