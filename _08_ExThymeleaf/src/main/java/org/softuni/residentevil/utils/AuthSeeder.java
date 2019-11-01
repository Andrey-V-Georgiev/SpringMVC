package org.softuni.residentevil.utils;

import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.repositories.RoleRepository;
import org.softuni.residentevil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthSeeder {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthSeeder(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void initialDbRolesSeed() {
        if(this.roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");

            Role moderator = new Role();
            moderator.setAuthority("ROLE_MODERATOR");

            Role user = new Role();
            user.setAuthority("ROLE_USER");

            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(moderator);
            this.roleRepository.saveAndFlush(user);
        }
    }

    public Set<Role> seedUserRoles() {
        Set<Role> roles = new HashSet<>();

        if(this.userRepository.count() == 0) {
            roles.add(this.roleRepository.findByAuthority("ROLE_ADMIN"));
            roles.add(this.roleRepository.findByAuthority("ROLE_MODERATOR"));
            roles.add(this.roleRepository.findByAuthority("ROLE_USER"));
        } else {
            roles.add(this.roleRepository.findByAuthority("ROLE_USER"));
        }
        return roles;
    }
}
