package com.JMedia.Data.UserData;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionTokenRepository extends CrudRepository<SessionToken, String> {
    public Optional<SessionToken> findByEmail(String email);
}
