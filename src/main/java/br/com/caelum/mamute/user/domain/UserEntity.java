package br.com.caelum.mamute.user.domain;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import br.com.caelum.mamute.infrastructure.sanitized.text.TextNormalizer;
import br.com.caelum.mamute.infrastructure.security.Digester;
import br.com.caelum.mamute.watch.Watcher;
import lombok.*;

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

import static br.com.caelum.mamute.user.domain.UserPersonalInfoValidator.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@Table(name = "Users")
public class UserEntity {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @NotEmpty(message = NAME_REQUIRED)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_LENGTH_MESSAGE)
    private String name;

    @Size(max = WEBSITE_MAX_LENGHT, message = WEBSITE_LENGTH_MESSAGE)
    private String website;

    @Size(max = LOCATION_MAX_LENGTH, message = LOCATION_LENGTH_MESSAGE)
    private String location;

    private String about;

    private String markedAbout;

    private LocalDate birthDate;

    private long karma = 0;

    private boolean moderator = false;

    private String forgotPasswordToken = "";

    private String photoUri;

    @NotEmpty
    private String sluggedName;

    private LocalDateTime nameLastTouchedAt;

    @OneToMany(mappedBy = "user")
    private final List<LoginMethod> loginMethods = new ArrayList<>();

    @Setter
    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH, message = EMAIL_LENGTH_MESSAGE)
    @Email(message = EMAIL_NOT_VALID)
    private String email;

    private boolean isSubscribed = true;

    private boolean isBanned = false;

    @OneToMany(mappedBy = "watcher")
    private final List<Watcher> watches = new ArrayList<>();

    private LocalDateTime lastUpvote = LocalDateTime.now();

    @ManyToOne
    private Attachment avatarImage;

    private boolean receiveAllUpdates = false;

    private boolean deleted = false;

    public UserEntity(SanitizedText name, String email) {
        this.name = name.getText();
        this.email = email;
        this.nameLastTouchedAt = LocalDateTime.now();
        this.sluggedName = TextNormalizer.toSlug(this.name);
    }

    public void setName(SanitizedText name) {
        this.name = name.getText();
        this.nameLastTouchedAt = LocalDateTime.now();
    }

    public void setSluggedName(final String sluggedName) {
        this.sluggedName = TextNormalizer.toSlug(sluggedName);
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

    public void add(LoginMethod brutalLogin) {
        loginMethods.add(brutalLogin);
    }

    public String getUnsubscribeHash() {
        return Digester.encrypt(this.email + Digester.hashFor(this.id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
