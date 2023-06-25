package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;

import java.util.List;

@RequiredArgsConstructor
public class SignUpSuccessState implements State {

    private final long userId;

    @Override
    public String getText() {
        return "Регистрация успешна";
    }

    @Override
    public List<Button> getButtons() {
        return List.of(
                new Button("Меню", Callback.of("menu"))
        );
    }

    @Override
    public State getNextState(String input) {
        return new MenuState(userId);
    }

    @Override
    public State getNextState(Callback callback) {
        return new MenuState(userId);
    }
}
