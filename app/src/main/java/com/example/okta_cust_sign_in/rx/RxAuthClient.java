package com.example.okta_cust_sign_in.rx;

import com.okta.oidc.AuthenticationPayload;
import com.okta.oidc.clients.BaseAuth;
import com.okta.oidc.results.Result;

import io.reactivex.Single;

/**
 * The Authentication client for logging in using a sessionToken.
 *
 * For login using web browser
 * {@link RxWebAuthClient}
 */
public interface RxAuthClient extends BaseAuth<RxSessionClient> {
    Single<Result> logIn(String sessionToken, AuthenticationPayload payload);
}
