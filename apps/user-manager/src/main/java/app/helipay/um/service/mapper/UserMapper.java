package app.helipay.um.service.mapper;


import app.helipay.um.constants.Constants;
import app.helipay.um.domain.Authority;
import app.helipay.um.domain.UserEntity;
import app.helipay.um.service.dto.RegisterRequest;
import app.helipay.um.service.dto.UserReply;
import app.helipay.um.service.dto.UserRequest;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.stream.Collectors;

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
//    UserEntity toEntity(UserRequest request);
    default UserEntity toEntity(UserRequest request) {

        final UserEntity.UserEntityBuilder builder = UserEntity.builder();

        builder.id(request.id());
        builder.firstName(request.firstName());
        builder.lastName(request.lastName());
        builder.imageUrl(request.imageUrl());

        builder.username(request.username() != null ? request.username().toLowerCase() : null);
        builder.email(request.email() != null ? request.email().toLowerCase() : null);
        builder.langKey(request.langKey() != null ? request.langKey() : Constants.DEFAULT_LANGUAGE);
        builder.activated(request.isActivated() != null ? request.isActivated() : false);

        return builder.build();
    }

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
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    default UserEntity userRequestToEntity(UserRequest input, UserEntity target) {
        if (input.username() != null) {
            target.setUsername(input.username().toLowerCase());
        }
        if (input.firstName() != null) {
            target.setFirstName(input.firstName());
        }
        if (input.lastName() != null) {
            target.setLastName(input.lastName());
        }
        if (input.email() != null) {
            target.setEmail(input.email().toLowerCase());
        }
        if (input.imageUrl() != null) {
            target.setImageUrl(input.imageUrl());
        }
        if (input.langKey() != null) {
            target.setLangKey(input.langKey());
        }
        if (input.isActivated() != null) {
            target.setActivated(input.isActivated());
        }

        return target;
    }

    default List<UserReply> toDto(Page<UserEntity> entities) {
        return entities.getContent().stream().map(this::toDto).toList();
    }

    default List<UserReply> toDto(List<UserEntity> users) {
        return users.stream().filter(Objects::nonNull).map(this::toDto).toList();
    }


    default UserReply toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        } else {
            return new UserReply(entity.getId(),
                    entity.getUsername(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getImageUrl(),
                    entity.isActivated(),
                    entity.getLangKey(),
                    entity.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        }
    }


    default UserEntity registerRequestToEntity(RegisterRequest request) {
        return UserEntity.builder()
                .username(request.username().toLowerCase())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email().toLowerCase())
                .imageUrl(request.imageUrl())
                .build();
    }


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