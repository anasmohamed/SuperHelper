package com.anas.superhelper.auth.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.anas.superhelper.MainActivity;
import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.viewmodels.SignUpViewModel;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class SignUpLastPageFragment extends Fragment {
    Button signUpBtn, takePhotoBtn;
    EditText phoneNumberET, dateET;
    User user;
    int TAKE_PHOTO_CODE = 0;
    int REQUEST_CAMERA_PERMISSION = 1;
    boolean isIdImageTaken = false;
    Bitmap photo;
    private SignUpViewModel signUpViewModel;
    private RadioGroup radioGenderGroup;
    private RadioButton radioButton;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_last_page, container, false);

        signUpBtn = view.findViewById(R.id.sign_up_btn_sign_up_last_page);
        phoneNumberET = view.findViewById(R.id.phone_number_et_sign_up_last_page);
        dateET = view.findViewById(R.id.date_of_birth_sign_up_last_page);
        radioGenderGroup = view.findViewById(R.id.gender_radio_group_sign_up_last_page);
        takePhotoBtn = view.findViewById(R.id.take_photo_id_card_btn);
        dateET.setKeyListener(null);
        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                int selectedId = radioGenderGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        user = getArguments().getParcelable("user");
        user.setProfileImageURL("https://firebasestorage.googleapis.com/v0/b/superhelper-568ba.appspot.com/o/profileImages%2Funnamed.png?alt=media&token=ed3cbfc9-2d0a-4c15-9b41-6c9dda375da1");
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        Log.i("user object", "onCreateView: " + user.getFirstName());
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberET.getText().toString().isEmpty()) {
                    phoneNumberET.setError(getString(R.string.enter_data_correctly));
                } else if (dateET.getText().toString().isEmpty()) {
                    dateET.setError(getString(R.string.enter_data_correctly));

                } else if (!isIdImageTaken) {
                    Toast.makeText(getContext(), getString(R.string.please_upload_image), Toast.LENGTH_LONG).show();
                } else {
                    user.setDate(dateET.getText().toString());
                    user.setPhone(phoneNumberET.getText().toString());
                    user.setGender(radioButton.getText().toString());
                    signUpViewModel.signUp(user, s -> {
                        if (s.equalsIgnoreCase("true")) {
                            signUpViewModel.uploadIdImage(photo, user.getEmail());
                            startActivity(new Intent(getActivity(), MainActivity.class));

                        } else {
//                            Somthing went wrong please see your data
                            Toast.makeText(getContext(), "" + s, Toast.LENGTH_LONG).show();
                        }

                    });

                }
            }
        });

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCameraPermission();
            }
        });
        return view;
    }

    void isSignUpSuccessfull(String SignUpStatus) {

    }

    //+10 changed its sinature as Fragment; without it  onRequestPermissionsResult won't bbe called
    private void myCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            takePicture();
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

        }
    }

    private void takePicture() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_PERMISSION && requestCode == RESULT_OK) {
            takePicture();

        }
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.d("CameraDemo", data.getExtras().get("data").toString());
            isIdImageTaken = true;
            photo = (Bitmap) data.getExtras()
                    .get("data");
            Log.d("CameraDemo", photo.toString());
//
//            user.setIdImage(data.getExtras()
//                    .get("data"));
        }
    }
}