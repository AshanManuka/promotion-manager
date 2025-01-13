package com.PromotionManager.pManager.controller;

import com.PromotionManager.pManager.dto.auth.AuthReqDto;
import com.PromotionManager.pManager.service.MyUserDetailsService;
import com.PromotionManager.pManager.service.UserService;
import com.PromotionManager.pManager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthReqDto authReq) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authReq.getUsername());
        System.out.println(userDetails);
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
    }
}
