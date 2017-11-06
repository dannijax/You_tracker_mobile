package com.oluwadara.youtrackmobile.app.ui.register;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oluwadara.youtrackmobile.app.YouTrackMobileApp;
import com.oluwadara.youtrackmobile.app.data.model.User;
import com.oluwadara.youtrackmobile.app.ui.tracker.HomeActivity;
import com.oluwadara.youtrackmobile.app.youtrackmobileapp.R;

public class RegisterFragment extends Fragment {

    public static final String TAG = RegisterFragment.class.getName();

    private EditText mPasswordText;
    private EditText mEmailText;
    private EditText mFirstNameText;
    private EditText mLastNAmeText;
    private EditText mPhoneText;
    private Button mRegisterButton;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    public static RegisterFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.sign_up_layout, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        mPasswordText = view.findViewById(R.id.password);
        mEmailText = view.findViewById(R.id.email);
        mFirstNameText = view.findViewById(R.id.first_name);
        mLastNAmeText = view.findViewById(R.id.last_name);
        mPhoneText = view.findViewById(R.id.phone);
        mRegisterButton = view.findViewById(R.id.sign_up_btn);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterButton.setEnabled(false);
                Log.e(TAG, "onClick: " );
                final String email = mEmailText.getText().toString().trim();
                String password = mEmailText.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.e(TAG, "onComplete: "+ task.toString() );
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String id = user.getUid();
                                    User signUpUser = new User();
                                    signUpUser.setId(id);
                                    signUpUser.setEmail(email);
                                    signUpUser.setFirstName(mFirstNameText.getText().toString().trim());
                                    signUpUser.setLastName(mLastNAmeText.getText().toString().trim());
                                    signUpUser.setPhoneNumber(mPhoneText.getText().toString().trim());
                                    DatabaseReference myRef = mDatabase.getReference("users");
                                    myRef.child(id).setValue(signUpUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mRegisterButton.setEnabled(true);
                                            Intent intent = new Intent(getContext(), HomeActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                            Log.e(TAG, "onComplete: " + task.toString() );

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    mRegisterButton.setEnabled(true);

                                                    Log.e(TAG, "onFailure: " + e.getMessage() );
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mRegisterButton.setEnabled(true);
                            Log.e(TAG, "onFailure: " + e.getMessage() );
                        }
                    });

                }
                else {
                    mRegisterButton.setEnabled(true);
                    Toast.makeText(getContext(), "Email or password is Empty", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void requestPermission() {

    }
}
