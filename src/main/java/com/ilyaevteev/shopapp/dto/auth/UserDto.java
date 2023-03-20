package com.ilyaevteev.shopapp.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ilyaevteev.shopapp.model.auth.User;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Long balance;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setBalance(balance);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setBalance(user.getBalance());

        return userDto;
    }
}
