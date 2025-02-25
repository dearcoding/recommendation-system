package com.dearcoding.recommendationsystem.mapper;

import com.dearcoding.recommendationsystem.dto.history.HistoryDTO;
import com.dearcoding.recommendationsystem.dto.user.UserDTO;
import com.dearcoding.recommendationsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source="user.id")
    @Mapping(target = "username", source="user.username")
    @Mapping(target = "history", source="history")
    UserDTO userToUserDTO(User user, HistoryDTO history);
}
