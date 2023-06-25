package tgbot.bot.statemachine;

import lombok.Data;

@Data
public class Button {
    private final String caption;
    private final Callback callback;
}
