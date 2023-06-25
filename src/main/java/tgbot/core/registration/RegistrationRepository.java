package tgbot.core.registration;

import tgbot.core.JpaRepository;
import tgbot.core.event.Event;
import tgbot.core.user.User;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration> {
    List<Registration> findByUser(User user);

    Optional<Registration> findByUserAndEvent(User user, Event event);
}
