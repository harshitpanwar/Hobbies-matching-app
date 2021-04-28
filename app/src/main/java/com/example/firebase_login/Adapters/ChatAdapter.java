package com.example.firebase_login.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_login.Models.Messages;
import com.example.firebase_login.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Messages> messages;
    Context context;


    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;



    public ChatAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        }

        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false);
            return new RecieverViewHolder(view);

        }

    }


    @Override
    public int getItemViewType(int position) {

        if(messages.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
            return SENDER_VIEW_TYPE;
        else
            return RECEIVER_VIEW_TYPE;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messagemodel = messages.get(position);

        if(holder.getClass() == SenderViewHolder.class){

            ((SenderViewHolder)holder).sendMsg.setText(messagemodel.getMessage());



        }

        else {

            ((RecieverViewHolder)holder).reciverMsg.setText(messagemodel.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView reciverMsg , receiverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            reciverMsg = itemView.findViewById(R.id.recieverText);
            receiverTime = itemView.findViewById(R.id.recieverTime);


        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView sendMsg , sendTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sendMsg = itemView.findViewById(R.id.senderText);
            sendTime = itemView.findViewById(R.id.senderTime);


        }
    }

}
