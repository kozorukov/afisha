package tgbot.bot.statemachine;

public interface StateController {

    State getNextState(String telegramId, String input);

    State getNextState(String telegramId, Callback callback);
}
