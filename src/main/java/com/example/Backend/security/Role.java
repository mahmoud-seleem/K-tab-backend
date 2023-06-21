package com.example.Backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import static com.example.Backend.security.Permission.*;
import java.util.HashSet;
import java.util.Set;

public enum Role {
    STUDENT(new HashSet<>(
            Arrays.asList(CHAPTER_READ))),
    ADMIN(new HashSet<>(
            Arrays.asList(CHAPTER_READ, CHAPTER_WRITE)));

    private Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Permission permission : this.getPermissions()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
