package com.example.okta_cust_sign_in.password;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.okta_cust_sign_in.MainActivity;
import com.example.okta_cust_sign_in.R;
import com.example.okta_cust_sign_in.base.BaseFragment;
import com.example.okta_cust_sign_in.util.CommonUtil;
import com.okta.authn.sdk.AuthenticationException;
import com.okta.authn.sdk.AuthenticationStateHandlerAdapter;
import com.okta.authn.sdk.resource.AuthenticationResponse;

import java.util.HashMap;
import java.util.Map;

public class PasswordResetFragment extends BaseFragment {
    private String TAG = "PasswordReset";
    private static String STATE_TOKEN_KEY = "STATE_TOKEN_KEY";
    private static String PASSWORD_POLICY_KEY = "PASSWORD_POLICY_KEY";

    private String stateToken = null;
    private Map<String, Integer> passwordPolicy = null;

    Button passwordResetBtn = null;
    TextView passwordPolicyTextView = null;
    EditText passwordEditText = null;
    EditText repeatPasswordEditText = null;

    public static PasswordResetFragment createFragment(String stateToken, AuthenticationResponse response) {
        PasswordResetFragment fragment = new PasswordResetFragment();

        Bundle arguments = new Bundle();
        arguments.putString(STATE_TOKEN_KEY, stateToken);
        Map<String, Integer> passwordPolicy = getPolicy(response);
        if(passwordPolicy != null) {
            arguments.putSerializable(PASSWORD_POLICY_KEY, new HashMap<>(passwordPolicy));
        }
        fragment.setArguments(arguments);

        return fragment;
    }

    private static Map<String, Integer> getPolicy(AuthenticationResponse response) {
        Map<String, Object> embedded = response.getEmbedded();
        if(embedded == null)
            return null;
        Map<String, Object> policy = (Map<String, Object>) embedded.get("policy");
        if(policy == null)
            return null;
        Map<String, Integer> complexity = (Map<String, Integer>) policy.get("complexity");
        if(complexity == null)
            return null;

        return complexity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.password_reset_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passwordPolicyTextView = view.findViewById(R.id.password_policy);
        passwordEditText = view.findViewById(R.id.password_edittext);
        repeatPasswordEditText = view.findViewById(R.id.repeat_password_edittext);
        passwordResetBtn = view.findViewById(R.id.reset_password);
        passwordResetBtn.setOnClickListener(v -> passwordReset());

        stateToken = getArguments().getString(STATE_TOKEN_KEY);
        try {
            passwordPolicy = (HashMap<String, Integer>) getArguments().getSerializable(PASSWORD_POLICY_KEY);
        } catch (Exception e) {
            passwordPolicy = null;
            Log.e(TAG, Log.getStackTraceString(e));
        }
        showPasswordPolicy();
    }



    private void showPasswordPolicy() {
        if(passwordPolicy != null) {
            String passwordPolicyText = "";
            for(String key : passwordPolicy.keySet()) {
                Object value = passwordPolicy.get(key);
                String item = "";
                if(value instanceof String) {
                    item += value;
                } else if(value instanceof Integer) {
                    item += Integer.toString((Integer) value);
                } else if(value instanceof Boolean) {
                    item += String.valueOf((Boolean)value);
                }
                passwordPolicyText = passwordPolicyText.concat(key+":"+item+"\n");
            }
            passwordPolicyTextView.setText(passwordPolicyText);
        }
    }

    private void passwordReset() {
        String password = passwordEditText.getText().toString();
        String repeatPassword = repeatPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.empty_field_error));
            return;
        } else if(TextUtils.isEmpty(repeatPassword)) {
            repeatPasswordEditText.setError(getString(R.string.empty_field_error));
            return;
        }

        if(!password.equalsIgnoreCase(repeatPassword)) {
            showMessage(getString(R.string.password_not_equal));
            return;
        }

        CommonUtil.hideSoftKeyboard(getActivity());
        showLoading();
        submit(() -> {
            try {
                AuthenticationResponse response = authenticationClient.resetPassword(password.toCharArray(), stateToken, new AuthenticationStateHandlerAdapter() {
                    @Override
                    public void handleUnknown(AuthenticationResponse authenticationResponse) {
                        runOnUIThread(() -> {
                            hideLoading();
                            showMessage(authenticationResponse.toString());
                        });
                    }

                    @Override
                    public void handleMfaRequired(AuthenticationResponse mfaRequiredResponse) {
                        runOnUIThread(() -> {
                            hideLoading();
                            showMessage(getString(R.string.mfa_require));
                        });
                    }

                    @Override
                    public void handleSuccess(AuthenticationResponse successResponse) {
                        runOnUIThread(() -> {
                            showMessage(getString(R.string.password_reset_successfully));
                            hideLoading();
                            navigation.close();
                            startActivity(MainActivity.createIntent(getContext()));
                        });
                    }
                });
            } catch (AuthenticationException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                runOnUIThread(() -> {
                    hideLoading();
                    showMessage(e.getMessage());
                });
            }
        });
    }
}
