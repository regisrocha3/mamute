package br.com.caelum.mamute.user.domain;

import br.com.caelum.mamute.infrastructure.sanitized.text.MarkedText;
import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static br.com.caelum.mamute.user.domain.UserPersonalInfoValidator.*;

@NoArgsConstructor
public class UserPersonalInfo {

    @Getter
    private UserEntity user;

    @Getter
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_LENGTH_MESSAGE)
    @NotEmpty(message = NAME_REQUIRED)
    private String name;

    @Getter
    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH, message = EMAIL_LENGTH_MESSAGE)
    @Email(message = EMAIL_NOT_VALID)
    private String email;

    @Getter
    @Size(min = WEBSITE_MIN_LENGTH, max = WEBSITE_MAX_LENGHT, message = WEBSITE_LENGTH_MESSAGE)
    private String website;

    @Getter
    @Setter
    @Size(max = LOCATION_MAX_LENGTH, message = LOCATION_LENGTH_MESSAGE)
    private String location;

    @Setter
    @Getter
    private LocalDate birthDate;

    @Getter
    private String about;

    @Getter
    private String markedAbout;

    @Getter
    private boolean isSubscribed;

    @Getter
    private boolean receiveAllUpdates;

    public UserPersonalInfo(UserEntity user) {
        this.user = user;
    }

    public UserPersonalInfo withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserPersonalInfo withLocation(SanitizedText location) {
        this.location = location.getText();
        return this;
    }

    public UserPersonalInfo withWebsite(SanitizedText website) {
        this.website = website.getText();
        return this;
    }

    public UserPersonalInfo withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserPersonalInfo withAbout(MarkedText about) {
        this.about = about.getPure();
        this.markedAbout = about.getMarked();
        return this;
    }

    public UserPersonalInfo withName(SanitizedText name) {
        this.name = name.getText();
        return this;
    }

    public UserPersonalInfo withIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
        return this;
    }

    public UserPersonalInfo withReceiveAllUpdates(boolean receiveAllUpdates) {
        this.receiveAllUpdates = receiveAllUpdates;
        return this;
    }
}