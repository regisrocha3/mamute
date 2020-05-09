package br.com.caelum.mamute.common;

import br.com.caelum.mamute.questions.QuestionEntity;
import br.com.caelum.mamute.user.UserEntity;
import br.com.caelum.mamute.vote.Vote;

import java.io.Serializable;
import java.util.List;

public interface Votable {
	void substitute(Vote previous, Vote current);
	void remove(Vote previous);
	UserEntity getAuthor();
	Serializable getId();
	long getVoteCount();
    Class<? extends Votable> getType();
	QuestionEntity getQuestion();
	List<Vote> getVotes();
}
