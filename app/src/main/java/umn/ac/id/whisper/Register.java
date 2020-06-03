package umn.ac.id.whisper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity
{
    private EditText NamaLengkap, NIM, Email, Password, ComfirmPassword;
    private Button Register;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    final static String TAG = "MyApplication";

    private String nama, nim, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        NamaLengkap = findViewById(R.id.Nama);
        NIM = findViewById(R.id.NIM);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ComfirmPassword = findViewById(R.id.ConfirmPassword);
        Register = findViewById(R.id.btnRegister);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nama = NamaLengkap.getText().toString();
                nim = NIM.getText().toString();
                email = Email.getText().toString();
                password = Password.getText().toString();
                confirmPassword = ComfirmPassword.getText().toString();

                if(TextUtils.isEmpty(nama))
                {
                    NamaLengkap.setError("Nama tidak boleh kosong");
                }
                if(TextUtils.isEmpty(nim))
                {
                    NIM.setError("Nama tidak boleh kosong");
                }
                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Nama tidak boleh kosong");
                }
                if(TextUtils.isEmpty(password))
                {
                    Password.setError("Nama tidak boleh kosong");
                }
                if(password.length() < 6)
                {
                    Password.setError("Minimal password 6 huruf");
                }

                if(password.equals(confirmPassword))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                assert firebaseUser != null;
                                String userid = firebaseUser.getUid();

                                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("ID", userid);
                                hashMap.put("NamaLengkap", nama);
                                hashMap.put("NIM", nim);
                                hashMap.put("Email", email);
                                hashMap.put("ImageURL", "https://firebasestorage.googleapis.com/v0/b/whisper-7d7b3.appspot.com/o/ProfileImages%2FNoPic.jpg?alt=media&token=48f4a98f-2ddf-44d7-b71d-d7fda2e9d323");

                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(Register.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(Register.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


}
