package ru.shtyrev.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shtyrev.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}