package br.com.caelum.mamute.user.api;

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
}
