package com.solid.auth.service;

import com.solid.auth.dto.LoginRequest;
import com.solid.auth.dto.LoginResponse;
import com.solid.auth.dto.RegisterRequest;
import com.solid.auth.dto.RegisterResponse;
import com.solid.auth.exception.BadRequestException;
import com.solid.auth.models.Users;
import com.solid.auth.repository.UserRepository;
import com.solid.auth.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest){
        RegisterResponse result = new RegisterResponse();
        //check existing data username
        Optional<Users> usernameTaken = userRepository.findByUsername(registerRequest.getUsername());
        if(usernameTaken.isPresent()){
            throw new BadRequestException("Username already Existed");
        }

        Optional<Users> emailTaken = userRepository.findByEmail(registerRequest.getEmail());
        if(emailTaken.isPresent()){
            throw new BadRequestException("Email already Taken");
        }

        Users user = new Users();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        String hashPassword = PasswordEncoderUtil.toHash(registerRequest.getPassword());
        user.setPassword(hashPassword);
        user = userRepository.save(user);

        result.setEmail(user.getEmail());
        result.setUsername(user.getUsername());
        result.setId(user.getId());

        return result;
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest loginRequest){
        //check existing data username
        Optional<Users> dataUser = userRepository.findByUsername(loginRequest.getUsername());
        if(dataUser.isPresent()){
            if (PasswordEncoderUtil.matches(loginRequest.getPassword(),dataUser.get().getPassword())){
                String token = jwtService.generateToken(loginRequest.getUsername());
                return new LoginResponse(token);
            }
        }else {
            dataUser = userRepository.findByEmail(loginRequest.getEmail());
            if(dataUser.isPresent()){
                if (PasswordEncoderUtil.matches(loginRequest.getPassword(),dataUser.get().getPassword())){
                    String token = jwtService.generateToken(loginRequest.getUsername());
                    return new LoginResponse(token);
                }
            }
            else {
                throw new BadRequestException("Username or Email Not Found");
            }
        }
        throw new BadRequestException("Invalid Password");
    }

}
