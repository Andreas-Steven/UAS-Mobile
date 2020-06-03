package umn.ac.id.whisper.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import umn.ac.id.whisper.Chat;
import umn.ac.id.whisper.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    private Context mContext;
    private List<Chat> mChat;

    public MessageAdapter(Context mContext, List<Chat> mChat){
        Log.d("MyApp", "Sampe Sini 3");
        this.mChat = mChat;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MyApp", "Sampe Sini 4");
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MyApp", "Sampe Sini 5");
        Chat chat = mChat.get(position);
        holder.Nama.setText(chat.getSender());
        Log.d("MyApp", "CHATNYA ANJING: " + chat.getMessage());
        holder.show_message.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Nama;
        public ImageView profile_image;
        public TextView show_message;

        public ViewHolder(View itemView)
        {
            super(itemView);
            Log.d("MyApp", "Sampe Sini 3");
            Nama = itemView.findViewById(R.id.nama);
            profile_image = itemView.findViewById(R.id.profile_image);
            show_message = itemView.findViewById(R.id.show_message);
        }
    }
}
