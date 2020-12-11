package com.example.mt_lr3.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mt_lr3.Objects.User;
import com.example.mt_lr3.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment{
    User user;
    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        user = gson.fromJson(bundle.getString("UserJson"), User.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_details, container, false);
        TextView firstName = v.findViewById(R.id.tvFirstName);
        TextView lastName = v.findViewById(R.id.tvLastName);
        TextView phone = v.findViewById(R.id.tvPhoneNumber);
        TextView email = v.findViewById(R.id.tvEmail);
        TextView jobTitle = v.findViewById(R.id.tvJobTitle);
        TextView department = v.findViewById(R.id.tvDepartment);
        TextView company = v.findViewById(R.id.tvCompany);
        ImageView avatar = v.findViewById(R.id.ivAvatar);
        ImageView emailVerified = v.findViewById(R.id.ivEmailVerified);

        if(user != null){
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
            jobTitle.setText(user.getJobTitle());
            department.setText(user.getDepartment());
            company.setText(user.getCompanyName());
            Picasso.get().load(user.getAvatar()).into(avatar);

            if(user.getEmailVerified()){
                emailVerified.setImageResource(R.drawable.email_verified);
            } else {
                emailVerified.setImageResource(R.drawable.email_non_verified);
            }
        } else {
            Log.d("Details", "user is null");
        }



        return v;
    }
}