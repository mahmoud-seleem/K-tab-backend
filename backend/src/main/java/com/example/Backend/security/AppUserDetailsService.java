package com.example.Backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserDetailsDao appUserDetailsDao;

    @Autowired
    public AppUserDetailsService(@Qualifier("DataBase") AppUserDetailsDao appUserDetailsDao) {
        this.appUserDetailsDao = appUserDetailsDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return appUserDetailsDao.findAppUserDetailsByUserName(username)
                    .orElseThrow(new Supplier<Throwable>() {
                        @Override
                        public Throwable get() {
                            return new UsernameNotFoundException(
                                    String.format(
                                            "user with user_name = \"%s\" not found",username));
                        }
                    });
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
