package com.example.firebasesearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    FirebaseRecyclerAdapter<Users, UserViewHolder> mFirebaseRecyclerAdapter;
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mSearchList;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchBtn = (ImageButton) findViewById(R.id.SearchBtn);
        mSearchField = (EditText) findViewById(R.id.searchField);
        mSearchList = (RecyclerView) findViewById(R.id.searchList);

        mSearchList.setHasFixedSize(true);
        mSearchList.setLayoutManager(new LinearLayoutManager(this));
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
            }
        });

        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                if (query.length() > 0) {
                    firebaseUserSearch(charSequence.toString());
                } else {
                    mSearchList.setAdapter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void firebaseUserSearch(String searchText) {

        if (mFirebaseRecyclerAdapter != null) {
            mFirebaseRecyclerAdapter.stopListening();
        }

        Toast.makeText(MainActivity.this, "Started Search", Toast.LENGTH_LONG).show();
        Query firebaseSearchQuery = mUserDatabase.orderByChild("nameLower").startAt(searchText.toLowerCase()).endAt(searchText.toLowerCase() + "\uf8ff");
        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(firebaseSearchQuery, Users.class)
                .build();

        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
                holder.setDetails(getApplicationContext(), model.getName(), model.getStatus(), model.getImage());
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_item, parent, false);
                return new UserViewHolder(view);
            }
        };

        mSearchList.setAdapter(mFirebaseRecyclerAdapter);

        if (mFirebaseRecyclerAdapter != null) {
            mFirebaseRecyclerAdapter.startListening();
        }
    }

    // View Holder Class

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Context ctx, String userName, String userStatus, String userImage) {

            TextView user_name = (TextView) mView.findViewById(R.id.nameText);
            TextView user_status = (TextView) mView.findViewById(R.id.statusText);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profileImage);

            user_name.setText(userName);
            user_status.setText(userStatus);
            Glide.with(ctx).load(userImage).into(user_image);
        }
    }
}