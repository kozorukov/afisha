package tgbot.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getOrCreate(String telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .orElseGet(() -> userRepository.save(new User(telegramId)));
    }

    @Override
    public User get(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User setFullName(long id, String fullName) {
        var user = userRepository.findById(id).orElseThrow();
        user.setFullName(fullName);
        return user;
    }

    @Override
    public User setCurrentState(long id, String name, String data) {
        var user = userRepository.findById(id).orElseThrow();
        user.setCurrentStateName(name);
        user.setCurrentStateData(data);
        return user;
    }
}
