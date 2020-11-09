package com.example.okta_cust_sign_in.interfaces;

import com.okta.oidc.clients.AuthClient;

public interface IOktaAppAuthClientProvider {
    AuthClient provideOktaAppAuthClient();
    void clearStorage();
}
