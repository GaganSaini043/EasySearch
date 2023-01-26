package app.quickfood.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import app.quickfood.R;
import app.quickfood.model.LoginModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText username;

    @BindView(R.id.et_password)
    EditText password;

    @BindView(R.id.btn_login)
    Button login;

    @BindView(R.id.btn_signup)
    Button signUp;

    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore ff;

    DatabaseReference databaseReference;

    LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        ff= FirebaseFirestore.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("LoginModel");

        loginModel= new LoginModel();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName= username.getText().toString();
                String pswrd = password.getText().toString();

                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pswrd)){
                    Toast.makeText(LoginActivity.this,"Please fill the fields" , Toast.LENGTH_SHORT).show();
                }
                else{
                    onLoginClick(userName,pswrd,"login");
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName= username.getText().toString();
                String pswrd = password.getText().toString();

                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pswrd)){
                    Toast.makeText(LoginActivity.this,"Please fill the fields" , Toast.LENGTH_SHORT).show();
                }
                else{
                    onLoginClick(userName,pswrd,"signup");
                }
            }
        });
    }

    public void onLoginClick(String username,String pswrd, String type){

        CollectionReference cRef = ff.collection("users");
        Query query = cRef.whereEqualTo("username",username);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        String user = documentSnapshot.getString("username");
                        String password = documentSnapshot.getString("password");

                        if (user.equals(username) && password.equals((pswrd))) {

                            if(type.equalsIgnoreCase("login")){
//                                Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
//                                startActivity(intent);
                            }
                            else if(type.equalsIgnoreCase("signup")){
                                Log.d("success22", "User Exists");
                                Toast.makeText(LoginActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if(task.getResult().size() == 0 ){
                    if(type.equalsIgnoreCase("login")){
                        Toast.makeText(LoginActivity.this, "User Does not Exists", Toast.LENGTH_SHORT).show();

                    }
                    else if(type.equalsIgnoreCase("signup")){
                        Log.d("failure22", "User not Exists");
                        addDataToFirebase(username,pswrd);
                    }

                }
            }
        });

    }

    public void addDataToFirebase(String username, String password){

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("password", password);

            ff.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Log.d("added", "DocumentSnapshot successfully written!");
//                    Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
//                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                }
            })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("failure", "Error writing document!");
                        Toast.makeText(LoginActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}