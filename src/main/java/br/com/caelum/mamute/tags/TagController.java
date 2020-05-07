package br.com.caelum.mamute.tags;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/tags")
@RestController
@AllArgsConstructor
class TagController {

    private TagService service;

    @GetMapping
    public ResponseEntity search(@RequestParam(name = "q") String query) {
        return ResponseEntity.ok(this.service.searchTag(query).stream().map(this::fromEntity));
    }

    private TagResource fromEntity(TagEntity tagEntity) {
        var resource = new TagResource();
        resource.id = tagEntity.getId();
        resource.tag = tagEntity.getTag();
        resource.totalUsage = tagEntity.getUsages();

        return resource;
    }

    static class TagResource {

        public Integer id;

        public String tag;

        public Long totalUsage;

    }

}
