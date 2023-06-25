package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;

import java.util.List;

@RequiredArgsConstructor
public class MenuState implements State {

    private final long userId;

    @Override
    public String getText() {
        return "Меню";
    }

    @Override
    public List<Button> getButtons() {
        return List.of(
                new Button("Профиль", Callback.of("profile")),
                new Button("Будущие мероприятия", Callback.of("future-events")),
                new Button("Мои регистрации", Callback.of("my-registrations"))
        );
    }

    @Override
    public State getNextState(Callback callback) {
        return switch (callback.getOperation()) {
            case "profile" -> new ProfileState(userId);
            case "future-events" -> new FutureEventsState(userId);
            case "my-registrations" -> new MyEventRegistrationsState(userId);
            default -> this;
        };
    }
}
