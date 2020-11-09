package com.example.okta_cust_sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.native_okta_sing_in)
    protected Button nativeSignIn;

    @BindView(R.id.okta_user_regestion)
    protected Button userRegestion;

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //startActivity(LoginLandingActivity.createNativeSignIn(this));
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.native_okta_sing_in)
    public void onClickNativeSign() {
        startActivity(LoginLandingActivity.createNativeSignIn(this));
    }

    @OnClick(R.id.okta_user_regestion)
    public void onClickUserRegest() {
        startActivity(UserRegistationActivity.createNativeUserRegistation(this));
    }
}