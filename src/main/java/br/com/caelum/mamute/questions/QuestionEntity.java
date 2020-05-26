package br.com.caelum.mamute.questions;

import br.com.caelum.mamute.tags.TagEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
public class QuestionEntity implements Serializable {

    private static final long serialVersionUID = -1233940751093452642L;

    @Column(nullable = false)
    private final String author;

    @Column(nullable = false, updatable = false)
    private final String title;

    @Column(nullable = false, updatable = false)
    private final Date createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String descritpion;

    @Column(insertable = false)
    private Date updatedAt;

    @OneToMany
    private List<TagEntity> tags;

}
