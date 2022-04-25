package com.example.httesti;

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

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    EditText username, password, repassword;
    Button signup, signin;

    DBManager Users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container,false);
        MainActivity.getInstance().setTitle("Register");
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        username = (EditText) view.findViewById(R.id.name);
        password = (EditText) view.findViewById(R.id.password);
        repassword = (EditText) view.findViewById(R.id.repass);
        signup = (Button) view.findViewById(R.id.btnsignup);
        signin = (Button) view.findViewById(R.id.btnsave);

        //DBManager for storing the users (uses SQLite)
        Users = new DBManager(getContext().getApplicationContext());


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                // Regular expression pattern for checking if the password fulfills the requirements
                String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=()!])(?=\\S+$).{12,30}$";
                Pattern pattern = Pattern.compile(regex);

                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(getContext().getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pattern.matcher(pass).matches()){
                        if(pass.equals(repass)){
                            Boolean checkuser = Users.checkusername(user);
                            if(checkuser==false){
                                Boolean insert = Users.insertUser(user, pass);
                                if(insert==true){
                                    Toast.makeText(getContext().getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
                                    MainActivity.getInstance().setTitle("Login");
                                }else{
                                    Toast.makeText(getContext().getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(getContext().getApplicationContext(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getContext().getApplicationContext(), "Passwords not matching", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(getContext().getApplicationContext(), "Passwords does not fulfill requirements", Toast.LENGTH_SHORT).show();
                    }

                } }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = MainActivity.getInstance().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new LoginFragment()).commit();
                MainActivity.getInstance().setTitle("Login");
            }
        });
    }

}
