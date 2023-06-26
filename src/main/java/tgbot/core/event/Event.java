package tgbot.core.event;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tgbot.core.JpaEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event extends JpaEntity {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private LocalDateTime dateTime;

    @Positive
    private Integer maxParticipantsCount;
}
