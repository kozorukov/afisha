package tgbot.core.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> getFuture() {
        var now = LocalDateTime.now();
        return eventRepository.findByDateTimeGreaterThanOrderByDateTime(now);
    }

    @Override
    public Event getById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));
    }
}
