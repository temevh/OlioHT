package com.example.httesti;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginFragment extends Fragment {
    EditText username, password;
    Button btnlogin;
    DBManager Users;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container,false);
        MainActivity.getInstance().setTitle("Kirjaudu");
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        username = (EditText) view.findViewById(R.id.username1);
        password = (EditText) view.findViewById(R.id.password1);
        btnlogin = (Button) view.findViewById(R.id.btnsignin1);
        Users = new DBManager(getContext().getApplicationContext());
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(getContext().getApplicationContext(), "Täytä kaikki kentät ensin", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = Users.checkpassword(user, pass);
                    if(checkuserpass==true){
                        Toast.makeText(getContext().getApplicationContext(), "Kirjautuminen onnistui", Toast.LENGTH_SHORT).show();
                        Boolean profile = Users.checkusername(user, "profiles");
                        if(profile){
                            User currentUser = Users.fetchUser(user);
                            if(currentUser != null){
                                MainActivity.getInstance().loadFragment(new accFragment(), currentUser);
                            }else{
                                Toast.makeText(getContext().getApplicationContext(), "Jokin meni vikaan!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            User newUser = new User(user);
                            MainActivity.getInstance().loadFragment(new profileCreationFragment(), newUser);
                        }


                    }else{
                        Toast.makeText(getContext().getApplicationContext(), "Väärät kirjautumistiedot", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
