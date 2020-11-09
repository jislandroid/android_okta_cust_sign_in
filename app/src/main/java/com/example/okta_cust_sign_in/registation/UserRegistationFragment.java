package com.example.okta_cust_sign_in.registation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.okta_cust_sign_in.R;
import com.example.okta_cust_sign_in.base.BaseFragment;
import com.example.okta_cust_sign_in.util.CommonUtil;
import com.okta.sdk.authc.credentials.ClientCredentials;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.AuthenticationScheme;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;

import com.okta.sdk.resource.ResourceException;
import com.okta.sdk.resource.user.PasswordCredential;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.resource.user.UserCredentials;
import com.okta.sdk.resource.user.UserProfile;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;



import java.util.concurrent.atomic.AtomicReference;

public class UserRegistationFragment extends BaseFragment {
    private String TAG = "UserRegistation";

    private Client client;// = Clients.builder().build();
    /*UserProfile profile=new UserProfile();
    UserLocal userLocal=new UserLocal();*/
    Unbinder unbinder;
    //=User.Entry<"firstName", "Isaac">;
    char[] password = {'P','a','s','s','w','o','r','d','1'};
    User user = null;
    UserProfile userProfile=null;
    UserCredentials userCredentials=null;
    PasswordCredential passwordCredential=null;

    @BindView(R.id.login_edit_text)
    protected EditText logInEditText;

    @BindView(R.id.email_edit_text)
    protected EditText emailEditText;

    @BindView(R.id.fastName_edit_text)
    protected EditText firstNameEditText;

    @BindView(R.id.lastName_edit_text)
    protected EditText lastNameEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*if (context instanceof IOktaClientProvider) {
            client = ((IOktaClientProvider) context).createClient();
        }*/

        ClientCredentials clientCredentials = new TokenClientCredentials("009fcBG0M4FeyHJPc1KYOMURsDyghqx5S2UdjtK8KA");
        client=Clients.builder()
                .setOrgUrl("https://dev-258962.okta.com")
                .setAuthenticationScheme(AuthenticationScheme.SSWS)
                .setClientCredentials(clientCredentials)
                .build();
    }
    @OnClick(R.id.btn_sign_up)
    public void onClickBtnSign() {
        registation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         //return inflater.inflate(R.layout.activity_user_registation, container, false);
        View view =inflater.inflate(R.layout.activity_user_registation, container, false);
        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    private void registation()
    {
        //println(Log.ASSERT,"hello","hello");
        String login = logInEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String firstName=firstNameEditText.getText().toString();
        String lastName=lastNameEditText.getText().toString();
        if (TextUtils.isEmpty(login)) {
            logInEditText.setError(getString(R.string.empty_field_error));
            return;
        } else if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.empty_field_error));
            return;
        }else if (TextUtils.isEmpty(firstName)) {
            firstNameEditText.setError(getString(R.string.empty_field_error));
            return;
        }else if (TextUtils.isEmpty(lastName))
        {
            lastNameEditText.setError(getString(R.string.empty_field_error));
            return;
        }

        CommonUtil.hideSoftKeyboard(getActivity());
        showLoading();
        AtomicReference<String> userName = new AtomicReference<>("USER: ");

        println("USERRESPONSE");
        submit(() -> {
            try {
                //User userResponse= client.createUser(user);
                //println(Log.ASSERT,"USERRESPONSE",userResponse.getId());
                //By UserBuilder
                user= UserBuilder.instance()
                        //.setEmail("rk.pattnayak82@gmail.com")
                        .setEmail(email)
                        //.setLogin("mike2@example.com")
                        .setLogin(login)
                        .setPassword(password)
                        //.setFirstName("Michael1")
                        .setFirstName(firstName)
                        //.setLastName("Carroll")
                        .setLastName(lastName)
                        .setMobilePhone("9999999999")
                        .setSecurityQuestion("Favorite security question?")
                        .setSecurityQuestionAnswer("None of them!")
                        .setActive(true)
                        .buildAndCreate(client);

                //End User Builder
                //By  User method
                //

               /* User userObj=Client.class.newInstance()
                        .createUser(user,true,true);*/


            } catch (ResourceException e) {
                hideLoading();
                Log.e(TAG, Log.getStackTraceString(e));
                AtomicReference<String> error= new AtomicReference<>("ERROR: ");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    e.getCauses().forEach( cause -> error.set(error + cause.getSummary()));
                }
                runOnUIThread(() -> {
                    hideLoading();
                    //showMessageDialog(error.get());
                    showMessageDialog(error.get(),getContext(),false);
                    showMessage(e.getMessage());
                });
            } finally {
                hideLoading();
                if(user!=null) {
                    String userId = user.getId();
                    println("User created with ID: " + userId);

                    // You can look up user by ID
                    println("User lookup by ID: "+ client.getUser(userId).getProfile().getLogin());

                    // or by Email
                    println("User lookup by Email: "+ client.getUser(login).getProfile().getLogin());
                   // userName.set(client.getUser(userId).getProfile().getFirstName());
                    //
                    //String suc_message="User : " + client.getUser(userId).getProfile().getFirstName() + " Created Successfully With User Login " + client.getUser(login).getProfile().getLogin();
                    runOnUIThread(() -> {
                        //showMessage("User : " + client.getUser(userId).getProfile().getFirstName() + " Created Successfully");

                        showMessageDialog("User Create Successfully",getContext(),true);
                    });
                }
            }
        });


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbind the view to free some memory
        unbinder.unbind();
    }
    private static void println(String message) {
        System.out.println(message);
        System.out.flush();
    }
    private void showMessageDialog(String message,Context context,Boolean sucessDialog)
    {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(sucessDialog){
        builder.setTitle("Success");}else
        {
            builder.setTitle("ERROR");
        }
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            dialog.cancel();
            navigation.close();
        });
        builder.create().show();
    }
    public UserProfile setProfileObj()
    {
        UserProfile objUserProfile=null;
        try {
            objUserProfile=UserProfile.class.newInstance()
                    .setEmail("rk.pattnayak82@gmail.com")
                    .setLogin("test@example.com")
                    .setFirstName("test")
                    .setLastName("okta")
                    .setMobilePhone("9999999999");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return objUserProfile;
    }

    public PasswordCredential setPassWord()
    {
        PasswordCredential passwordCredentialObj=null;
        try {
            passwordCredentialObj=PasswordCredential.class.newInstance()
                    .setValue(password);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return passwordCredentialObj;
    }
    public UserCredentials setUserCred()
    {
        UserCredentials userCredObj=null;

        try {
            userCredObj=UserCredentials.class.newInstance()
                    .setPassword(setPassWord());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return userCredObj;
    }

    public User setUser()
    {
        User userObj=null;
        try {
            userObj=User.class.newInstance()
                    .setCredentials(setUserCred())
                    .setProfile(setProfileObj());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return userObj;
    }



}
