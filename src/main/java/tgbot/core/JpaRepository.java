package tgbot.core;

public interface JpaRepository<T extends JpaEntity>
        extends org.springframework.data.jpa.repository.JpaRepository<T, Long> {
}
