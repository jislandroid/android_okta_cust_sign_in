package com.example.okta_cust_sign_in.interfaces;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;

public interface IOktaClientProvider {
    Client createClient();
}
