package br.com.caelum.mamute.user.api;

import br.com.caelum.mamute.user.domain.Attachment;
import br.com.caelum.mamute.user.domain.UserEntity;
import lombok.*;
import org.elasticsearch.client.security.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResource {

    private Long id;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private String name;
    private String website;
    private String location;
    private String about;
    private String markedAbout;
    private LocalDate birthDate;
    private long karma = 0;
    private boolean moderator = false;
    private String forgotPasswordToken = "";
    private String photoUri;
    private String sluggedName;
    private LocalDateTime nameLastTouchedAt;
    private String email;
    private boolean isSubscribed = true;
    private boolean isBanned = false;
    private LocalDateTime lastUpvote = LocalDateTime.now();
    private Attachment avatarImage;
    private boolean receiveAllUpdates = false;
    private boolean deleted = false;

    public static List<UserResource> convertResponseFromUserEntity(List<UserEntity> listEntity) {
        return listEntity.stream().map(UserResource::convert).collect(Collectors.toList());
    }

    private static UserResource convert(UserEntity entity) {
        final UserResource response = new UserResource();
        response.setName(entity.getName());
        response.setAbout(entity.getAbout());
        response.setBanned(entity.isBanned());
        response.setBirthDate(entity.getBirthDate());
        response.setEmail(entity.getEmail());
        response.setId(entity.getId());
        return response;
    }
}
