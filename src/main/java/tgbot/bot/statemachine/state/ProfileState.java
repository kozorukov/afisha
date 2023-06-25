package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.Services;
import tgbot.bot.statemachine.State;

import java.util.List;

@RequiredArgsConstructor
public class ProfileState implements State {

    private final long userId;

    @Setter
    private Services services;

    @Override
    public String getText() {
        var user = services.getUserService().get(userId);
        return """
                <b>Имя:</b> %s
                <b>Telegram ID:</b> %s
                """.formatted(user.getFullName(), user.getTelegramId());
    }

    @Override
    public List<Button> getButtons() {
        return List.of(
                new Button("Меню", Callback.of("menu"))
        );
    }

    @Override
    public State getNextState(Callback callback) {
        return switch (callback.getOperation()) {
            case "menu" -> new MenuState(userId);
            default -> this;
        };
    }
}
