package com.example.authentication.iam.infrastructure.tokens.jwt;

import com.example.authentication.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;

public interface BearerTokenService extends TokenService {
  String getBearerTokenFrom(HttpServletRequest token);
}
