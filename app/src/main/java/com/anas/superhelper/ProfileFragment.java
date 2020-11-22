package com.anas.superhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

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
    ProfileViewModel profileViewModel;
    private int mYear, mMonth, mDay;
    RadioButton radioButton;
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
//            Picasso.with(getActivity()).load(user.getProfileImageURL()).into(photo);
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

    @OnClick(R.id.date_of_birth_edit_btn)
    void onDataOfBirthEditBtnClick() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        birthDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        profileViewModel.updateUserProfileData("date", birthDate.getText().toString());
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    @OnClick({R.id.name_edit_btn})
    void onNameEditBtnClick() {
        showAddItemDialog("name");
    }


    @OnClick({R.id.mobile_edit_btn})
    void onMobileEditBtnClick() {
        showAddItemDialog("mobile number");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

    private void showChangeGenderDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.gender_change_dialog, null);
        TextView titleTextView = (TextView) dialogView.findViewById(R.id.textView);
        RadioGroup radioGenderGroup = dialogView.findViewById(R.id.gender_radio_group_sign_up_last_page);
        titleTextView.setText("Update Gender");
        Button updateBtn = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.buttonCancel);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGenderGroup.getCheckedRadioButtonId();

                radioButton = dialogView.findViewById(selectedId);

                profileViewModel.updateUserProfileData("gender", radioButton.getText().toString());
                dialogBuilder.dismiss();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void showAddItemDialog(String title) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        TextView titleTextView = (TextView) dialogView.findViewById(R.id.textView);
        final EditText firstEditText = (EditText) dialogView.findViewById(R.id.first_edit_text);
        final EditText secondEditText = (EditText) dialogView.findViewById(R.id.second_edit_text);

        titleTextView.setText("Update " + title);
        Button updateBtn = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.buttonCancel);
        switch (title) {
            case "name":
                secondEditText.setVisibility(View.VISIBLE);
                firstEditText.setHint(R.string.first_name);
                secondEditText.setHint(R.string.second_name);
                break;
            case "mobile number":
                firstEditText.setHint(R.string.mobile);
                break;
        }


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (title) {
                    case "name":
                        profileViewModel.updateUserProfileData("firstName", firstEditText.getText().toString());
                        profileViewModel.updateUserProfileData("lastName", secondEditText.getText().toString());
                        break;
                    case "mobile number":
                        profileViewModel.updateUserProfileData("phone", firstEditText.getText().toString());
                        break;

                }
                dialogBuilder.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    @OnClick(R.id.gender_edit_btn)
    void onGenderEditBtnClick() {
        showChangeGenderDialog();
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