package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.Services;
import tgbot.bot.statemachine.State;
import tgbot.core.registration.Registration;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MyEventRegistrationsState implements State {

    private final long userId;

    @Setter
    private Services services;

    @Override
    public String getText() {
        return "Мои регистрации";
    }

    @Override
    public List<Button> getButtons() {
        var user = services.getUserService().get(userId);
        List<Registration> registrations = services.getRegistrationService().getByUser(user);
        List<Button> buttons = new ArrayList<>();
        for (Registration registration : registrations) {
            buttons.add(
                    new Button(
                            registration.getEvent().getName(),
                            Callback.of("event", registration.getEvent().getId())
                    )
            );
        }
        buttons.add(
                new Button("Меню", Callback.of("menu"))
        );
        return buttons;
    }

    @Override
    public State getNextState(Callback callback) {
        State state = this;
        switch (callback.getOperation()) {
            case "event" -> {
                state = new EventState(userId, callback.getResourceId());
            }
            case "menu" -> {
                state = new MenuState(userId);
            }
        }
        return state;
    }
}
