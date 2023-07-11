package com.example.Backend.security;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.springframework.stereotype.Component;

@MappedSuperclass
@Component
public class AppUser {

    @Column
    private boolean isAccountNonExpired = true;
    @Column
    private boolean isAccountNonLocked = true;
    @Column
    private boolean isCredentialsNonExpired = true;
    @Column
    private boolean isEnabled = true;

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
