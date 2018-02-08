package com.cloudsys.smashintl.base.asynck;


import com.webservicehelper.retrofit.APIs;
import com.webservicehelper.retrofit.RetrofitHelper;

public class AppBaseServiceCall {

    public APIs getApis() {
        return new RetrofitHelper().getApis();
    }

}
