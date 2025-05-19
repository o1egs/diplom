package ru.shtyrev.user.dto;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link ru.shtyrev.user.entity.User}
 */
@Value
@Builder
public class UserDto {
    Long id;
    String login;
    String name;
    String surname;
}