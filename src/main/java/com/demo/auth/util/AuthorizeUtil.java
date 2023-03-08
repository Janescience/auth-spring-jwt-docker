package com.demo.auth.util;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthorizeUtil implements Serializable {
    private String username;
}
