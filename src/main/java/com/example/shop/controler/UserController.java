package com.example.shop.controler;

import com.example.shop.dtos.ChangePasswordRequest;
import com.example.shop.dtos.RegisterUserRequest;
import com.example.shop.dtos.UpdateUserRequest;
import com.example.shop.dtos.UserDto;
import com.example.shop.entities.User;
import com.example.shop.mappers.UserMapper;
import com.example.shop.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
  @GetMapping
public Iterable<UserDto> findAll(@RequestParam(required = false,defaultValue = " ") String sort) {
      if(!sort.equals("name") && !sort.equals("email")){
          sort = "name";
      }
      return userRepository.findAll(Sort.by(sort))
              .stream()
              .map(userMapper::toDto )
              .toList();
  }
@GetMapping("/{id}")
public ResponseEntity<UserDto> getUser(@PathVariable int id){
  var user= userRepository.findById(id).orElse(null);
  if(user!=null){
      return ResponseEntity.ok(userMapper.toDto(user));
  }
  else {
    return ResponseEntity.notFound().build();
  }
}
@PostMapping
public ResponseEntity<UserDto> creatUsear(@Valid @RequestBody RegisterUserRequest request, UriComponentsBuilder uriBuilder){

      var user = userMapper.toEntity(request);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      var userDto = userMapper.toDto(user);
    var uri=  uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

      return  ResponseEntity.created(uri).body(userDto);

  }
  @PutMapping("/{id}")
public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request, @PathVariable(name = "id") int id){

var user = userRepository.findById(id).orElse(null);
if(user==null) {
    return ResponseEntity.notFound().build();
}
    userMapper.updateUser(request, user);
    userRepository.save(user);

    return ResponseEntity.ok(userMapper.toDto(user));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") int id){
    var user = userRepository.findById(id).orElse(null);
    if(user==null) {
        return ResponseEntity.notFound().build();
    }
    userRepository.delete(user);
    return ResponseEntity.noContent().build();
}
@PutMapping("/{id}/change-password")
public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request,@PathVariable(name="id") int id){
var user = userRepository.findById(id).orElse(null);
if(user==null) {
    return ResponseEntity.notFound().build();
}
if(!user.getPassword().equals(request.getOldPassword())){
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
}
user.setPassword(request.getNewPassword());

    userRepository.save(user);
    return ResponseEntity.noContent().build();
}


 }