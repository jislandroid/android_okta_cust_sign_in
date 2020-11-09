package com.example.okta_cust_sign_in.rx;

import android.content.Context;

import com.okta.oidc.AuthenticationPayload;
import com.okta.oidc.OIDCConfig;
import com.okta.oidc.clients.SyncAuthClient;
import com.okta.oidc.clients.SyncAuthClientFactory;
import com.okta.oidc.net.OktaHttpClient;
import com.okta.oidc.results.Result;
import com.okta.oidc.storage.OktaStorage;
import com.okta.oidc.storage.security.EncryptionManager;

import io.reactivex.Single;

class RxAuthClientImpl implements RxAuthClient {
    private SyncAuthClient mSyncAuthClient;
    private RxSessionClientImpl rxSessionClientImpl;

    RxAuthClientImpl(OIDCConfig oidcConfig, Context context, OktaStorage oktaStorage, EncryptionManager encryptionManager, OktaHttpClient httpClient, boolean isRequireHardwareBackedKeyStore, boolean isCacheMode) {
        mSyncAuthClient = new SyncAuthClientFactory().createClient(oidcConfig, context, oktaStorage, encryptionManager, httpClient, isRequireHardwareBackedKeyStore, isCacheMode);
        rxSessionClientImpl = new RxSessionClientImpl(mSyncAuthClient.getSessionClient());
    }

    @Override
    public Single<Result> logIn(String sessionToken, AuthenticationPayload payload) {
        return Single.create(emitter -> emitter.onSuccess(mSyncAuthClient.signIn(sessionToken, payload)));
    }

    @Override
    public RxSessionClient getSessionClient() {
        return rxSessionClientImpl;
    }
}
