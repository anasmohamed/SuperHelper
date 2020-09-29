package com.anas.superhelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.LoginRepository;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
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

    private LoginRepository loginRepository=new LoginRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);
        loginRepository.getUser(this::setViews);
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
        startActivity(new Intent(this, FirstActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}