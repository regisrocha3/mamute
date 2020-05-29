package br.com.caelum.mamute.user.api;

import br.com.caelum.mamute.infrastructure.sanitized.text.SanitizedText;
import br.com.caelum.mamute.user.domain.UserEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterResource {
    private String name;
    private String email;
    private String sluggedName;

    public UserEntity toEntity() {
        final UserEntity entity = new UserEntity();
        entity.setEmail(this.email);
        entity.setSluggedName(this.sluggedName);
        entity.setName(SanitizedText.fromTrustedText(this.name));
        return entity;
    }
}
