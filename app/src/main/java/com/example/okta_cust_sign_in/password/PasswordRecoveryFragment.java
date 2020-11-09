package com.example.okta_cust_sign_in.password;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.okta_cust_sign_in.R;
import com.example.okta_cust_sign_in.base.BaseFragment;
import com.example.okta_cust_sign_in.util.CommonUtil;
import com.okta.authn.sdk.AuthenticationException;
import com.okta.authn.sdk.AuthenticationStateHandler;
import com.okta.authn.sdk.resource.AuthenticationResponse;
import com.okta.sdk.resource.user.factor.FactorType;

import static android.util.Log.println;

public class PasswordRecoveryFragment extends BaseFragment {
    private String TAG = "PasswordRecovery";
    Button passwordResetBtn = null;
    EditText loginEditText = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.password_recovery_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginEditText = view.findViewById(R.id.password_edittext);
        passwordResetBtn = view.findViewById(R.id.reset_password);
        passwordResetBtn.setOnClickListener(v -> passwordReset());
    }

    private void passwordReset() {
        if(TextUtils.isEmpty(loginEditText.getText())) {
            loginEditText.setError(getString(R.string.empty_field_error));
        } else {
            loginEditText.setError(null);
        }

        String username = loginEditText.getText().toString();

        CommonUtil.hideSoftKeyboard(getActivity());
        showLoading();
        submit(() -> {
            try {
                AuthenticationResponse response = authenticationClient.recoverPassword(username, FactorType.EMAIL, null, new AuthenticationStateHandler() {
                    @Override
                    public void handleUnauthenticated(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handlePasswordWarning(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handlePasswordExpired(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleRecovery(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleRecoveryChallenge(AuthenticationResponse authenticationResponse) {
                        runOnUIThread(() -> {
                            String finishMessage = String.format(getString(R.string.letter_with_reset_link_success), username)+"\n"+authenticationResponse.toString();
                            showMessage(finishMessage);
                            println(Log.ASSERT,"RECOVERYMESSAGE",finishMessage);
                            hideLoading();
                            navigation.close();
                        });
                    }

                    @Override
                    public void handlePasswordReset(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleLockedOut(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleMfaRequired(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleMfaEnroll(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleMfaEnrollActivate(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleMfaChallenge(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleSuccess(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }

                    @Override
                    public void handleUnknown(AuthenticationResponse authenticationResponse) {
                        showUnhandledStateMessage(authenticationResponse);
                    }
                });
            } catch (AuthenticationException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                runOnUIThread(() -> {
                    showMessage(e.getLocalizedMessage());
                    hideLoading();
                });
            }
        });
    }

    private void showUnhandledStateMessage(AuthenticationResponse authenticationResponse) {
        runOnUIThread(() -> {
            showMessage(String.format(getString(R.string.not_handle_message), authenticationResponse.getStatus().name()));
            hideLoading();
        });
    }
}
