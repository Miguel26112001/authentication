package com.example.authentication.iam.infrastructure.authorization.sfs.pipeline;

import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnauthorizedRequestHandlerEntryPoint implements AuthenticationEntryPoint {
  private static final Logger LOGGER
      = LoggerFactory.getLogger(UnauthorizedRequestHandlerEntryPoint.class);

  @Override
  public void commence(@NonNull HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authenticationException)
      throws IOException {

    LOGGER.error("Unauthorized request: {}", authenticationException.getMessage());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    var messageResource = new MessageResource(authenticationException.getMessage());
    var objectMapper = new ObjectMapper();
    var responseBody = objectMapper.writeValueAsString(messageResource);
    response.getWriter().write(responseBody);
  }
}