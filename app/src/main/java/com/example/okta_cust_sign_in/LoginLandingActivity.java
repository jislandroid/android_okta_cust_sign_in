package com.example.okta_cust_sign_in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.fragment.app.Fragment;

import com.example.okta_cust_sign_in.base.AppContainerActivity;
import com.example.okta_cust_sign_in.interfaces.IOktaAppAuthClientProvider;
import com.example.okta_cust_sign_in.native_sign_in.NativeSignInFragment;
import com.example.okta_cust_sign_in.network.ServiceLocator;
import com.example.okta_cust_sign_in.user_info.UserInfoActivity;
import com.example.okta_cust_sign_in.util.PreferenceRepository;
import com.example.okta_cust_sign_in.util.SmartLockHelper;
import com.okta.oidc.clients.AuthClient;
import com.okta.oidc.clients.sessions.SessionClient;
import com.okta.oidc.storage.security.DefaultEncryptionManager;
import com.okta.oidc.storage.security.EncryptionManager;

public class LoginLandingActivity extends AppContainerActivity implements IOktaAppAuthClientProvider {
    private String TAG = "NativeSignInActivity";

    private static String MODE_KEY = "MODE_KEY";

    private AuthClient mAuth;
    private SessionClient mSessionClient;
    private PreferenceRepository mPreferenceRepository;
    private SmartLockHelper mSmartLockHelper;

    enum MODE {
        NATIVE_SIGN_IN,
        NATIVE_SIGN_IN_WITH_MFA,
        UNKNOWN
    }

    public static Intent createNativeSignIn(Context context) {
        Intent intent = new Intent(context, LoginLandingActivity.class);
        intent.putExtra(MODE_KEY, LoginLandingActivity.MODE.NATIVE_SIGN_IN.ordinal());
        return intent;
    }
    private Fragment getFragmentByModeId(int id, Intent intent){
        if(id == LoginLandingActivity.MODE.NATIVE_SIGN_IN.ordinal()) {
            return new NativeSignInFragment();
        } else if(id == LoginLandingActivity.MODE.NATIVE_SIGN_IN_WITH_MFA.ordinal()) {
           // return new NativeSignInFragment();
            return null;
        } else {
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferenceRepository = ServiceLocator.providePreferenceRepository(this);
        mSmartLockHelper = ServiceLocator.provideSmartLockHelper();

        init();
    }
    private void init() {
        mAuth = ServiceLocator.provideAuthClient(this);
        mSessionClient = mAuth.getSessionClient();

        //clearStorage();

        if (mSessionClient.isAuthenticated()) {
            if (mPreferenceRepository.isEnabledSmartLock()) {
                if (!SmartLockHelper.isKeyguardSecure(this)) {
                    clearStorage();
                } else {
                    EncryptionManager encryptionManager = ServiceLocator.provideEncryptionManager(LoginLandingActivity.this);
                    mSmartLockHelper.showSmartLockChooseDialog(this, new SmartLockHelper.FingerprintCallback(this, encryptionManager) {
                        @Override
                        protected void onSuccess() {
                            if (encryptionManager.isValidKeys()) {
                                showUserInfo();
                            } else {
                                clearStorage();
                            }
                        }

                        @Override
                        public void onFingerprintError(String error) {
                            super.onFingerprintError(error);
                            showMessage(error);
                            clearStorage();
                        }

                        @Override
                        public void onFingerprintCancel() {
                            super.onFingerprintCancel();
                            showMessage(getString(R.string.cancel));
                        }
                    }, encryptionManager.getCipher());
                }
            } else {
                showUserInfo();
            }
        } else {
            clearStorage();
            showLoginForm();
        }
    }
    @MainThread
    private void showUserInfo() {
        startActivity(new Intent(this, UserInfoActivity.class));
        finish();
    }

    private void showLoginForm() {
        Fragment fragment = getFragmentByModeId(getIntent().getIntExtra(MODE_KEY, -1), getIntent());
        if(fragment != null) {
            this.navigation.present(fragment);
        }
    }

    @Override
    public void clearStorage() {
        mSessionClient.clear();
        ServiceLocator.provideEncryptionManager(this).removeKeys();
        try {
            DefaultEncryptionManager simpleEncryptionManager = ServiceLocator.createSimpleEncryptionManager(this);
            ServiceLocator.setEncryptionManager(simpleEncryptionManager);
            mAuth.migrateTo(simpleEncryptionManager);
            mPreferenceRepository.enableSmartLock(false);
        } catch (Exception e) {
            showMessage(e.getMessage());
        }
    }

    @Override
    public AuthClient provideOktaAppAuthClient() {
        return mAuth;
    }
}
