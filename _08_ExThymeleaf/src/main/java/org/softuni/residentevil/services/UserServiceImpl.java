package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.entities.User;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.repositories.RoleRepository;
import org.softuni.residentevil.repositories.UserRepository;
import org.softuni.residentevil.utils.AuthSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final AuthSeeder authSeeder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, AuthSeeder authSeeder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.authSeeder = authSeeder;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.authSeeder.initialDbRolesSeed();

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        user.setAuthorities(this.authSeeder.seedUserRoles());

        try {
            this.userRepository.saveAndFlush(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserServiceModel> findAll() {
        List<UserServiceModel> userServiceModelList = this.userRepository
                .findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
        return userServiceModelList;
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    public void editUser(String id, String role) {

        User user = this.userRepository.findById(id).orElse(null);
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);

        Role userRole = this.roleRepository.findByAuthority("ROLE_USER");
        Role adminRole = this.roleRepository.findByAuthority("ROLE_ADMIN");
        Set<Role> newRoles = new HashSet<>();

        switch (role) {
            case "ROLE_USER":
                newRoles.add(userRole);
                break;
            case "ROLE_ADMIN":
                newRoles.add(userRole);
                newRoles.add(adminRole);
                break;
        }

        userServiceModel.setAuthorities(newRoles);
        this.userRepository.save(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public boolean isRoot(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        Role rootRole  = this.roleRepository.findByAuthority("ROLE_ROOT");
        return userServiceModel.getAuthorities().contains(rootRole);
    }

    @Override
    public void deleteById(String id) throws Exception {
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }
}
