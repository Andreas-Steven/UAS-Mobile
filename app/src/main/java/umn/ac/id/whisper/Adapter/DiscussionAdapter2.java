package umn.ac.id.whisper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;

import umn.ac.id.whisper.Discussion;
import umn.ac.id.whisper.MessageActivity;
import umn.ac.id.whisper.R;

public class DiscussionAdapter2 extends RecyclerView.Adapter<DiscussionAdapter2.ViewHolder>
{
    private Context mContext;
    private List<Discussion> mDiscussion;

    private String getUserID;


    public DiscussionAdapter2(Context context, List<Discussion> mDiscussion)
    {
        this.mDiscussion = mDiscussion;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DiscussionAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new DiscussionAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Discussion discussion = mDiscussion.get(position);
        holder.title.setText(discussion.getTitle());
        Glide.with(mContext).load(discussion.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount()
    {
        return mDiscussion.size();
    }

    public void deleteItem(int position)
    {
        final Discussion discussion = mDiscussion.get(position);
        FirebaseDatabase.getInstance().getReference("Discussions").child(discussion.getID()).removeValue();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public ImageView image;

        public ViewHolder(View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.username);
            image = itemView.findViewById(R.id.profile_image);
        }

    }
}
