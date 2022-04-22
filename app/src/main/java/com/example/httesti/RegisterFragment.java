package com.example.httesti;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RegisterFragment extends Fragment {

    EditText username, password, repassword;
    Button signup, signin;
    DBManager Users;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container,false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        repassword = (EditText) view.findViewById(R.id.repassword);
        signup = (Button) view.findViewById(R.id.btnsignup);
        signin = (Button) view.findViewById(R.id.btnsignin);
        Users = new DBManager(getContext().getApplicationContext());


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(getContext().getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkuser = Users.checkusername(user);
                        if(checkuser==false){
                            Boolean insert = Users.insertData(user, pass);
                            if(insert==true){
                                Toast.makeText(getContext().getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
                            }else{
                                Toast.makeText(getContext().getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext().getApplicationContext(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext().getApplicationContext(), "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
            }
        });
    }

}
