package app.helipay.um.service.mapper;


import app.helipay.um.domain.Authority;
import app.helipay.um.domain.UserEntity;
import app.helipay.um.service.dto.UserReply;
import app.helipay.um.service.dto.UserRequest;
import org.mapstruct.*;

//
import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.Mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
//        @Mapping(target = "id", ignore = true)
//    @Mapping(target = "resetKey", ignore = true)
////  @Mapping(target = "accounts", ignore = true)
//    @Mapping(target = "activationKey", ignore = true)
//    @Mapping(target = "resetDate", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    @Mapping(target = "activated", ignore = true)
//    @Mapping(target = "imageUrl", ignore = true)
//    @Mapping(target = "created", ignore = true)
//    @Mapping(target = "changed", ignore = true)
    UserEntity toEntity(UserRequest request);

    UserReply toDto(UserEntity entity);

    List<UserReply> toDto(List<UserEntity> entities);

    List<UserReply> toDto(Page<UserEntity> entities);

    // WARNING: ADMIN USAGE

    //    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "resetKey", ignore = true)
////  @Mapping(target = "accounts", ignore = true)
//    @Mapping(target = "activationKey", ignore = true)
//    @Mapping(target = "resetDate", ignore = true)
//    @Mapping(target = "version", ignore = true)
//    @Mapping(target = "activated", ignore = true)
//    @Mapping(target = "imageUrl", ignore = true)
//    @Mapping(target = "created", ignore = true)
//    @Mapping(target = "changed", ignore = true)
//    @Mapping(target = "username", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copyUpdateToEntity(UserRequest input, @MappingTarget UserEntity target);

    default Page<UserReply> toPageDto(Page<UserEntity> entities) {
        return entities.map(this::toDto);
    }


//     List<UserReply> usersToUserDTOs(List<UserEntity> users) {
//        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).toList();
//    }

//     UserDTO userToUserDTO(User user) {
//        return new UserDTO(user);
//    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString
                    .stream()
                    .map(string -> {
                        Authority auth = new Authority();
                        auth.setName(string);
                        return auth;
                    })
                    .collect(Collectors.toSet());
        }

        return authorities;
    }


}