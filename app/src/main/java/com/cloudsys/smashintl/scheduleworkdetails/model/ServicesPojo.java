package com.cloudsys.smashintl.scheduleworkdetails.model;

/**
 * Created by User on 11/30/2017.
 */

public class ServicesPojo {
    String id, WorkOrderCategoryId, ServiceTypeId, ServiceTypeName, WorkOrderCategoryName, BriefDescription, ImageURL;
    long ProviderTimeOutSec;
    boolean IsActive, ShowProviderAssigned, IsMultipleResourcesAllowed, HasMaterial;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOrderCategoryId() {
        return WorkOrderCategoryId;
    }

    public void setWorkOrderCategoryId(String workOrderCategoryId) {
        WorkOrderCategoryId = workOrderCategoryId;
    }

    public String getServiceTypeId() {
        return ServiceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        ServiceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return ServiceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        ServiceTypeName = serviceTypeName;
    }

    public String getWorkOrderCategoryName() {
        return WorkOrderCategoryName;
    }

    public void setWorkOrderCategoryName(String workOrderCategoryName) {
        WorkOrderCategoryName = workOrderCategoryName;
    }

    public String getBriefDescription() {
        return BriefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        BriefDescription = briefDescription;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public long getProviderTimeOutSec() {
        return ProviderTimeOutSec;
    }

    public void setProviderTimeOutSec(long providerTimeOutSec) {
        ProviderTimeOutSec = providerTimeOutSec;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isShowProviderAssigned() {
        return ShowProviderAssigned;
    }

    public void setShowProviderAssigned(boolean showProviderAssigned) {
        ShowProviderAssigned = showProviderAssigned;
    }

    public boolean isMultipleResourcesAllowed() {
        return IsMultipleResourcesAllowed;
    }

    public void setMultipleResourcesAllowed(boolean multipleResourcesAllowed) {
        IsMultipleResourcesAllowed = multipleResourcesAllowed;
    }

    public boolean isHasMaterial() {
        return HasMaterial;
    }

    public void setHasMaterial(boolean hasMaterial) {
        HasMaterial = hasMaterial;
    }
}
