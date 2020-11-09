
package com.example.okta_cust_sign_in.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.okta_cust_sign_in.BuildConfig;
import com.example.okta_cust_sign_in.R;
import com.example.okta_cust_sign_in.interfaces.AppLoadingView;
import com.example.okta_cust_sign_in.interfaces.AppMessageView;
import com.example.okta_cust_sign_in.interfaces.AppNavigation;
import com.example.okta_cust_sign_in.interfaces.AppNavigationProvider;
import com.example.okta_cust_sign_in.interfaces.IOktaAuthenticationClientProvider;
import com.example.okta_cust_sign_in.util.NavigationHelper;
import com.example.okta_cust_sign_in.util.OktaProgressDialog;
import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.authn.sdk.client.AuthenticationClients;



public class AppContainerActivity extends AppCompatActivity implements
        AppLoadingView,
        AppMessageView,
        IOktaAuthenticationClientProvider,
        AppNavigationProvider {
    AuthenticationClient authenticationClient = null;
    OktaProgressDialog oktaProgressDialog = null;
    protected AppNavigation navigation = null;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        this.oktaProgressDialog = new OktaProgressDialog(this);
        this.navigation = new NavigationHelper(this, fragmentManager, R.id.container);

        init();

    }

    private void init() {
        authenticationClient = AuthenticationClients.builder()
                .setOrgUrl(BuildConfig.BASE_URL)
                .build();
    }

    @Override
    public void onBackPressed() {
        int fragmentsCount = fragmentManager.getBackStackEntryCount();
        if (fragmentsCount == 1) {
            finish();
        } else if(fragmentsCount > 1) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void show() {
        oktaProgressDialog.show();
    }

    @Override
    public void show(String message) {
        oktaProgressDialog.show(message);
    }

    @Override
    public void hide() {
        oktaProgressDialog.hide();
    }

    @Override
    public AuthenticationClient provideAuthenticationClient() {
        return authenticationClient;
    }

    @Override
    public AppNavigation provideNavigation() {
        return navigation;
    }
}