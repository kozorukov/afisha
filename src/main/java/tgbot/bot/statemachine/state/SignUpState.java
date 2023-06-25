package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tgbot.bot.statemachine.Services;
import tgbot.bot.statemachine.State;

@RequiredArgsConstructor
public class SignUpState implements State {

    private final long userId;

    @Setter
    private Services services;

    @Override
    public String getText() {
        return "Введите ФИО";
    }

    @Override
    public State getNextState(String input) {
        services.getUserService().setFullName(userId, input);
        return new SignUpSuccessState(userId);
    }
}
