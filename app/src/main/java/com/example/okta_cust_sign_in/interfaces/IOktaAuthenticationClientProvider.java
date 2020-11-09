package com.example.okta_cust_sign_in.interfaces;

import com.okta.authn.sdk.client.AuthenticationClient;

public interface IOktaAuthenticationClientProvider {
    AuthenticationClient provideAuthenticationClient();
}
