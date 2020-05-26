package br.com.caelum.mamute.tags;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Entity
@Table(name = "tags")
@Getter
@Setter
@RequiredArgsConstructor
public class TagEntity implements Serializable {

    private static final long serialVersionUID = -8052933124003489052L;
    @Column(nullable = false, updatable = false, unique = true)
    private final String tag;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Long usages;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TagEntity tagEntity = (TagEntity) o;

        return getTag().equals(tagEntity.getTag());
    }

}
