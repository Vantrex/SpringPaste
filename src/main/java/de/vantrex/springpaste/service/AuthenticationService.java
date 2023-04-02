package de.vantrex.springpaste.service;

import de.vantrex.springpaste.model.Credentials;
import de.vantrex.springpaste.model.user.User;
import de.vantrex.springpaste.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.token.secret-key:secret-key")
    private String secretKey;
    @Value("$security.jwt.token.separator:separator")
    private String tokenSeparator;



    public AuthenticationService(UserRepository userService, PasswordEncoder passwordEncoder) {
        this.userRepository = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticateUser(Credentials credentials) {
        final String encodedPassword = passwordEncoder.encode(credentials.password());
        return userRepository.findUserByNameAndPassword(credentials.name(), encodedPassword)
                .orElseThrow(() -> new RuntimeException("Username or password do not match!"));
    }

    public String createToken(User user) {
        return user.getId() + this.tokenSeparator + user.getName() + this.tokenSeparator + calculateHmac(user);
    }

    public User findByName(final String name) {
        return this.userRepository.findUserByName(name).orElseThrow(() -> new RuntimeException("No User with this name found!"));
    }
    public User findByToken(final String token) {
        String[] parts = token.split(this.tokenSeparator);
        final UUID userId = UUID.fromString(parts[0]);
        final String name = parts[1];
        final String hmac = parts[2];

        final User user = this.findByName(name);
        if (userId != user.getId() ||!hmac.equals(calculateHmac(user)))
            throw new RuntimeException("Invalid Cookie value!");
        return user;
    }

    private String calculateHmac(User user) {
        byte[] secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = Objects.requireNonNull(user.getId().toString() + this.tokenSeparator + user.getName())
                .getBytes(StandardCharsets.UTF_8);
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] bytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}