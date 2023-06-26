package tgbot.datainitializer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import tgbot.core.event.Event;
import tgbot.core.event.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitializer {

    private final EventRepository eventRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        var events = createEvents();
        events.forEach(this::addIfNotExists);
    }

    @SneakyThrows
    private List<Event> createEvents() {
        return List.of(
                Event.builder()
                        .name("День Рождения")
                        .description("Описание моего дня рождения")
                        .maxParticipantsCount(5)
                        .dateTime(LocalDateTime.of(2023, 6, 29, 10, 0))
                        .build(),
                Event.builder()
                        .name("Собеседование")
                        .description("Собеседование в команду Java-разработки")
                        .maxParticipantsCount(1)
                        .dateTime(LocalDateTime.of(2023, 7, 1, 10, 0))
                        .build()
        );
    }

    private void addIfNotExists(Event event) {
        var exists = eventRepository.existsByName(event.getName());
        if (!exists) {
            eventRepository.save(event);
        }
    }
}
