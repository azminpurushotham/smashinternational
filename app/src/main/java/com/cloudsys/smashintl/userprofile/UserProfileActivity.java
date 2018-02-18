package com.cloudsys.smashintl.userprofile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.main.MainActivity;
import com.cloudsys.smashintl.utiliti.ImageUtility;
import com.cloudsys.smashintl.utiliti.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.cloudsys.smashintl.userprofile.Presenter.REQUEST_CAMERA;
import static com.cloudsys.smashintl.userprofile.Presenter.SELECT_FILE;
import static com.cloudsys.smashintl.userprofile.Presenter.userChoosenTask;

/**
 * Created by azmin on 16/2/18.
 */

public class UserProfileActivity extends AppBaseActivity implements ActionView, View.OnClickListener {

    private static final String TAG = "UserProfileActivity";
    @BindView(R.id.BTNsubmit)
    Button BTNsubmit;
    @BindView(R.id.parent)
    RelativeLayout parent;
    @BindView(R.id.LAYnointernet)
    ConstraintLayout LAYnointernet;
    @BindView(R.id.BTN_try)
    Button BTN_try;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.TVtitle)
    TextView TVtitle;

    @BindView(R.id.mCircleImageView)
    CircleImageView mCircleImageView;
    @BindView(R.id.BTNadd)
    Button BTNadd;
    @BindView(R.id.CHKchangePassword)
    CheckBox CHKchangePassword;
    @BindView(R.id.lay2)
    LinearLayout lay2;
    @BindView(R.id.EDToldPassword)
    EditText EDToldPassword;
    @BindView(R.id.EDTnewpassword)
    EditText EDTnewpassword;
    @BindView(R.id.EDTconfirmPassword)
    EditText EDTconfirmPassword;
    @BindView(R.id.TVname)
    TextView TVname;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;


    Presenter mPresenter;
    Dialog mLoading;
    String selectedImage = "";


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        buscinessLogic();
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_userprofile);
        ButterKnife.bind(this);
    }

    private void buscinessLogic() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.menu_arrow_back_24dp);
        TVtitle.setText(R.string.user_profile);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter = new Presenter(this, getBaseInstence());
        BTNsubmit.setOnClickListener(this);
        BTNadd.setOnClickListener(this);
        if (mLoading == null) {
            mLoading = Utilities.showProgressBar(UserProfileActivity.this, getString(R.string.loading));
        }
        mPresenter.setData();
        CHKchangePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lay2.setVisibility(View.VISIBLE);
                } else {
                    lay2.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void setErrorOldPasswordMissing(int message) {
        EDToldPassword.setError(getString(message));
        EDToldPassword.requestFocus();
    }

    @Override
    public void setErrorNewPasswordMissing(int message) {
        EDTnewpassword.setError(getString(message));
        EDTnewpassword.requestFocus();
    }

    @Override
    public void setErrorConfirmPasswordMissing(int message) {
        EDTconfirmPassword.setError(getString(message));
        EDTconfirmPassword.requestFocus();
    }

    @Override
    public void setErrorPassWordInvalid(int message) {
        showSnackBar(message);
    }

    @Override
    public void setPasswordNotMaching(int message) {
        showSnackBar(message);
    }

    @Override
    public String getUserName() {
        return getSharedPreferenceHelper().getString(getString(R.string.user_name), "");
    }


    @Override
    public void loadHomePage() {
        startActivity(MainActivity.getStartIntent(UserProfileActivity.this));
        finish();
    }

    @Override
    public String getOldPassword() {
        return EDToldPassword.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return EDTnewpassword.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return EDTconfirmPassword.getText().toString();
    }

    @Override
    public String getImageUrl() {
        return getSharedPreferenceHelper().getString(getString(R.string.user_image), "");
    }

    @Override
    public CircleImageView getCircleImageView() {
        return mCircleImageView;
    }

    @Override
    public void setName() {
        TVname.setText(getSharedPreferenceHelper().getString(getString(R.string.user_name), ""));
    }

    @Override
    public boolean isPassWordChange() {
        return CHKchangePassword.isChecked();
    }

    @Override
    public void dimissImagePregress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showImagePregress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public String getImagePathForUpload() {
        return selectedImage;
    }


    @Override
    public Context getViewContext() {
        return UserProfileActivity.this;
    }

    @Override
    public AppBaseActivity getViewActivity() {
        return UserProfileActivity.this;
    }

    @Override
    public AppBaseFragment getViewFragment() {
        return null;
    }

    @Override
    public AppBaseFragment getBaseFragment() {
        return null;
    }

    @Override
    public AppBaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public RelativeLayout getParentView() {
        return parent;
    }

    @Override
    public void onBackPressed() {
        onBackPressedLogic();
    }

    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void showWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.show();
    }

    @Override
    public void removeWait(String message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mLoading.show();
    }

    @Override
    public void removeWait(int message) {
        TextView TVmessage = (TextView) mLoading.findViewById(R.id.TVmessage);
        TVmessage.setText(getString(message));
        mLoading.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    @Override
    public void removeWait() {
        mLoading.dismiss();
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public void showSnackBar(int message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(getViewContext(), R.color.snack_bar_text_color));
        snackbar.show();
    }

    @Override
    public String getStringRes(int string_id) {
        return getString(string_id);
    }


    @Override
    public void onFinishActivity() {
        finish();
    }

    @Override
    public void showInternetAlertLogic(boolean isInternet) {
        if (isInternet == false) {
            parent.setVisibility(View.GONE);
            LAYnointernet.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            LAYnointernet.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNodataAlertLogic(boolean isDataPresent) {

    }

    @Override
    public OnFragmentSwitchListener getFragmentSwitch() {
        return super.getFragmenntSwitchListner();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void onBackPressedLogic() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.BTNadd:
                mPresenter.pickImage();
                break;

            case R.id.BTNsubmit:
                mPresenter.onUpdatePasswordClick();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ImageUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        mPresenter.cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        mPresenter.galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedImage = getRealPathFromURI(data.getData());
        }
        mCircleImageView.setImageBitmap(bm);
    }

    private String getRealPathFromURI(Uri data) {

        String[] proj = {MediaStore.Images.Media.DATA};

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                this,
                data, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        selectedImage = destination.getAbsolutePath();
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCircleImageView.setImageBitmap(thumbnail);
    }
}
