package com.example.okta_cust_sign_in.native_sign_in;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.okta_cust_sign_in.R;
import com.example.okta_cust_sign_in.RecoveryActivity;
import com.example.okta_cust_sign_in.base.BaseFragment;
import com.example.okta_cust_sign_in.interfaces.IOktaAppAuthClientProvider;
import com.example.okta_cust_sign_in.user_info.UserInfoActivity;
import com.example.okta_cust_sign_in.util.CommonUtil;
import com.okta.authn.sdk.AuthenticationException;
import com.okta.authn.sdk.AuthenticationStateHandlerAdapter;
import com.okta.authn.sdk.resource.AuthenticationResponse;
import com.okta.oidc.RequestCallback;
import com.okta.oidc.clients.AuthClient;
import com.okta.oidc.results.Result;
import com.okta.oidc.util.AuthorizationException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.util.Log.println;

public class NativeSignInFragment extends BaseFragment {
    private String TAG = "NativeSignIn";
    private AuthClient mAuthClient;

    @BindView(R.id.login_edit_text)
    protected EditText logInEditText;

    @BindView(R.id.password_edit_text)
    protected EditText passwordEditText;

    Unbinder unbinder;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IOktaAppAuthClientProvider) {
            mAuthClient = ((IOktaAppAuthClientProvider) context).provideOktaAppAuthClient();
        }
    }

    @OnClick(R.id.btn_sign_in)
    public void onClickBtnSign() {
        signIn();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.activity_sighn_in, container, false);
        View view =inflater.inflate(R.layout.activity_sighn_in, container, false);
        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @MainThread
    private void showUserInfo() {
        startActivity(UserInfoActivity.createIntent(getContext()));
        navigation.close();
    }

    private void signIn() {
        String login = logInEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(login)) {
            logInEditText.setError(getString(R.string.empty_field_error));
            return;
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.empty_field_error));
            return;
        }


        CommonUtil.hideSoftKeyboard(getActivity());
        showLoading();
        submit(() -> {
            try {
                authenticationClient.authenticate(login, password.toCharArray(), null, new AuthenticationStateHandlerAdapter() {
                    @Override
                    public void handleUnknown(AuthenticationResponse authenticationResponse) {
                        runOnUIThread(() -> {
                            hideLoading();
                            showMessage(String.format(getString(R.string.not_handle_message), authenticationResponse.getStatus().name()));
                        });
                    }

                    @Override
                    public void handleLockedOut(AuthenticationResponse lockedOut) {
                        runOnUIThread(() -> {
                            hideLoading();
                            showLockedAccountMessage(getContext());
                        });
                    }

                    @Override
                    public void handleSuccess(AuthenticationResponse successResponse) {
                        String sessionToken = successResponse.getSessionToken();
                        authenticateViaOktaAndroidSDK(sessionToken);
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

    private void authenticateViaOktaAndroidSDK(String sessionToken) {
        println(Log.ASSERT,"SESSIONTOKEN",sessionToken);
        this.mAuthClient.signIn(sessionToken, null, new RequestCallback<Result, AuthorizationException>() {
            @Override
            public void onSuccess(@NonNull Result result) {
                hideLoading();
                showUserInfo();
            }

            @Override
            public void onError(String s, AuthorizationException e) {
                hideLoading();
                showMessage(e.getLocalizedMessage());
                navigation.close();
            }
        });
    }

    private void showLockedAccountMessage(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.account_lock_out);
        builder.setMessage(R.string.unlock_account_message);
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.cancel();
        });
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            dialog.cancel();
            //navigation.present(UnlockAccountFragment.createFragment());
        });
        builder.create().show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

    @OnClick(R.id.btn_recovery_password)
    public void onClickRecPassword() {
        startActivity(RecoveryActivity.createPasswordRecovery(getContext()));
        navigation.close();
    }

}
