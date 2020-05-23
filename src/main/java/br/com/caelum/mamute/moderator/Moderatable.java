package br.com.caelum.mamute.moderator;

import br.com.caelum.mamute.common.Identifiable;
import br.com.caelum.mamute.common.Information;
import br.com.caelum.mamute.questions.QuestionEntity;
import br.com.caelum.mamute.user.domain.UserEntity;
import status.UpdateStatus;

public abstract class Moderatable implements Identifiable {

    protected abstract Information getInformation();

    protected abstract void updateApproved(Information approved);

    protected abstract void addHistory(Information information);

    public abstract UserEntity getAuthor();

    public abstract String getTypeName();

    public abstract boolean hasPendingEdits();

    public abstract QuestionEntity getQuestion();

    public final UpdateStatus approve(Information approved) {
        if (!canBeUptadedWith(approved)) {
            throw new IllegalArgumentException(
                    "an Answer can only approve an AnswerInformation and a Question can only approve a QuestionInformation");
        }
        updateApproved(approved);
        return UpdateStatus.APPROVED;
    }

    public boolean canBeUptadedWith(Information approved) {
        boolean isTheSameImplementation = this.getInformation().getClass().isAssignableFrom(approved.getClass());
        return isTheSameImplementation;
    }

    public void enqueueChange(Information newInformation, UpdateStatus status) {
        if (status.equals(UpdateStatus.NO_NEED_TO_APPROVE)) {
            updateApproved(newInformation);
        }
        newInformation.setModeratable(this);
        newInformation.setInitStatus(status);
        addHistory(newInformation);
    }
}
