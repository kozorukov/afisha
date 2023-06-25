package tgbot.core.registration;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tgbot.core.JpaEntity;
import tgbot.core.event.Event;
import tgbot.core.user.User;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends JpaEntity {
    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Event event;
}
