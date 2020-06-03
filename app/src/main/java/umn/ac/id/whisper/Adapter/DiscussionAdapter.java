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

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;

import umn.ac.id.whisper.Discussion;
import umn.ac.id.whisper.MessageActivity;
import umn.ac.id.whisper.R;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder>
{
    private Context mContext;
    private List<Discussion> mDiscussion;

    private String getUserID;


    public DiscussionAdapter(Context context, List<Discussion> mDiscussion)
    {
        this.mDiscussion = mDiscussion;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DiscussionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new DiscussionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Discussion discussion = mDiscussion.get(position);
        holder.title.setText(discussion.getTitle());
        Glide.with(mContext).load(discussion.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("DiscussionID", discussion.getID());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mDiscussion.size();
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
