package com.snowboard_rental_crm.auth_core.service;

import com.snowboard_rental_crm.auth_core.entity.TokenEntity;
import com.snowboard_rental_crm.auth_core.entity.UserEntity;
import com.snowboard_rental_crm.auth_core.repository.TokenRepository;
import com.snowboard_rental_crm.auth_core.repository.UserRepository;
import com.snowboard_rental_crm.auth_data.model.AuthTokenModel;
import com.snowboard_rental_crm.auth_data.model.RegistrationRequest;
import com.snowboard_rental_crm.auth_data.model.RegistrationResponse;
import com.snowboard_rental_crm.shared_data.enumeration.UserType;
import com.snowboard_rental_crm.shared_data.exception.BadRequestException;
import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.EMAIL_EXIST;
import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.LOGIN_EXIST;
import static com.snowboard_rental_crm.auth_data.constant.ExceptionConstants.PHONE_EXIST;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    public AuthTokenModel getToken(Long userId) {
        return tokenRepository.findByUserId(userId)
                .map(token -> AuthTokenModel.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .build())
                .orElse(new AuthTokenModel());
    }

    public void saveToken(AuthTokenModel request, Long userId) {
        TokenEntity tokenByUserId = tokenRepository.findByUserId(userId)
                .orElse(TokenEntity.builder()
                        .userId(userId)
                        .build());

        tokenByUserId.setAccessToken(request.getAccessToken());
        tokenByUserId.setRefreshToken(request.getRefreshToken());

        tokenRepository.save(tokenByUserId);
    }

    public UserDataResponseModel getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(admin -> modelMapper.map(admin, UserDataResponseModel.class))
                .orElse(new UserDataResponseModel());
    }

    public RegistrationResponse register(RegistrationRequest request) {
        checkDataExistence(request);

        UserEntity user = UserEntity.builder()
                .password(encoder.encode(request.getPassword()))
                .login(request.getLogin())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(UserType.ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);

        return RegistrationResponse.builder()
                .userId(user.getId())
                .build();
    }

    private void checkDataExistence(RegistrationRequest request) {
        userRepository.findByLoginOrPhoneOrEmail(request.getLogin(), request.getPhone(), request.getEmail())
                .ifPresent(user -> {
                    if (Objects.equals(user.getLogin(), request.getLogin())) {
                        throw new BadRequestException(String.format(LOGIN_EXIST, request.getLogin()));
                    }
                    if (Objects.equals(user.getEmail(), request.getEmail())) {
                        throw new BadRequestException(String.format(EMAIL_EXIST, request.getEmail()));
                    }
                    if (Objects.equals(user.getPhone(), request.getPhone())) {
                        throw new BadRequestException(String.format(PHONE_EXIST, request.getPhone()));
                    }
                });
    }
}
