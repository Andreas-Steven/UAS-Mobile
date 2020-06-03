package umn.ac.id.whisper.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import umn.ac.id.whisper.Adapter.DiscussionAdapter;
import umn.ac.id.whisper.Discussion;
import umn.ac.id.whisper.R;

public class Fragment_discussions extends Fragment
{
    private RecyclerView recyclerView;
    private DiscussionAdapter discussionAdapter;
    private List<Discussion> mDiscussion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discussions, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDiscussion = new ArrayList<>();
        readUsers();


        return view;
    }


    private void readUsers()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Discussions");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d("MyApplication", "Discussion Fragment");
                mDiscussion.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("MyApplication", "Discussion Fragment Inside For");
                    //Discussion discussion = new Discussion();
                    Discussion discussion = snapshot.getValue(Discussion.class);
                    /*discussion.setDiskusi(snapshot.child("Diskusi").getValue().toString());
                    discussion.setID(snapshot.child("ID").getValue().toString());
                    discussion.setImage(snapshot.child("Image").getValue().toString());
                    discussion.setTitle(snapshot.child("Title:").getValue().toString());
                    discussion.setUserID(snapshot.child("UserID").getValue().toString());*/

                    Log.d("MyApplication", "ANJING BANGSAT ID: " + discussion.getID());
                    Log.d("MyApplication", "ANJING BANGSAT Diskusi: " + discussion.getDiskusi());
                    Log.d("MyApplication", "ANJING BANGSAT UserID: " + discussion.getUserID());
                    Log.d("MyApplication", "ANJING BANGSAT Image: " + discussion.getImage());
                    Log.d("MyApplication", "ANJING BANGSAT Title: " + discussion.getTitle());

                    mDiscussion.add(discussion);

                }
                discussionAdapter = new DiscussionAdapter(getContext(), mDiscussion);
                Log.d("MyApplication", "Sampe Sini FRAGMEN DISCUSSION");
                recyclerView.setAdapter(discussionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}