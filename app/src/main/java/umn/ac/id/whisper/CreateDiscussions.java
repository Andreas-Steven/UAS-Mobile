package umn.ac.id.whisper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Random;

public class CreateDiscussions extends AppCompatActivity
{
    private EditText DiscussionTitle, DiscussionDescription;
    private Button Create;
    DatabaseReference reference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private String Title, Description, Image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_discussions);

        DiscussionTitle = findViewById(R.id.DisTitle);
        DiscussionDescription = findViewById(R.id.textArea_information);
        Create = findViewById(R.id.btnCreate);

        Bundle extras = getIntent().getExtras();
        final String UserID = extras.getString("UID");

        Create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Title = DiscussionTitle.getText().toString();
                Description = DiscussionDescription.getText().toString();
                Image = "https://firebasestorage.googleapis.com/v0/b/whisper-7d7b3.appspot.com/o/TitleImage%2FDiscussion.png?alt=media&token=93c690d5-b4a2-49d1-9fc9-2ca4a0b1a09b";

                if(TextUtils.isEmpty(Title))
                {
                    DiscussionTitle.setError("Judul tidak boleh kosong");
                }
                if(TextUtils.isEmpty(Description))
                {
                    DiscussionDescription.setError("Deskripsi tidak boleh kosong");
                }

                String RandomID = getRandomString(28);

                reference = FirebaseDatabase.getInstance().getReference("Discussions").child(RandomID);;
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Diskusi", Description);
                hashMap.put("ID", RandomID);
                hashMap.put("Image", Image);
                hashMap.put("Title", Title);
                hashMap.put("UserID", UserID);

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(CreateDiscussions.this, Home.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        //DiscussionImage = findViewById(R.id.imageView);

        /*DiscussionImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openImage();
            }
        });*/
    }

    private String getRandomString(int i)
    {
        final String characters = "abcdefghijklmnopqrtuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_";
        StringBuilder result = new StringBuilder();
        while(i > 0)
        {
            Random rand = new Random();
            result.append(characters.charAt(rand.nextInt(characters.length())));
            i--;
        }

        return result.toString();
    }

    /*private void openImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage()
    {
        final ProgressDialog pd = new ProgressDialog(getApplicationContext());
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child("DiscussionImages").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  task.getException();
                    }

                    return  fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        reference = FirebaseDatabase.getInstance().getReference("Discussions");
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Image", "" + mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(), "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }*/
}