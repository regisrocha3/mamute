package br.com.caelum.mamute.watch;

import br.com.caelum.mamute.user.domain.UserEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Watcher {

    @Getter
    @GeneratedValue
    @Id
    private Long id;

    @Getter
    private boolean active = true;

    @Getter
    @ManyToOne
    private final UserEntity watcher;

    @Getter
    private final LocalDateTime createdAt;

    public Watcher(UserEntity watcher) {
        this.watcher = watcher;
        this.createdAt = LocalDateTime.now();
    }

    public void inactivate() {
        active = false;
    }

    public void activate() {
        active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Watcher watcher = (Watcher) o;
        return Objects.equals(id, watcher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
