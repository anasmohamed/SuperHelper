package com.anas.superhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.LoginRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    @BindView(R.id.profile_image_textView)
    CircleImageView photo;
    @BindView(R.id.profile_name_title_textView)
    TextView nameTitle;
    @BindView(R.id.profile_name_textView)
    TextView name;
    @BindView(R.id.profile_phone_textView)
    TextView phone;
    @BindView(R.id.profile_email_textView)
    TextView email;
    @BindView(R.id.profile_birth_date_textView)
    TextView birthDate;
    @BindView(R.id.profile_gender_textView)
    TextView gender;


    Unbinder unbinder;

    private LoginRepository loginRepository = new LoginRepository();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(getActivity());
        loginRepository.getUser(this::setViews);
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
//            Picasso.with(this).load(Uri.parse(null)).into(photo);
        }
    }

    @OnClick(R.id.sign_out_button)
    void onModifyBtnClick() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), FirstActivity.class));
    }

    @OnClick(R.id.change_profile_image_btn)
    void onChangeProfileImageBtnClick(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0)
        {
            switch (requestCode){
                case RESULT_OK :
                    
            }

        }
    }
}