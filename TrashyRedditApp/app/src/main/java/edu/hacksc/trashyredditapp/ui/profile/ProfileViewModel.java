package edu.hacksc.trashyredditapp.ui.profile;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;
//    private MutableLiveData<String> mFirst;
//    private MutableLiveData<String> mLast;
//    private MutableLiveData<String> mEmail;
//    private MutableLiveData<String> mPassword;

    String mFirst;
    String mLast;
    String mEmail;
    String mPassword;

    Profile myRef;



    public ProfileViewModel() {
        myRef = new Profile(mFirst, mLast, mEmail, mPassword);

        //mText = new MutableLiveData<>();

        //email_i = i.getStringExtra("email");
        //password_i = i.getStringExtra("password");

        //mFirst.setValue(myRef.Profile.first);



    }

    public LiveData<String> getText() {
        return mText;
    }
}