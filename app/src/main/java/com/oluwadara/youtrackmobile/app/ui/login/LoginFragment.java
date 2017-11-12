package com.oluwadara.youtrackmobile.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.oluwadara.youtrackmobile.app.YouTrackMobileApp;
import com.oluwadara.youtrackmobile.app.ui.tracker.HomeActivity;
import com.oluwadara.youtrackmobile.app.ui.tracker.TrackerActivity;
import com.oluwadara.youtrackmobile.app.youtrackmobileapp.R;


public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getName();

    private EditText mPasswordText;
    private EditText mEmailText;
    private Button mSignInButton;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    public static LoginFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = ((YouTrackMobileApp)getActivity().getApplication()).getmAuth();
        mDatabase = ((YouTrackMobileApp)getActivity().getApplication()).getmDataBase();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_layout, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        mPasswordText = view.findViewById(R.id.password);
        mEmailText = view.findViewById(R.id.email);
        mSignInButton = view.findViewById(R.id.sign_in_btn);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignInButton.setEnabled(false);
                String email = mEmailText.getText().toString().trim();
                String password = mEmailText.getText().toString().trim();
                Log.e(TAG, "onClick: " + email + password );
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(getContext(), "successfully signed in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mSignInButton.setEnabled(true);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure: " + e.getMessage());
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Email or password is Empty", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}
