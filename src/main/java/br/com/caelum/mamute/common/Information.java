package br.com.caelum.mamute.common;

import br.com.caelum.mamute.moderator.Moderatable;
import br.com.caelum.mamute.user.UserEntity;
import status.UpdateStatus;

public interface Information {

    void moderate(UserEntity currentUser, UpdateStatus refused);

    Object getId();

    boolean isPending();
    
    UserEntity getAuthor();
    
    Moderatable getModeratable();
    void setModeratable(Moderatable moderatable);

    String getTypeName();
    
    boolean isBeforeCurrent();

	void setInitStatus(UpdateStatus status);

}