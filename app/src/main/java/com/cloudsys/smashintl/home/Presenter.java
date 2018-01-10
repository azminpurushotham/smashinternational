package com.cloudsys.smashintl.home;

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
import com.cloudsys.smashintl.home.async.ServiceCall;
import com.cloudsys.smashintl.home.async.ServiceCallBack;
import com.cloudsys.smashintl.utiliti.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AzminPurushotham on 10/31/2017 time 15 : 58.
 * Mfluid Mobile Apps Pvt Ltd
 */

public class Presenter extends AppBasePresenter implements UserActions, ServiceCallBack, HomeServicesAdapter.OnAdapterItemClick {
    ActionView mView;
    ServiceCall mServiceCall;
    ArrayList<ServicesPojo> list = new ArrayList<>();
    private HomeServicesAdapter adapter;
    private HomeServicesAdapter.OnAdapterItemClick listner;
    SingleSelectionPojo mSingleSelectionPojo = new SingleSelectionPojo();

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
    public void getJson() {
        mView.showWait(mView.getLoading());
        if (Utilities.isInternet(mView.getViewContext())) {
            mServiceCall.getServices();
        } else {
            mView.removeWait(mView.getLoading());
            mView.showInternetAlertLogic(false);
        }
    }

    @Override
    public void setJson() {
    }

    @Override
    public void setServices(JSONObject mJsonObject) {
        try {
            JSONArray mJsonArray = mJsonObject.getJSONArray("Result");
            if (mJsonArray.length() > 0) {
                list = new ArrayList<>();
                for (int i = 0; i < mJsonArray.length(); i++) {
                    ServicesPojo item = new ServicesPojo();
                    item.setId(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("$id"));
                    item.setWorkOrderCategoryId(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("WorkOrderCategoryId"));
                    item.setServiceTypeId(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("ServiceTypeId"));
                    item.setServiceTypeName(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("ServiceTypeName"));
                    item.setWorkOrderCategoryName(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("WorkOrderCategoryName"));
                    item.setBriefDescription(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("BriefDescription"));
                    item.setImageURL(mJsonObject.getJSONArray("Result").getJSONObject(i).getString("ImageURL"));
                    item.setActive(mJsonObject.getJSONArray("Result").getJSONObject(i).getBoolean("IsActive"));
                    item.setProviderTimeOutSec(mJsonObject.getJSONArray("Result").getJSONObject(i).getLong("ProviderTimeOutSec"));
                    item.setShowProviderAssigned(mJsonObject.getJSONArray("Result").getJSONObject(i).getBoolean("ShowProviderAssigned"));
                    item.setMultipleResourcesAllowed(mJsonObject.getJSONArray("Result").getJSONObject(i).getBoolean("IsMultipleResourcesAllowed"));
                    item.setHasMaterial(mJsonObject.getJSONArray("Result").getJSONObject(i).getBoolean("HasMaterial"));
                    item.setSelected(false);
                    list.add(item);
                }
            }

            if (list.size() > 0) {
                setJson();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdapterItemClick(ServicesPojo servicesPojo, int position) {
        if (mSingleSelectionPojo.getmPojo() != null
                && mSingleSelectionPojo.getmPojo().isSelected()
                && mSingleSelectionPojo.getPosition() != position) {

            ServicesPojo mPojo = mSingleSelectionPojo.getmPojo();
            mPojo.setSelected(false);
            list.set(mSingleSelectionPojo.getPosition(), mPojo);
            adapter.notifyItemChanged(mSingleSelectionPojo.getPosition(), mPojo);
        }
        mSingleSelectionPojo.setmPojo(servicesPojo);
        mSingleSelectionPojo.setPosition(position);

        if (servicesPojo.isSelected()) {
            servicesPojo.setSelected(false);
        } else {
            servicesPojo.setSelected(true);
            mSingleSelectionPojo.setmPojo(servicesPojo);
            mSingleSelectionPojo.setPosition(position);
        }
        adapter.notifyItemChanged(position);
    }

    /////////////DEFAULTS///////////////////////

    @Override
    public Context getViewContext() {
        return mView.getViewContext();
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
//        int permissionSendMessage = ContextCompat.checkSelfPermission(mView.getViewContext(),
//                Manifest.permission.RECEIVE_SMS);
//        int readPermission = ContextCompat.checkSelfPermission(mView.getViewContext(), Manifest.permission.READ_SMS);
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (readPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(mView.getViewActivity(),
//                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
//                    REQUEST_ID_MULTIPLE_PERMISSIONS);
//            return false;
//        }
        return true;
    }


    @Override
    public void checkRunTimePermission(AppBaseActivity activity, String permission) {
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(activity, permission)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{permission},
//                        MY_PERMISSIONS_REQUEST_READ_SMS);
//                // MY_PERMISSIONS_REQUEST_READ_SMS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }
    }

    /////////////DEFAULTS///////////////////////
}
