package tgbot.bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;
import tgbot.bot.statemachine.StateController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final StateController stateController;

    public TelegramBot(@Value("${token}") String token, StateController stateController) {
        super(token);
        this.stateController = stateController;
    }

    @Override
    public String getBotUsername() {
        return "Afisha";
    }

    @PostConstruct
    @SneakyThrows
    public void init() {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        getNextState(update).ifPresent(this::showNextState);
    }

    private Optional<NextState> getNextState(Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();
            if (message.hasText()) {
                var telegramId = message.getChatId().toString();
                var input = message.getText();
                var nextState = stateController.getNextState(telegramId, input);
                return Optional.of(new NextState(telegramId, null, nextState));
            }
        }
        if (update.hasCallbackQuery()) {
            var callbackQuery = update.getCallbackQuery();
            var telegramId = callbackQuery.getFrom().getId().toString();
            var messageId = callbackQuery.getMessage().getMessageId();
            var callback = Callback.deserialize(callbackQuery.getData());
            var nextState = stateController.getNextState(telegramId, callback);
            return Optional.of(new NextState(telegramId, messageId, nextState));
        }
        return Optional.empty();
    }

    @SneakyThrows
    private void showNextState(NextState nextState) {
        var buttons = nextState.nextState.getButtons().stream()
                .map(button -> List.of(toInlineKeyboardButton(button)))
                .toList();
        if (nextState.messageId == null) {
            var sendMessage = new SendMessage();
            sendMessage.setChatId(nextState.telegramId);
            sendMessage.enableHtml(true);
            sendMessage.setText(nextState.nextState.getText());
            sendMessage.setReplyMarkup(new InlineKeyboardMarkup(buttons));
            execute(sendMessage);
        } else {
            var editMessageText = new EditMessageText();
            editMessageText.setChatId(nextState.telegramId);
            editMessageText.setMessageId(nextState.messageId);
            editMessageText.enableHtml(true);
            editMessageText.setText(nextState.nextState.getText());
            editMessageText.setReplyMarkup(new InlineKeyboardMarkup(buttons));
            execute(editMessageText);
        }
    }

    private InlineKeyboardButton toInlineKeyboardButton(Button button) {
        var inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(button.getCaption());
        inlineKeyboardButton.setCallbackData(button.getCallback().serialize());
        return inlineKeyboardButton;
    }

    @Data
    private static class NextState {
        private final String telegramId;
        private final Integer messageId;
        private final State nextState;
    }
}
