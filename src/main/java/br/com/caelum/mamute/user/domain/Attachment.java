package br.com.caelum.mamute.user.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
public class Attachment {

    @Id
    @Getter
    @GeneratedValue
    private Long id;

    @Getter
    @Transient
    private File image;

    @Getter
    @Column(name = "createdAt", columnDefinition = "TIMESTAMP")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @ManyToOne
    private UserEntity owner;

    @Getter
    private String ip;

    @Getter
    private String mime;

    @Getter
    @Setter
    private String name;

    public Attachment(File file, String contentType, String fileName, UserEntity owner, String ip) {
        this.image = file;
        this.owner = owner;
        this.ip = ip;
        this.mime = contentType;
        this.name = fileName;
    }

    public Attachment(File image, UserEntity owner, String ip, String name) {
        this.image = image;
        this.owner = owner;
        this.ip = ip;
        this.mime = "image/png";
        this.name = name;
    }

    public boolean canDelete(UserEntity user) {
        return getOwner().equals(user);
    }
}
