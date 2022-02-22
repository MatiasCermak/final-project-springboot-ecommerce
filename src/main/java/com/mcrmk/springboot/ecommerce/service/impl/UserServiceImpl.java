package com.mcrmk.springboot.ecommerce.service.impl;

import com.mcrmk.springboot.ecommerce.builder.UserBuilder;
import com.mcrmk.springboot.ecommerce.cache.impl.CacheClientImpl;
import com.mcrmk.springboot.ecommerce.exception.BadRequestException;
import com.mcrmk.springboot.ecommerce.mail.EmailService;
import com.mcrmk.springboot.ecommerce.model.database.document.User;
import com.mcrmk.springboot.ecommerce.model.request.UserRequest;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;
import com.mcrmk.springboot.ecommerce.repository.UserRepository;
import com.mcrmk.springboot.ecommerce.security.JwtProvider;
import com.mcrmk.springboot.ecommerce.service.UserService;
import com.mcrmk.springboot.ecommerce.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CacheClientImpl<User> cacheUser;
    private final CacheClientImpl<String> cacheToken;
    private final EmailService emailService;

    @Override
    public UserResponse login(UserRequest request) {

        if (request.getEmail() == null || Objects.equals(request.getEmail(), ""))
            throw new BadRequestException("Email no existe o no es valido");
        User user = getByEmailFromCacheOrDB(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {

        }
        UserResponse userResponseCandidateFromCache = getTokenFromCache(user.getUsername());
        if (userResponseCandidateFromCache != null) return userResponseCandidateFromCache;
        String token = jwtProvider.getJWTToken(request.getUsername());
        saveTokenToCache(user.getUsername(), token);
        return UserResponse.builder().username(user.getUsername()).token(token).build();
    }

    @Override
    public UserResponse register(UserRequest request) {
        validateUser(request);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserBuilder.requestToDocument(request);
        User user = repository.save(UserBuilder.requestToDocument(request));
        emailService.sendSimpleMessage(user.getEmail(), "Bienvenido, " + user.getFirstName(), "Tu cuenta ha sido creada correctamente.");
        return UserBuilder.documentToResponse(user);

    }

    @Override
    public UserResponse retrieveByUsername(String username) {
        return UserBuilder.documentToResponse(getByEmailFromCacheOrDB(username));
    }

    private UserResponse getTokenFromCache(String username) {
        String candidateToken = cacheToken.recover(Constants.USER_TOKEN_KEY, username, String.class);
        if (candidateToken == null) return null;
        return UserResponse.builder().username(username).token(candidateToken).build();
    }

    private void saveTokenToCache(String username, String token) {
        cacheToken.save(Constants.USER_TOKEN_KEY, username, token);
    }

    private User getByEmailFromCacheOrDB(String email) {
        User userFromCache = cacheUser.recover(Constants.USER_KEY, email, User.class);
        if (userFromCache != null) return userFromCache;
        User userFromDB = repository.findByEmail(email).orElse(null);
        return userFromDB;
    }

    void validateUser(UserRequest request) {
        User user = getByEmailFromCacheOrDB(request.getUsername());
        if (user != null) {
            throw new BadRequestException("El email ya esta en uso.");

        }
        user = repository.findByUsername(request.getUsername()).orElse(null);
        if (user != null) {
            throw new BadRequestException("El usuario ya esta en uso.");
        }
    }

}
