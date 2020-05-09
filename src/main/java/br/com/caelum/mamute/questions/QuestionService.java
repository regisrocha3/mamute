package br.com.caelum.mamute.questions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS)
public class QuestionService {

    private QuestionRepository repository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void createQuestion(QuestionEntity entity) {
        this.repository.save(entity);
    }

}
