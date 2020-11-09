
package com.example.okta_cust_sign_in.base;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.okta_cust_sign_in.interfaces.AppLoadingView;
import com.example.okta_cust_sign_in.interfaces.AppMessageView;
import com.example.okta_cust_sign_in.interfaces.AppNavigation;
import com.example.okta_cust_sign_in.interfaces.AppNavigationProvider;
import com.example.okta_cust_sign_in.interfaces.IOktaAuthenticationClientProvider;
import com.example.okta_cust_sign_in.interfaces.IOktaClientProvider;
import com.okta.authn.sdk.client.AuthenticationClient;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.ClientBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BaseFragment extends Fragment {
    private static String TAG = "BaseFragment";
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    protected Future lastTask = null;

    protected AuthenticationClient authenticationClient;
    protected AppNavigation navigation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AppLoadingView
                && context instanceof AppMessageView
                && context instanceof IOktaAuthenticationClientProvider){
            authenticationClient = ((IOktaAuthenticationClientProvider)context).provideAuthenticationClient();
            navigation = ((AppNavigationProvider)context).provideNavigation();
        }
    }

    protected void showLoading() {
        Activity activity = getActivity();
        if((activity instanceof AppLoadingView)) {
            ((AppLoadingView) activity).show();
        }
    }

    protected void hideLoading() {
        Activity activity = getActivity();
        if((activity instanceof AppLoadingView)) {
            ((AppLoadingView) activity).hide();
        }
    }

    protected void showMessage(String message) {
        Activity activity = getActivity();
        if((activity instanceof AppMessageView)) {
            ((AppMessageView) activity).showMessage(message);
        }
    }

    protected void submit(Runnable task) {
        if(!executor.isShutdown()) {
            lastTask = executor.submit(task);
        }
    }
    protected void schedule(Runnable task, long delay, TimeUnit unit) {
        if(!executor.isShutdown()) {
            lastTask = executor.schedule(task, delay, unit);
        }
    }

    private void unsubscribe() {
        executor.shutdownNow();
    }

    protected void runOnUIThread(Runnable task) {
        if(getActivity() != null) {
            getActivity().runOnUiThread(task);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }
}
