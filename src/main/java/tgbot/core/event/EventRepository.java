package tgbot.core.event;

import tgbot.core.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event> {

    boolean existsByName(String name);

    List<Event> findByDateTimeGreaterThanOrderByDateTime(LocalDateTime now);

}