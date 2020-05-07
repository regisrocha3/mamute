package br.com.caelum.mamute.tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
@AllArgsConstructor
public class TagService {

    private TagRepository repository;

    public List<TagEntity> searchTag(String tag) {
        return repository.findByTagLike("%" + tag + "%");
    }

    public List<TagEntity> findOrCreate(List<String> tags) {
        var tagsList = this.repository.findByTagIn(tags);
        if (tagsList.size() != tags.size()) {
            var tagsToCreate = tags.stream().filter(tag -> tagsList.contains(new TagEntity(tag))).map(TagEntity::new)
                    .collect(Collectors.toList());
            tagsList.addAll(createTag(tagsToCreate));
        }

        return tagsList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TagEntity> createTag(List<TagEntity> tag) {
        return (List<TagEntity>) this.repository.saveAll(tag);
    }

}