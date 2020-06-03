package umn.ac.id.whisper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import umn.ac.id.whisper.Adapter.DiscussionAdapter;
import umn.ac.id.whisper.Adapter.MessageAdapter;

public class MessageActivity extends AppCompatActivity
{
    TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;
    StorageReference storageReference;
    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;
    String DiscussionID;
    ValueEventListener seenListener;

    private SharedPreferences preferences;
    private String UserID;
    public Discussion discussion = new Discussion();
    public Users user = new Users();
    private String NamaLengkap;
    private TextView Diskusi;
    private ImageView image;

    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recycler_viewM);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        Diskusi = findViewById(R.id.DISKUSI);
        image = findViewById(R.id.DiscussionImage);

        intent = getIntent();
        DiscussionID = intent.getStringExtra("DiscussionID");
        discussion.setID(DiscussionID);
        preferences = getPreferences(MODE_PRIVATE);
        UserID = preferences.getString("FirebaseUID", "");
        user.setId(UserID);
        UserID = user.getId();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getId());
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    NamaLengkap = snapshot.child("NamaLengkap").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(DiscussionID, NamaLengkap, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openImage();
            }
        });

        Log.d("MyApplication",  "Sampe Sini 0.4");
        Log.d("MyApplication",  "Sampe Sini 0.5, DID: " + DiscussionID);
        readMesagges(DiscussionID);
    }

    private void sendMessage(String DiscussionID, String nama, String message)
    {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Chat", message);
        hashMap.put("Nama", nama);
        hashMap.put("DiscussionID", DiscussionID);

        reference.push().setValue(hashMap);
    }

    private void readMesagges(final String DiscussionID)
    {
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                Log.d("MyApplication",  "Sampe Sini 0.5");
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Chat chat = new Chat();
                    chat.setSender(snapshot.child("Nama").getValue().toString());
                    chat.setMessage(snapshot.child("Chat").getValue().toString());
                    chat.setDiscussionID(snapshot.child("DiscussionID").getValue().toString());
                    Log.d("MyApplication",  "Sampe Sini 0.9");
                    Log.d("MyApplication",  "CHATNYA ANJING 1: " + chat.getDiscussionID());

                    if (chat.getDiscussionID().equals(DiscussionID))
                    {
                        Log.d("MyApplication",  "Sampe Sini 1");
                        mchat.add(chat);
                    }

                    DatabaseReference DBreference = FirebaseDatabase.getInstance().getReference("Discussions");
                    DBreference.addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                Discussion discussion = new Discussion();
                                discussion.setID(snapshot.child("ID").getValue().toString());
                                discussion.setImage(snapshot.child("Image").getValue().toString());
                                Log.d("MyApplication",  "ID ANJING (Dari FIREBASE): " + snapshot.child("ID").getValue().toString());
                                if(discussion.getID().equals(DiscussionID))
                                {
                                    Log.d("MyApplication",  "DISKUSINYA ANJING: " + snapshot.child("Diskusi").getValue().toString());
                                    Diskusi.setText(snapshot.child("Diskusi").getValue().toString());
                                    Picasso.get().load(discussion.getImage()).into(image);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });

                    Log.d("MyApplication", "Sampe Sini 2");
                    messageAdapter = new MessageAdapter(getApplicationContext(), mchat);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void openImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = MessageActivity.this.getContentResolver();
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

                        reference = FirebaseDatabase.getInstance().getReference("Discussion").child(discussion.getID());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Image", "" + mUri);
                        reference.updateChildren(map);

                        pd.dismiss();
                    }
                    else
                    {
                        Toast.makeText(MessageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(MessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(MessageActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MessageActivity.this, "Upload in preogress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}