package tgbot.bot.statemachine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tgbot.core.event.EventService;
import tgbot.core.registration.RegistrationService;
import tgbot.core.user.UserService;

@Component
@RequiredArgsConstructor
@Getter
public class Services {
    private final UserService userService;

    private final EventService eventService;

    private final RegistrationService registrationService;
}
