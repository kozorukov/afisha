package tgbot.core.user;

public interface UserService {

    User getOrCreate(String telegramId);

    User get(long id);

    User setFullName(long id, String fullName);

    User setCurrentState(long id, String name, String data);
}
