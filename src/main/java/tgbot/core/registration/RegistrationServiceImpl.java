package tgbot.core.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tgbot.core.event.Event;
import tgbot.core.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;

    @Override
    public Registration register(User user, Event event) {
        var registration = Registration.builder()
                .user(user)
                .event(event)
                .build();
        return registrationRepository.save(registration);
    }

    @Override
    public Registration findByUserAndEvent(User user, Event event) {
        return registrationRepository.findByUserAndEvent(user, event).orElseThrow();
    }

    @Override
    public void cancel(Registration registration) {
        registrationRepository.delete(registration);
    }

    @Override
    public List<Registration> getByUser(User user) {
        return registrationRepository.findByUser(user);
    }
}
