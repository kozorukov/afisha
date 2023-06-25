package tgbot.bot.statemachine;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tgbot.bot.statemachine.state.MenuState;
import tgbot.bot.statemachine.state.SignUpState;
import tgbot.core.user.User;

import java.util.Optional;
import java.util.function.UnaryOperator;

@Slf4j
@Component
@RequiredArgsConstructor
public class StateControllerImpl implements StateController {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
            .setSerializationInclusion(Include.NON_NULL);

    private final Services services;

    @Override
    public State getNextState(String telegramId, String input) {
        return getNextState(telegramId, input, state -> state.getNextState(input));
    }

    @Override
    public State getNextState(String telegramId, Callback callback) {
        return getNextState(telegramId, null, state -> state.getNextState(callback));
    }

    private State getNextState(String telegramId, String input, UnaryOperator<State> nextStateSupplier) {
        var user = services.getUserService().getOrCreate(telegramId);
        var nextState = getNextState(user, input, nextStateSupplier);
        saveState(user, nextState);
        nextState.setServices(services);
        log.info("Next state: {}", nextState.getClass().getSimpleName());
        return nextState;
    }

    private State getNextState(User user, String input, UnaryOperator<State> nextStateSupplier) {
        var optionalCurrentState = getCurrentState(user);
        var currentStateIsSignUpState = optionalCurrentState.isPresent()
                && optionalCurrentState.get().getClass() == SignUpState.class;
        if (!user.isRegistered() && !currentStateIsSignUpState) {
            return new SignUpState(user.getId());
        }
        if (optionalCurrentState.isEmpty() || user.isRegistered() && "/start".equals(input)) {
            return new MenuState(user.getId());
        }
        var currentState = optionalCurrentState.get();
        currentState.setServices(services);
        return nextStateSupplier.apply(currentState);
    }

    private Optional<State> getCurrentState(User user) {
        try {
            log.info("Current state: {} / {}", user.getCurrentStateName(), user.getCurrentStateData());
            var stateClass = (Class<? extends State>) Class.forName(user.getCurrentStateName());
            return Optional.of(OBJECT_MAPPER.readValue(user.getCurrentStateData(), stateClass));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private void saveState(User user, State state) {
        state.setServices(null);
        services.getUserService().setCurrentState(
                user.getId(),
                state.getClass().getName(),
                OBJECT_MAPPER.writeValueAsString(state)
        );
    }
}
