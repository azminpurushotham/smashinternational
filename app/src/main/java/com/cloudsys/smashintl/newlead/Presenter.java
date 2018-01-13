package com.cloudsys.smashintl.newlead;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cloudsys.smashintl.R;
import com.cloudsys.smashintl.base.AppBaseActivity;
import com.cloudsys.smashintl.base.AppBaseFragment;
import com.cloudsys.smashintl.base.AppBasePresenter;
import com.cloudsys.smashintl.newlead.async.ServiceCall;
import com.cloudsys.smashintl.newlead.async.ServiceCallBack;
import com.cloudsys.smashintl.newlead.model.newlead;
import com.cloudsys.smashintl.utiliti.SharedPreferenceHelper;
import com.cloudsys.smashintl.utiliti.Utilities;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack{
    ActionView mView;
    ServiceCall mServiceCall;

    public Presenter(ActionView mView, AppBaseActivity baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    public Presenter(ActionView mView, AppBaseFragment baseInstence) {
        super(mView, baseInstence);
        this.mView = mView;
        mServiceCall = new ServiceCall(this);
    }

    @Override
    public void setServices(JSONObject mJsonObject) {
    }

    @Override
    public void completPosting() {
        mView.returnToHome();
    }

    /////////////DEFAULTS///////////////////////

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
    }

    @Override
    public SharedPreferenceHelper getSharedPreferenceHelper() {
        return getSharedPreference();
    }

    @Override
    public void onSuccess(JSONObject mJsonObject) {

    }

    @Override
    public void onFailer(String message) {
        Log.v("exception", message);
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void onCallfailerFromServerside() {

    }

    @Override
    public void onException(String message) {
        mView.removeWait(mView.getLoading());
    }

    @Override
    public void showScnackBar(String message) {
        showSnackBar(mView.getParentView(), message);
    }

    @Override
    public void removeWait() {
        mView.removeWait(mView.getLoading());
    }


    @Override
    public void showWait(String message) {
        TextView TVmessage = (TextView) mView.getLoading().findViewById(R.id.TVmessage);
        TVmessage.setText(message);
        mView.showWait(mView.getLoading());
    }

    @Override
    public void showNoInternetConnectionLayout(boolean isInternet) {
        mView.showInternetAlertLogic(isInternet);
    }

    @Override
    public void showSnackBar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(mView.getViewContext(), R.color.snack_bar_text_color));
        textView.setMaxLines(3);
        snackbar.setActionTextColor(ContextCompat.getColor(mView.getViewContext(), R.color.snack_bar_text_color));
        mView.showSnackBar(snackbar);
    }


    private boolean checkAndRequestPermissions() {
        return true;
    }


    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
    }

    @Override
    public void submitData() {

        Utilities.hideKeyboard((Activity) getViewContext());
        if(mView.getCustomerName().equals("")){
            Utilities.hideKeyboard((Activity) getViewContext());
            showSnackBar(mView.getParentView(),"Customer Name cannot be blank");
        }else if(mView.getCustomerId().equals("")){
            showSnackBar(mView.getParentView(),"Customer id cannot be blank");
        }else if(mView.getTelephoneNumber().length()==0){
            showSnackBar(mView.getParentView(),"Telephone number cannot be blank");
        }else if(!isValidMobile(mView.getTelephoneNumber())){
            showSnackBar(mView.getParentView(),"Please enter a valid phone number");
        }else if(mView.getEmail().equals("")){
            showSnackBar(mView.getParentView(),"Email cannot be blank");
        }else if(!isValidMail(mView.getEmail())){
            showSnackBar(mView.getParentView(),"Please enter a valid email");
        }else if(mView.getSMS().equals("")){
            showSnackBar(mView.getParentView(),"SMS number cannot be blank");
        }else if(!isValidMobile(mView.getSMS())){
            showSnackBar(mView.getParentView(),"Please enter a valid SMS number");
        }else if(mView.getAddress().equals("")){
            showSnackBar(mView.getParentView(),"Address cannot be blank");
        }else if(mView.getPendingAmount().equals("")){
            showSnackBar(mView.getParentView(),"Pending amount cannot be blank");
        }else if(mView.getBillId().equals("")){
            showSnackBar(mView.getParentView(),"Bill id cannot be blank");
        }else if(mView.getLat()==0||mView.getLon()==0){
            showSnackBar(mView.getParentView(),"Please select a location");
        } else {
            if (Utilities.isInternet(getViewContext())) {
                newlead data=new newlead();
                data.setUserId(mView.getCustomerId());
                data.setToken( getSharedPreference().getString(mView.getViewContext().getString(R.string.tocken), null));
                data.setStatus(mView.getStatus());
                data.setCustomerName(mView.getCustomerName());
                data.setBranch("");
                data.setTelephone(mView.getTelephoneNumber());
                data.setEmail(mView.getEmail());
                data.setSms(mView.getSMS());
                data.setAddress(mView.getAddress());
                data.setPending(mView.getPendingAmount());
                data.setCollecting("");
                data.setCurrency(mView.getCurrency());
                data.setLat(mView.getLat()+"");
                data.setLon(mView.getLon()+"");
                data.setBill(mView.getBillId());
                mServiceCall.postNewLead(data);
            }else{
                mView.showInternetAlertLogic(false);
//                showSnackBar(mView.getParentView(),getViewContext().getString(R.string.no_internet));
            }
        }
    }

    /////////////VALIDATION///////////////////////

    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        return check;
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }
}
