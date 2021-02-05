package com.github.wephotos.webwork.entity.dto;

import com.github.wephotos.webwork.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author chengzi
 * @date 2021-02-02 10:49
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class UserDto extends User {
    List<String> roles;
}
