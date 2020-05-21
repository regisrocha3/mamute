package br.com.caelum.mamute.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(" FROM UserEntity u WHERE u.email=?1")
    UserEntity findUserByEmail(String email);

}
