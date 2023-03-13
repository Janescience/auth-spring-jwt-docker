package com.demo.auth.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping("/firsttime")
	public CsrfToken allAccess(CsrfToken token) {
        return token;
	}
}
