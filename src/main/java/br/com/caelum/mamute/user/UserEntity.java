package br.com.caelum.mamute.user;

import br.com.caelum.mamute.common.Votable;
import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import br.com.caelum.mamute.infrastructure.sanitized.text.TextNormalizer;
import br.com.caelum.mamute.infrastructure.security.Digester;
import br.com.caelum.mamute.watch.Watcher;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText.fromTrustedText;
import static br.com.caelum.mamute.user.UserPersonalInfoValidator.*;

@Entity
@Cacheable
@Table(name = "Users")
public class UserEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @NotEmpty(message = NAME_REQUIRED)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_LENGTH_MESSAGE)
    private String name;

    @Getter
    @Size(max = WEBSITE_MAX_LENGHT, message = WEBSITE_LENGTH_MESSAGE)
    private String website;

    @Getter
    @Size(max = LOCATION_MAX_LENGTH, message = LOCATION_LENGTH_MESSAGE)
    private String location;

    @Getter
    private String about;

    @Getter
    private String markedAbout;

    @Getter
    private LocalDate birthDate;

    @Getter
    private long karma = 0;

    @Getter
    private boolean moderator = false;

    private String forgotPasswordToken = "";

    private String photoUri;

    @Getter
    @NotEmpty
    private String sluggedName;

    @Getter
    private LocalDateTime nameLastTouchedAt;

    @Getter
    @OneToMany(mappedBy = "user")
    private final List<LoginMethod> loginMethods = new ArrayList<>();

    public static final UserEntity GHOST;

    @Setter
    @Getter
    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH, message = EMAIL_LENGTH_MESSAGE)
    @Email(message = EMAIL_NOT_VALID)
    private String email;

    @Getter
    private boolean isSubscribed = true;

    @Getter
    private boolean isBanned = false;

    @Getter
    @OneToMany(mappedBy = "watcher")
    private final List<Watcher> watches = new ArrayList<>();

    private LocalDateTime lastUpvote = LocalDateTime.now();

    @ManyToOne
    private Attachment avatarImage;

    @Getter
    private boolean receiveAllUpdates = false;

    @Getter
    private boolean deleted = false;

    static {
        GHOST = new UserEntity(fromTrustedText("GHOST"), "");
        GHOST.setId(1000l); // TODO: Entender melhor esse ponto
    }

    public UserEntity(SanitizedText name, String email) {
        setName(name);
        this.email = email;
    }

    public UserSession newSession() {
        Long currentTimeMillis = System.currentTimeMillis();
        String sessionKey = Digester.encrypt(currentTimeMillis.toString() + this.id.toString());
        return new UserSession(this, sessionKey);
    }

    public void setName(SanitizedText name) {
        this.name = name.getText();
        this.sluggedName = TextNormalizer.toSlug(this.name);
        this.nameLastTouchedAt = LocalDateTime.now();
    }

    public void setPhotoUri(URL storedUri) {
        photoUri = storedUri.toString();
    }

    public void setPersonalInformation(UserPersonalInfo info) {
        this.birthDate = info.getBirthDate();
        this.name = info.getName();
        this.email = info.getEmail();
        this.website = info.getWebsite();
        this.location = info.getLocation();
        this.about = info.getAbout();
        this.markedAbout = info.getMarkedAbout();
        this.isSubscribed = info.isSubscribed();
        this.receiveAllUpdates = info.isReceiveAllUpdates();
    }

    public String getSmallPhoto(String gravatarUrl) {
        return getPhoto(32, 32, gravatarUrl);
    }

    public String getMediumPhoto(String gravatarUrl) {
        return getPhoto(48, 48, gravatarUrl);
    }

    public String getBigPhoto(String gravatarUrl) {
        return getPhoto(128, 128, gravatarUrl);
    }

    public Integer getAge() {
        LocalDate now = LocalDate.now();
        if (birthDate == null) {
            return null;
        }

        return Period.between(birthDate, now).getYears();
    }

    public LoginMethod getBrutalLogin() {
        for (LoginMethod method : loginMethods) {
            if (method.isBrutal()) {
                return method;
            }
        }
        throw new IllegalStateException("this guy dont have a brutal login method!");
    }

    public String getPhoto(Integer width, Integer height, String gravatarUrl) {
        if (this.avatarImage != null) {
            return this.localAvatarPhoto(width, height);
        }
        String size = width + "x" + height;
        if (photoUri == null) {
            String digest = Digester.md5(email);
            String robohash = "https://robohash.org/size_" + size + "/set_set1/bgset_any/" + digest + ".png";
            String gravatar = gravatarUrl + "/avatar/" + digest + ".png?r=PG&size=" + size;
            try {
                return gravatar + "&d=" + java.net.URLEncoder.encode(robohash, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return gravatar;
            }
        } else if (photoUri.contains("googleusercontent")) {
            return photoUri.replaceAll("sz=(\\d+)", "sz=" + width);
        } else {
            return photoUri + "?width=" + width + "&height=" + height;
        }
    }

    private String localAvatarPhoto(Integer width, Integer height) {
        String format = "/attachments/%d?w=%d&h=%d";
        String url = String.format(format, avatarImage.getId(), width, height);
        return url;
    }

    public UserEntity asModerator() {
        this.moderator = true;
        return this;
    }

    public UserEntity removeModerator() {
        this.moderator = false;
        return this;
    }

    /*public boolean canModerate(EnvironmentKarma environmentKarma) {
        long karma = environmentKarma.get(PermissionRules.MODERATE_EDITS);
        return isModerator() || this.karma >= karma;
    }*/

    public String touchForgotPasswordToken() {
        String tokenSource = Math.random() + System.currentTimeMillis() + getEmail() + getId();
        this.forgotPasswordToken = Digester.encrypt(tokenSource);
        return forgotPasswordToken;
    }

    public boolean isValidForgotPasswordToken(String token) {
        return this.forgotPasswordToken.equals(token);
    }

    public boolean updateForgottenPassword(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            return false;
        }
        for (LoginMethod method : loginMethods) {
            method.updateForgottenPassword(password);
        }
        return true;
    }

    /*public UpdateStatus approve(Moderatable moderatable, Information approvedInfo, EnvironmentKarma environmentKarma) {
        if (this.canModerate(environmentKarma)) {
            moderatable.approve(approvedInfo);
            approvedInfo.moderate(this, UpdateStatus.APPROVED);
        }
        return UpdateStatus.REFUSED;
    }*/

    public void decreaseKarma(int value) {
        this.karma -= value;
    }

    public void increaseKarma(int value) {
        this.karma += value;
    }

    public void add(LoginMethod brutalLogin) {
        loginMethods.add(brutalLogin);
    }

    public boolean isAuthorOf(Votable votable) {
        return id == votable.getAuthor().getId();
    }

    /*public boolean hasKarmaToAnswerOwnQuestion(EnvironmentKarma environmentKarma) {
        long answerOwnQuestion = environmentKarma.get(PermissionRules.ANSWER_OWN_QUESTION);
        return (this.karma >= answerOwnQuestion) || isModerator();
    }*/

    /*public boolean hasKarmaToAnswerInactiveQuestion(EnvironmentKarma environmentKarma) {
        long answerInactiveQuestion = environmentKarma.get(PermissionRules.INACTIVATE_QUESTION);
        return (this.karma >= answerInactiveQuestion) || isModerator();
    }*/

    public String getUnsubscribeHash() {
        return Digester.encrypt(this.email + Digester.hashFor(this.id));
    }

    public void ban() {
        this.isBanned = true;
    }

    public void undoBan() {
        this.isBanned = false;
    }

    public void votedUp() {
        this.lastUpvote = LocalDateTime.now();
    }

    public boolean isVotingEnough() {
        return !lastUpvote.isBefore(LocalDateTime.now().minusWeeks(1));
    }

    public boolean hasKarma() {
        return (this.karma >= 0);
    }

    public void setAvatar(Attachment attachment) {
        this.avatarImage = attachment;
    }

    public Attachment getAvatar() {
        return avatarImage;
    }

    @Override
    public String toString() {
        return "[User " + email + ", " + name + ", " + id + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

