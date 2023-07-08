package de.vantrex.springpaste.model.user;

public record UserTokenResponse(User user, String token, long expireTime) { }