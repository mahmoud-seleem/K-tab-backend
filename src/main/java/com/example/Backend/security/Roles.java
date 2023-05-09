package com.example.Backend.security;
;
import jakarta.persistence.PersistenceUnit;
import org.springframework.security.core.parameters.P;

import java.util.Arrays;
import static com.example.Backend.security.Permissions.*;
import java.util.HashSet;
import java.util.Set;

public enum Roles {
    STUDENT(new HashSet<>(
            Arrays.asList(CHAPTER_READ)
    ))
    ,ADMIN(new HashSet<>(
            Arrays.asList(CHAPTER_READ,CHAPTER_WRITE)));

    private Set<Permissions> permissions;

    Roles(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
