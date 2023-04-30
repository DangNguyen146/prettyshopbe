package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.dto.ResponseDto;
import com.prettyshopbe.prettyshopbe.dto.user.SignInDto;
import com.prettyshopbe.prettyshopbe.dto.user.SignInResponseDto;
import com.prettyshopbe.prettyshopbe.dto.user.SignupDto;
import com.prettyshopbe.prettyshopbe.exceptions.AuthenticationFailException;
import com.prettyshopbe.prettyshopbe.exceptions.CustomException;
import com.prettyshopbe.prettyshopbe.model.User;
import com.prettyshopbe.prettyshopbe.respository.UserRepository;
import com.prettyshopbe.prettyshopbe.service.AuthenticationService;
import com.prettyshopbe.prettyshopbe.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }
    @GetMapping("/getrole")
    public ResponseEntity<String> getRole(@RequestParam("token") String token) throws AuthenticationFailException {
        // Code để xác thực token và lấy thông tin user từ cơ sở dữ liệu
        User user = authenticationService.getUser(token);

        // Lấy role của user và trả về kết quả dưới dạng chuỗi JSON
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("role", user.getRole().toString());
        return new ResponseEntity<>(jsonResult.toString(), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    //TODO token should be updated
    @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }
}
