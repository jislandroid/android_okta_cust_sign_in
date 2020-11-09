package com.example.okta_cust_sign_in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.okta_cust_sign_in.base.AppContainerActivity;
import com.example.okta_cust_sign_in.interfaces.IOktaClientProvider;
import com.example.okta_cust_sign_in.native_sign_in.NativeSignInFragment;
import com.example.okta_cust_sign_in.registation.UserRegistationFragment;
import com.okta.sdk.client.Client;

public class UserRegistationActivity extends AppContainerActivity {

    private String TAG="UserRegistationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_registation);
        init();
    }

    public static Intent createNativeUserRegistation(Context context)
    {
        Intent intent = new Intent (context,UserRegistationActivity.class);
        return intent;
    }

    private Fragment getFragmentd(){
        return new UserRegistationFragment();
    }

    private void init() {
        Fragment fragment = getFragmentd();
        if(fragment != null) {
            this.navigation.present(fragment);
        }
    }

}