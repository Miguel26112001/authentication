package com.example.authentication.iam.infrastructure.hashing.bcrypt;

import com.example.authentication.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BcryptHashingService extends HashingService, PasswordEncoder {
}
