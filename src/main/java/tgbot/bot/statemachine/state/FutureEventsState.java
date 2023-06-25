package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.Services;
import tgbot.bot.statemachine.State;
import tgbot.core.event.Event;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FutureEventsState implements State {

    private final long userId;

    @Setter
    private Services services;

    @Override
    public String getText() {
        return "Будущие мероприятия";
    }

    @Override
    public List<Button> getButtons() {
        List<Event> events = services.getEventService().getFuture();
        List<Button> buttons = new ArrayList<>();
        for (Event event : events) {
            buttons.add(
                    new Button(event.getName(), Callback.of("event", event.getId()))
            );
        }
        buttons.add(
                new Button("Меню", Callback.of("menu"))
        );
        return buttons;
    }

    @Override
    public State getNextState(Callback callback) {
        return switch (callback.getOperation()) {
            case "event" -> new EventState(userId, callback.getResourceId());
            case "menu" -> new MenuState(userId);
            default -> this;
        };
    }
}
