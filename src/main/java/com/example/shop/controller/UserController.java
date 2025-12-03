package com.example.shop.controller;

import com.example.shop.dtos.*;
import com.example.shop.exceptions.NotAdminException;
import com.example.shop.exceptions.UserNotFoundException;
import com.example.shop.exceptions.WrongPasswordException;
import com.example.shop.mappers.UserMapper;
import com.example.shop.repositories.UserRepository;
import com.example.shop.services.AuthService;
import com.example.shop.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserService userService;
  @GetMapping
public List<UserDto> findAll(@RequestParam(required = false,defaultValue = " ") String sort) {
   return userService.findAll(sort);
  }

@GetMapping("/{id}")
public ResponseEntity<UserDto> getUser(@PathVariable Long id){

      return ResponseEntity.ok(userService.getUser(id));

}
@PostMapping
public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request
        , UriComponentsBuilder uriBuilder){
      var password= passwordEncoder.encode(request.getPassword());
      var userDto = userService.registerUser(request,password);
    var uri= uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

      return  ResponseEntity.created(uri).body(userDto);

  }

  @PutMapping("/{id}")
public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request, @PathVariable(name = "id") Long id){

     var userDto = userService.updateUser(request,id);

    return ResponseEntity.ok(userDto);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id){
   userService.deleteUser(id);
    return ResponseEntity.noContent().build();
}
@PutMapping("/{id}/change-password")
public ResponseEntity<Void> changePassword
        (@RequestBody ChangePasswordRequest request,@PathVariable(name="id") Long id)
        throws WrongPasswordException{
   userService.changePassword(request,id);

    return ResponseEntity.noContent().build();
}


 }