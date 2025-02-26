package com.PromotionManager.pManager.controller;

import com.PromotionManager.pManager.dto.auth.AuthReqDto;
import com.PromotionManager.pManager.service.MyUserDetailsService;
import com.PromotionManager.pManager.service.UserService;
import com.PromotionManager.pManager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
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

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        final String jwt = jwtUtil.generateToken(userDetails, roles);

        return jwt;
    }

}
