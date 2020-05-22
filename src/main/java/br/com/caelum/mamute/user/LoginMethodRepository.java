package br.com.caelum.mamute.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMethodRepository extends CrudRepository<LoginMethod, Long> {
}
