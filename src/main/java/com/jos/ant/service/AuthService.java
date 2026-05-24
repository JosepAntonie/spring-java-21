package com.jos.ant.service;

import java.security.PublicKey;

public interface AuthService
{
    PublicKey getPublicKey( String keyId );
}
