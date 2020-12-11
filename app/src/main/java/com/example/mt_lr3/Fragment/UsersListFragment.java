package com.example.mt_lr3.Fragment;

import android.app.backup.BackupDataInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.mt_lr3.MainActivity;
import com.example.mt_lr3.Network.Service;
import com.example.mt_lr3.R;
import com.example.mt_lr3.Objects.User;
import com.example.mt_lr3.Objects.Users;
import com.example.mt_lr3.db.AppDatabase;
import com.example.mt_lr3.db.UserDao;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends ListFragment {
    User[] users;
    MyListAdapter adapter;
    AppDatabase db;
    boolean hasInternet;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null) {
            setRetainInstance(true);
            db = MainActivity.getInstance().getDatabase();
            UserDao userDao = db.userDao();

            Service.getInstance()
                    .getJSONApi()
                    .getAllUsers()
                    .enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            List<User> userList = response.body().getUsers();
                            users = new User[userList.size()];
                            userList.toArray(users);
                            adapter = new MyListAdapter(getActivity(), R.layout.user_in_list, users);
                            setListAdapter(adapter);
                            hasInternet = true;
                            for(User u : userList){
                                if(userDao.getCurrent(u.getId()).size() == 0){
                                    userDao.insertAll(u);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Log.d("Response", "response fail: " + t.getMessage());
                            List<User> _users = userDao.getAll();
                            if(_users.size() > 0){
                                users = new User[_users.size()];
                                _users.toArray(users);
                                adapter = new MyListAdapter(getActivity(),R.layout.user_in_list, users);
                                setListAdapter(adapter);
                                hasInternet = false;
                            } else {
                                Toast.makeText(getContext(), "Connection error. No cached users. Please try again later", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Gson gson = new Gson();
            users = gson.fromJson(savedInstanceState.getString("UsersList"), User[].class);
            adapter = new MyListAdapter(getActivity(), R.layout.user_in_list, users);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        outState.putString("UsersList", json);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DetailsFragment detailsFragment = new DetailsFragment();
        Gson gson = new Gson();
        String json = gson.toJson(users[position]);
        Bundle b = new Bundle();
        b.putString("UserJson", json);

        detailsFragment.setArguments(b);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listFragment, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public class MyListAdapter extends ArrayAdapter {
        private Context context;
        int ResourceId;

        public MyListAdapter(Context _context, int _ResourceId, User[] users){
            super(_context, _ResourceId, users);
            context = _context;
            ResourceId = _ResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(ResourceId, parent,
                    false);


            TextView firstName = (TextView) row.findViewById(R.id.tvFirstName);
            TextView lastName = (TextView) row.findViewById(R.id.tvLastName);
            TextView phoneNumber = row.findViewById(R.id.tvPhoneNumber);
            ImageView avatar = row.findViewById(R.id.ivAvatar);
            firstName.setText(users[position].getFirstName());
            lastName.setText(users[position].getLastName());
            phoneNumber.setText(users[position].getPhone());
            Picasso.get().load(users[position].getAvatar()).into(avatar);

//            imageDownload(users[position].getAvatar(), users[position].getId().toString());
            return row;
        }

//        //save image
//        public void imageDownload(String url, String path){
//            Picasso.get().load(url)
//                    .into(getTarget(path));
//        }
//
//        //target to save
//        private Target getTarget(final String path){
//            Target target = new Target(){
//
//                @Override
//                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + path);
//                            try {
//                                file.createNewFile();
//                                FileOutputStream ostream = new FileOutputStream(file);
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
//                                ostream.flush();
//                                ostream.close();
//                            } catch (IOException e) {
//                                Log.e("IOException", e.getLocalizedMessage());
//                            }
//                        }
//                    }).start();
//
//                }
//
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            };
//            return target;
//        }
    }
}
