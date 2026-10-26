package com.idat.soportetecnico.service;

import com.idat.soportetecnico.dto.AuthResponse;
import com.idat.soportetecnico.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}