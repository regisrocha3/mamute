package br.com.caelum.mamute.questions;

import br.com.caelum.mamute.tags.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/v1/questions")
@AllArgsConstructor
class QuestionController {

    private QuestionService service;
    private TagService tagService;

    @PostMapping
    public ResponseEntity createQuestion(Principal principal, @RequestBody @Valid CreateQuestionResource resource) {
        var question = resource.toEntity(principal.getName());
        question.setTags(this.tagService.findOrCreate(Arrays.asList(resource.tags)));
        this.service.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    static class CreateQuestionResource {
        @NotNull
        @Min(15)
        public String title;
        @NotNull
        @Min(50)
        public String description;
        public String[] tags;

        QuestionEntity toEntity(final String author) {
            var question = new QuestionEntity(author, this.title, new Date());
            question.setDescritpion(this.description);
            return question;
        }
    }
}