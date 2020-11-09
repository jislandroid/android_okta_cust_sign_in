package com.example.okta_cust_sign_in.rx;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.okta.oidc.Tokens;
import com.okta.oidc.net.ConnectionParameters;
import com.okta.oidc.net.response.IntrospectInfo;
import com.okta.oidc.net.response.UserInfo;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;


/**
 * This is the client for Okta OpenID Connect & OAuth 2.0 APIs. You can get the client when
 * user is authorized.
 *
 * @see <a href="https://developer.okta.com/docs/api/resources/oidc/">Okta API docs</a>
 */
public interface RxSessionClient {

    Single<JSONObject> authorizedRequest(@NonNull Uri uri, @Nullable Map<String, String> properties,
                                         @Nullable Map<String, String> postParameters,
                                         @NonNull ConnectionParameters.RequestMethod method);

    Single<UserInfo> getUserProfile();

    Single<IntrospectInfo> introspectToken(String token, String tokenType);

    Single<Boolean> revokeToken(String token);

    Single<Tokens> refreshToken();

    Single<Tokens> getTokens();

    Single<Boolean> isLoggedIn();

    Completable clear();
}
