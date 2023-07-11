package com.example.Backend.security;

import java.util.Optional;

public interface AppUserDetailsDao {

    Optional<AppUserDetails> findAppUserDetailsByUserName(String userName);
}
