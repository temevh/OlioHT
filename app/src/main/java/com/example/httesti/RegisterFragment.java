package com.example.httesti;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    EditText username, password, repassword;
    Button signup, signin;
    TextView upperLower, numeric, special, length;

    DBManager Users;

    // Regular expression patterns for checking if the password fulfills the requirements
    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=()!])(?=\\S+$).+$";
    Pattern pattern = Pattern.compile(regex);

    String regexForCases = "^(?=.*[a-z])(?=.*[A-Z]).+$";
    Pattern casePattern = Pattern.compile(regexForCases);

    String regexNum= "^(?=.*\\d).+$";
    Pattern numPattern = Pattern.compile(regexNum);

    String regexSpec = "^(?=.*[@#$%^&+=()!]).+$";
    Pattern specPattern = Pattern.compile(regexSpec);

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

        upperLower = (TextView) view.findViewById(R.id.upperAndLower);
        numeric = (TextView) view.findViewById(R.id.numeric);
        special = (TextView) view.findViewById(R.id.special);
        length = (TextView) view.findViewById(R.id.length);

        //DBManager for storing the users (uses SQLite)
        Users = new DBManager(getContext().getApplicationContext());

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence pass, int i, int i1, int i2) {

                if(pass.length()>=12){
                    length.setTextColor(Color.GREEN);
                }
                else{
                    length.setTextColor(Color.BLACK);
                }
                if(casePattern.matcher(pass).matches()){
                    upperLower.setTextColor(Color.GREEN);
                }
                else{
                    upperLower.setTextColor(Color.BLACK);
                }
                if(numPattern.matcher(pass).matches()){
                    numeric.setTextColor(Color.GREEN);
                }
                else{
                    numeric.setTextColor(Color.BLACK);
                }
                if(specPattern.matcher(pass).matches()){
                    special.setTextColor(Color.GREEN);
                }else{
                    special.setTextColor(Color.BLACK);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(user.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(getContext().getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pattern.matcher(pass).matches() && pass.length()>=12){
                        if(pass.equals(repass)){
                            Boolean checkuser = Users.checkusername(user, "users");
                            if(!checkuser){
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
