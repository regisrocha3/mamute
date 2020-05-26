package br.com.caelum.mamute.user.domain.repository;

import br.com.caelum.mamute.user.api.UserFilterResource;
import br.com.caelum.mamute.user.domain.UserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, JpaSpecificationExecutor {

    @Query(" FROM UserEntity u WHERE u.email=?1")
    UserEntity findUserByEmail(String email);

    default public Specification<UserEntity> toSpecification(final UserFilterResource filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            this.createCriteriaAndFilterName(root, criteriaQuery, criteriaBuilder, predicates, filter.getName());
            this.createCriteriaAndFilterEmail(root, criteriaQuery, criteriaBuilder, predicates, filter.getEmail());
            this.createCriteriaAndFilterSluggedName(root, criteriaQuery, criteriaBuilder, predicates, filter.getSluggedName());

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private List<Predicate> createCriteriaAndFilterName(final Root<UserEntity> root,
                                                        final CriteriaQuery<?> criteriaQuery,
                                                        final CriteriaBuilder criteriaBuilder,
                                                        final List<Predicate> predicates, final String name) {
        if (StringUtils.isNotBlank(name)) {
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }
        return predicates;
    }

    private List<Predicate> createCriteriaAndFilterEmail(final Root<UserEntity> root,
                                                         final CriteriaQuery<?> criteriaQuery,
                                                         final CriteriaBuilder criteriaBuilder,
                                                         final List<Predicate> predicates, final String email) {
        if (StringUtils.isNotBlank(email)) {
            predicates.add(criteriaBuilder.equal(root.get("email"), email));
        }
        return predicates;
    }

    private List<Predicate> createCriteriaAndFilterSluggedName(final Root<UserEntity> root,
                                                               final CriteriaQuery<?> criteriaQuery,
                                                               final CriteriaBuilder criteriaBuilder,
                                                               final List<Predicate> predicates, final String sluggedName) {
        if (StringUtils.isNotBlank(sluggedName)) {
            predicates.add(criteriaBuilder.equal(root.get("sluggedName"), sluggedName));
        }
        return predicates;
    }

}
