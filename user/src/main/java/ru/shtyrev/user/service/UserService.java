package ru.shtyrev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shtyrev.user.dto.UserDto;
import ru.shtyrev.user.mapper.UserMapper;
import ru.shtyrev.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userMapper::toUserDto)
                .orElseThrow(RuntimeException::new);
    }
}
