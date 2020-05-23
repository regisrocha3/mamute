package br.com.caelum.mamute.vote;

import br.com.caelum.mamute.user.domain.UserEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@ToString
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private final VoteType type;

    @ManyToOne
    private final UserEntity author;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime lastUpdatedAt = LocalDateTime.now();

    public int getCountValue() {
        return type.getCountValue();
    }

    public long substitute(Vote previous, List<Vote> votes) {
        long delta = 0;
        if (votes.remove(previous))
            delta -= previous.getCountValue();
        votes.add(this);
        delta += getCountValue();
        return delta;
    }

    public Vote(UserEntity author, VoteType type) {
        this.author = author;
        this.type = type;
    }

    public boolean isUp() {
        return type.equals(VoteType.UP);
    }

    public boolean isDown() {
        return type.equals(VoteType.DOWN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
