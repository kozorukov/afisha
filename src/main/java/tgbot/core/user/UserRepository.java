package tgbot.core.user;


import tgbot.core.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User> {

    Optional<User> findByTelegramId(String telegramId);
}
