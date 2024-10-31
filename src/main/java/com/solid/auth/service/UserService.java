package com.solid.auth.service;

import com.solid.auth.dto.LoginRequest;
import com.solid.auth.dto.LoginResponse;
import com.solid.auth.dto.RegisterRequest;
import com.solid.auth.dto.RegisterResponse;
import com.solid.auth.exception.BadRequestException;
import com.solid.auth.models.Session;
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
    private final SessionService sessionService;
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

        Session session = sessionService.createSessionForUser(user.getId());
        result.setJwtToken(jwtService.generateToken(user.getUsername(), session.getId()));
        result.setRefreshToken(jwtService.generateRefreshToken(user.getUsername(), session.getId(), session.getExpSession()));

        return result;
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest loginRequest){
        //check existing data username
        Optional<Users> dataUser = userRepository.findByUsername(loginRequest.getUsername());
        if(dataUser.isPresent()){
            if (PasswordEncoderUtil.matches(loginRequest.getPassword(),dataUser.get().getPassword())){
                Session session = sessionService.createSessionForUser(dataUser.get().getId());
                return new LoginResponse(jwtService.generateToken(dataUser.get().getUsername(), session.getId()),
                        jwtService.generateRefreshToken(dataUser.get().getUsername(), session.getId(), session.getExpSession()));
            }
        }else {
            dataUser = userRepository.findByEmail(loginRequest.getEmail());
            if(dataUser.isPresent()){
                if (PasswordEncoderUtil.matches(loginRequest.getPassword(),dataUser.get().getPassword())){
                    Session session = sessionService.createSessionForUser(dataUser.get().getId());
                    return new LoginResponse(jwtService.generateToken(dataUser.get().getUsername(), session.getId()),
                            jwtService.generateRefreshToken(dataUser.get().getUsername(), session.getId(), session.getExpSession()));
                }
            }
            else {
                throw new BadRequestException("Username or Email Not Found");
            }
        }
        throw new BadRequestException("Invalid Password");
    }

}
