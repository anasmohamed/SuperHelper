package com.anas.superhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.viewmodels.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    @BindView(R2.id.profile_image_textView)
    CircleImageView photo;
    @BindView(R2.id.profile_name_title_textView)
    TextView nameTitle;
    @BindView(R2.id.profile_name_textView)
    TextView name;
    @BindView(R2.id.profile_phone_textView)
    TextView phone;
    @BindView(R2.id.profile_email_textView)
    TextView email;
    @BindView(R2.id.profile_birth_date_textView)
    TextView birthDate;
    @BindView(R2.id.profile_gender_textView)
    TextView gender;

    Unbinder unbinder;

    ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.getUser(this::setViews);
        return view;
    }

    void setViews(User user) {
        if (user != null) {
            nameTitle.setText(user.getFirstName() + " " + user.getLastName());
            name.setText(user.getFirstName() + " " + user.getLastName());
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
            gender.setText(user.getGender());
            birthDate.setText(user.getDate());
            Picasso.with(getActivity()).load(user.getProfileImageURL()).into(photo);
        }
    }

    @OnClick(R.id.sign_out_button)
    void onModifyBtnClick() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), FirstActivity.class));
    }

    @OnClick(R.id.change_profile_image_btn)
    public void onChangeProfileImageBtnClick() {
        Log.i("click", "onChangeProfileImageBtnClick");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 8898);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 8898) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    photo.setImageBitmap(bitmapImage);
                    handleUploadProfileImage(bitmapImage);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private void handleUploadProfileImage(Bitmap bitmap) {
        profileViewModel.updateProfileImage(bitmap);

    }
}