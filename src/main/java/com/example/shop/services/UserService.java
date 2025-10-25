package com.example.shop.services;

import com.example.shop.dtos.ChangePasswordRequest;
import com.example.shop.dtos.RegisterUserRequest;
import com.example.shop.dtos.UpdateUserRequest;
import com.example.shop.dtos.UserDto;
import com.example.shop.entities.Role;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.exceptions.UserNotFoundException;
import com.example.shop.exceptions.WrongPasswordException;
import com.example.shop.mappers.UserMapper;
import com.example.shop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user=userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getEmail(),
                user.getPassword(),
                Collections.emptyList());
    }

    public List<UserDto> findAll(String sort) {
        if(!sort.equals("name") && !sort.equals("email")){
            sort = "name";
        }
        var role = authService.getRole();
        if(role != Role.ADMIN){
            throw new NotAdminException();
        }
        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto )
                .toList();
    }

    public UserDto getUser(Long id){
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        var role = authService.getRole();
        if(role != Role.ADMIN){
            throw new NotAdminException();
        }

            return userMapper.toDto(user);


    }
    public  UserDto registerUser( RegisterUserRequest request,
                                  String password){
        if(userRepository.existsByEmail(request.getEmail())){
          throw new UserNotFoundException("Email already in use");
        }
        var user = userMapper.toEntity(request);
        user.setPassword(password);
        user.setRole(Role.USER);
       var savedUser= userRepository.save(user);

            var userDto = userMapper.toDto(savedUser);

        return  userDto;

    }
    public UserDto updateUser(@RequestBody UpdateUserRequest request, @PathVariable(name = "id") Long id){


        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

      userMapper.updateUser(request, user);
      var savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
    public void deleteUser(@PathVariable(name = "id") Long id){
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        userRepository.delete(user);

    }
    public void changePassword( ChangePasswordRequest request,  Long id) throws WrongPasswordException {
        var user= userRepository.findById(id).orElse(null);
        if(user==null) {
            throw new UserNotFoundException("User not found");
        }
        if(!user.getPassword().equals(request.getOldPassword())){
            throw new WrongPasswordException("Wrong Password");
        }
        user.setPassword(request.getNewPassword());

        userRepository.save(user);
    }
}
