package tgbot.core.registration;

import tgbot.core.event.Event;
import tgbot.core.user.User;

import java.util.List;

public interface RegistrationService {
    Registration register(User user, Event event);

    Registration findByUserAndEvent(User user, Event event);

    void cancel(Registration registration);

    List<Registration> getByUser(User user);
}
