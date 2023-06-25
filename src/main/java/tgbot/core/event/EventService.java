package tgbot.core.event;

import java.util.List;

public interface EventService {
    List<Event> getFuture();

    Event getById(long id);
}
