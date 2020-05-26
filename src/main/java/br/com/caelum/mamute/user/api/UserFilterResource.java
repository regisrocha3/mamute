package br.com.caelum.mamute.user.api;

import br.com.caelum.mamute.user.domain.SearchCriteria;
import br.com.caelum.mamute.user.domain.UserEntity;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterResource implements SearchCriteria {
    private String name;
    private String email;
    private String sluggedName;

    @Override
    public Specification<UserEntity> toSpecification() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Predicate predicateName = this.createCriteriaAndFilterName(root, criteriaQuery, criteriaBuilder);
            final Predicate predicateEmail = this.createCriteriaAndFilterEmail(root, criteriaQuery, criteriaBuilder);
            final Predicate predicateSluggedName = this.createCriteriaAndFilterSluggedName(root, criteriaQuery,
                    criteriaBuilder);

            return criteriaBuilder
                    .and(List.of(predicateName, predicateEmail, predicateSluggedName).toArray(new Predicate[0]));
        };
    }

    private Predicate createCriteriaAndFilterName(final Root<UserEntity> root, final CriteriaQuery<?> criteriaQuery,
            final CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isNotBlank(this.name)) {
            return criteriaBuilder.equal(root.get("name"), this.name);
        }
        return null;
    }

    private Predicate createCriteriaAndFilterEmail(final Root<UserEntity> root, final CriteriaQuery<?> criteriaQuery,
            final CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isNotBlank(this.email)) {
            return criteriaBuilder.equal(root.get("email"), this.email);
        }
        return null;
    }

    private Predicate createCriteriaAndFilterSluggedName(final Root<UserEntity> root,
            final CriteriaQuery<?> criteriaQuery, final CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isNotBlank(this.sluggedName)) {
            return criteriaBuilder.equal(root.get("sluggedName"), this.sluggedName);
        }
        return null;
    }
}
