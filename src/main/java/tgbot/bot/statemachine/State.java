package tgbot.bot.statemachine;

import java.util.List;

import static java.util.Collections.emptyList;

public interface State {

    String getText();

    default List<Button> getButtons() {
        return emptyList();
    }

    default State getNextState(String input) {
        return this;
    }

    default State getNextState(Callback callback) {
        return this;
    }

    default void setServices(Services services) {
    }
}
