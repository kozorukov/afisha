package tgbot.core.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tgbot.core.JpaEntity;

@Entity
@Table(name = "user_")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User extends JpaEntity {

    @Setter
    private String fullName;

    @NotNull
    @Column(unique = true)
    private String telegramId;

    @Setter
    private String currentStateName;

    @Setter
    private String currentStateData;

    public User(String telegramId) {
        this.telegramId = telegramId;
    }

    public boolean isRegistered() {
        return fullName != null;
    }
}
