package com.JMedia.Data.UserData;

import com.JMedia.Data.UserData.TwoFactorCodes;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called twoFactorCodesRepository
// CRUD refers Create, Read, Update, Delete

public interface TwoFactorCodesRepository extends CrudRepository<TwoFactorCodes, String> {
    public Optional<TwoFactorCodes> findByEmail(String email);
}
