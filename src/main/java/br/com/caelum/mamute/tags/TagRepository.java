package br.com.caelum.mamute.tags;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TagRepository extends CrudRepository<TagEntity, Integer> {

    List<TagEntity> findByTagLike(String tag);

    List<TagEntity> findByTagIn(List<String> tags);

}
