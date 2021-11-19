package com.destruction.myDemolish.domainOne;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.destruction.myDemolish.domainOne.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(COURSE_READ,STUDENT_READ)),
    ADMIN(Sets.newHashSet(
            COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE
    ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
