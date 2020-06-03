package umn.ac.id.whisper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
{
    /* https://stackoverflow.com/questions/43491554/android-how-to-save-firebase-uid-onto-sharedpreferences
        sharedPref = getPreferences(MODE_PRIVATE);
        String UserId = sharedPref.getString("FirebaseUID", "");
    */
    /* https://stackoverflow.com/questions/5265913/how-to-use-putextra-and-getextra-for-string-data
        Bundle extras = getIntent().getExtras();
        String UserID = extras.getString("UID");
     */
    /*
    * https://developer.android.com/guide/topics/ui/floating-action-button
    * */
    private EditText Email, Password;
    private Button Login;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    final static  String TAG = "MyApplication";
    private String email, password;
    private CheckBox Remember;
    private SharedPreferences preferences;
    private String getUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Login = findViewById(R.id.btnLogin);
        Remember = findViewById(R.id.RememberMe);
        firebaseAuth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true"))
        {
            preferences = getPreferences(MODE_PRIVATE);
            String UserID = preferences.getString("FirebaseUID", "");
            Intent intent = new Intent(MainActivity.this, Home.class);
            intent.putExtra("UID", UserID);
            startActivity(intent);
        }
        else if(checkbox.equals("false"))
        {
            //Toast.makeText(MainActivity.this, "Silahkan login terlebih dahulu untuk melanjutkan", Toast.LENGTH_LONG).show();
        }

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = Email.getText().toString().trim();
                password = Password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null)
                            {
                                getUserID = user.getUid();
                                preferences = getPreferences(MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("FirebaseUID", getUserID);
                                editor.commit();
                                Log.d(TAG, "Isi UserID Dari Firebase 1: " + getUserID);
                            }

                            Intent intent = new Intent(MainActivity.this, Home.class);
                            intent.putExtra("UID", getUserID);
                            Log.d(TAG, "Isi UserID Dari Firebase 2: " + getUserID);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Email atau Password Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (buttonView.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                }
                else if (!buttonView.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });
    }


    // Text View Registration
    public void TextRegister(View view)
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}
