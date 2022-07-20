package com.cst2335.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cst2335.myapplication.database.MessageDatabaseSource;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView listChat;
    private Button btnSend, btnReceive;
    private EditText edtMessage;

    private List<Message> listMessage;
    private MessageDatabaseSource messageDatabaseSource;

    @Override
    protected void onResume() {
        messageDatabaseSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        messageDatabaseSource.close();
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        messageDatabaseSource = new MessageDatabaseSource(this);
        messageDatabaseSource.open();
        messageDatabaseSource.fetchAllMessage();

        listChat = findViewById(R.id.listChat);
        btnSend = findViewById(R.id.btnSend);
        btnReceive = findViewById(R.id.btnReceive);
        edtMessage = findViewById(R.id.edtMessage);

        listMessage = messageDatabaseSource.getAllMessages();
        ChatAdappter chatAdappter = new ChatAdappter();
        listChat.setAdapter(chatAdappter);
        listChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
                builder.setTitle("Alert");
                builder.setMessage("Do you want to delete this ?" +
                        "\nThe selected row is: " + (position + 1) +
                        "\nThe database id is: " + chatAdappter.getItemId(position));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        messageDatabaseSource.deleteMessage(chatAdappter.getItemId(position));
                        listMessage.remove(position);
                        chatAdappter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.create().show();
                return false;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = edtMessage.getText().toString().trim();
                Message message = new Message();
                message.setMessage(m);
                message.setSent(true);
                message.setReceived(false);

                listMessage.add(message);
                chatAdappter.notifyDataSetChanged();
                edtMessage.setText("");
                messageDatabaseSource.saveMessage(message);
            }
        });


        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = edtMessage.getText().toString().trim();
                Message message = new Message();
                message.setMessage(m);
                message.setSent(false);
                message.setReceived(true);

                listMessage.add(message);
                chatAdappter.notifyDataSetChanged();
                edtMessage.setText("");
                messageDatabaseSource.saveMessage(message);
            }
        });


    }

    private class ChatAdappter extends BaseAdapter {

        @Override
        public int getCount() {
            return listMessage.size();
        }

        @Override
        public Object getItem(int i) {
            return listMessage.get(i);
        }

        @Override
        public long getItemId(int i) {
            return listMessage.get(i).getId();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parentViewGroup) {
            Message message = (Message) getItem(position);
            if (message.isSent()) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.item_message_sent,
                        null,
                        false);
                TextView textView = convertView.findViewById(R.id.txtMessage);
                textView.setText(message.getMessage());

            } else {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.item_message_received,
                        null,
                        false);
                TextView textView = convertView.findViewById(R.id.txtMessage);
                textView.setText(message.getMessage());
            }

            return convertView;
        }
    }

}