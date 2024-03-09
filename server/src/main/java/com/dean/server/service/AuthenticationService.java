package com.dean.server.service;

import com.dean.server.dto.*;
import com.dean.server.entity.UserEntity;
import com.dean.server.repository.UserRepository;
import com.dean.server.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthenticationService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    public UserDTO findUserByUserName(String username){
        UserDTO userDTO = new UserDTO();
        UserEntity userEntity = this.userRepository.findByUsername(username);

        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setRole(userDTO.getRole());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());

        return userDTO;
    }

    public ResponseEntity<?> register(RegisterDTO registerDTO){

        ResultDTO resultDTO = new ResultDTO();

        if(registerDTO.getUsername().isEmpty() ||
        registerDTO.getName().isEmpty() ||
        registerDTO.getPassword().isEmpty()){
            resultDTO.setErrorCode("-1");
            resultDTO.setData(null);
            resultDTO.setMessage("empty");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);

        }

        if(this.userRepository.findByUsername(registerDTO.getUsername()) != null ){
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("username is already taken");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(registerDTO.getName());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setRole((short) 1);

        userRepository.save(userEntity);



        resultDTO.setErrorCode("0");
        resultDTO.setData(null);
        resultDTO.setMessage("success");

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO){
        ResultDTO resultDTO = new ResultDTO();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();


        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserEntity userEntity = userRepository.findByUsername(authentication.getName());

        if(userEntity != null){
            String token = this.jwtUtil.generateToken(userEntity.getUsername(), Arrays.asList(userEntity.getRole().toString()));

            loginResponseDTO.setUsername(userEntity.getUsername());
            loginResponseDTO.setRole(userEntity.getRole().toString());
            loginResponseDTO.setAuthToken(token);
            loginResponseDTO.setMsv(userEntity.getMsv());

            resultDTO.setErrorCode("0");
            resultDTO.setData(loginResponseDTO);

            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        } else {
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("login fail");

            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO){
        ResultDTO resultDTO = new ResultDTO();
        UserEntity userEntity = userRepository.findByUsername(changePasswordDTO.getUsername());

        if(userEntity != null){
            if(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), userEntity.getPassword())){
                userEntity.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(userEntity);
                resultDTO.setErrorCode("0");
                resultDTO.setMessage("Thành công");
                return new ResponseEntity<>(resultDTO, HttpStatus.OK);
            } else {
                resultDTO.setErrorCode("-1");
                resultDTO.setMessage("Mật khẩu cũ không đúng");
                return new ResponseEntity<>(resultDTO, HttpStatus.OK);
            }
        } else {
            resultDTO.setErrorCode("-1");
            resultDTO.setMessage("user not found");
            return new ResponseEntity<>(resultDTO, HttpStatus.OK);
        }
    }




}
