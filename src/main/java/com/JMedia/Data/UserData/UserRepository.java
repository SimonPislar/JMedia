package com.JMedia.Data.UserData;

import com.JMedia.Data.UserData.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
}
