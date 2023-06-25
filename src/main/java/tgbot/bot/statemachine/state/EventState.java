package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.Services;
import tgbot.bot.statemachine.State;

import java.util.List;

@RequiredArgsConstructor
public class EventState implements State {

    private final long userId;
    private final long eventId;

    @Setter
    private Services services;

    @Override
    public String getText() {
        var event = services.getEventService().getById(eventId);
        return """
                <b>Мероприятие:</b> %s
                <b>Описание:</b> %s
                <b>Дата, время:</b> %s
                """.formatted(event.getName(), event.getDescription(), event.getDateTime());
    }

    @Override
    public List<Button> getButtons() {
        var user = services.getUserService().get(userId);
        var event = services.getEventService().getById(eventId);
        Button actionButton;
        try {
            services.getRegistrationService().findByUserAndEvent(user, event);
            actionButton = new Button("Отменить регистрацию", Callback.of("cancel"));
        } catch (Exception e) {
            actionButton = new Button("Зарегистрироваться", Callback.of("registration"));
        }

        return List.of(
                actionButton,
                new Button("Меню", Callback.of("menu"))
        );
    }

    @Override
    public State getNextState(Callback callback) {
        State state = this;
        switch (callback.getOperation()) {
            case "registration" -> {
                var user = services.getUserService().get(userId);
                var event = services.getEventService().getById(eventId);
                services.getRegistrationService().register(user, event);
            }
            case "cancel" -> {
                var user = services.getUserService().get(userId);
                var event = services.getEventService().getById(eventId);
                var registration = services.getRegistrationService().findByUserAndEvent(user, event);
                services.getRegistrationService().cancel(registration);
            }
            case "menu" -> {
                state = new MenuState(userId);
            }
        }
        return state;
    }
}
