package com.example.okta_cust_sign_in.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.okta.sdk.resource.group.GroupList;
import com.okta.sdk.resource.user.AppLinkList;
import com.okta.sdk.resource.user.ChangePasswordRequest;
import com.okta.sdk.resource.user.ForgotPasswordResponse;
import com.okta.sdk.resource.user.ResetPasswordToken;
import com.okta.sdk.resource.user.Role;
import com.okta.sdk.resource.user.RoleList;
import com.okta.sdk.resource.user.TempPassword;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserActivationToken;
import com.okta.sdk.resource.user.UserCredentials;
import com.okta.sdk.resource.user.UserStatus;
import com.okta.sdk.resource.user.factor.Factor;
import com.okta.sdk.resource.user.factor.FactorList;
import com.okta.sdk.resource.user.factor.SecurityQuestionList;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserLocal implements com.okta.sdk.resource.user.User {
    @Override
    public Map<String, Object> getEmbedded() {
        return null;
    }

    @Override
    public Map<String, Object> getLinks() {
        return null;
    }

    @Override
    public Date getActivated() {
        return null;
    }

    @Override
    public Date getCreated() {
        return null;
    }

    @Override
    public UserCredentials getCredentials() {
        return null;
    }

    @Override
    public com.okta.sdk.resource.user.User setCredentials(UserCredentials credentials) {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Date getLastLogin() {
        return null;
    }

    @Override
    public Date getLastUpdated() {
        return null;
    }

    @Override
    public Date getPasswordChanged() {
        return null;
    }

    @Override
    public UserProfileLocal getProfile() {
        return null;
    }

    @Override
    public User setProfile(com.okta.sdk.resource.user.UserProfile profile) {
        return null;
    }


    @Override
    public UserStatus getStatus() {
        return null;
    }

    @Override
    public Date getStatusChanged() {
        return null;
    }

    @Override
    public UserStatus getTransitioningToStatus() {
        return null;
    }

    @Override
    public void deactivate(Boolean sendEmail) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public ResetPasswordToken resetPassword(String provider, Boolean sendEmail) {
        return null;
    }

    @Override
    public ResetPasswordToken resetPassword() {
        return null;
    }

    @Override
    public FactorList listFactors() {
        return null;
    }

    @Override
    public GroupList listGroupTargetsForRole(String roleId) {
        return null;
    }

    @Override
    public ForgotPasswordResponse forgotPassword(UserCredentials userCredentials, Boolean sendEmail) {
        return null;
    }

    @Override
    public ForgotPasswordResponse forgotPassword() {
        return null;
    }

    @Override
    public void removeRole(String roleId) {

    }

    @Override
    public TempPassword expirePassword(Boolean tempPassword) {
        return null;
    }

    @Override
    public TempPassword expirePassword() {
        return null;
    }

    @Override
    public UserActivationToken activate(Boolean sendEmail) {
        return null;
    }

    @Override
    public UserCredentials changeRecoveryQuestion(UserCredentials userCredentials) {
        return null;
    }

    @Override
    public void unsuspend() {

    }

    @Override
    public Factor addFactor(Factor body, Boolean updatePhone, String templateId, Integer tokenLifetimeSeconds, Boolean activate) {
        return null;
    }

    @Override
    public Factor addFactor(Factor body) {
        return null;
    }

    @Override
    public Factor addFactor(Factor body, Boolean updatePhone, String templateId) {
        return null;
    }

    @Override
    public GroupList listGroups() {
        return null;
    }

    @Override
    public void removeGroupTargetFromRole(String roleId, String groupId) {

    }

    @Override
    public FactorList listSupportedFactors() {
        return null;
    }

    @Override
    public void delete(Boolean sendEmail) {

    }

    @Override
    public void delete() {

    }

    @Override
    public void resetFactors() {

    }

    @Override
    public void suspend() {

    }

    @Override
    public RoleList listRoles(String expand) {
        return null;
    }

    @Override
    public RoleList listRoles() {
        return null;
    }

    @Override
    public void unlock() {

    }

    @Override
    public com.okta.sdk.resource.user.User update() {
        return null;
    }

    @Override
    public Factor getFactor(String factorId) {
        return null;
    }

    @Override
    public UserCredentials changePassword(ChangePasswordRequest changePasswordRequest) {
        return null;
    }

    @Override
    public AppLinkList listAppLinks(Boolean showAll) {
        return null;
    }

    @Override
    public AppLinkList listAppLinks() {
        return null;
    }

    @Override
    public void addGroupTargetToRole(String roleId, String groupId) {

    }

    @Override
    public SecurityQuestionList listSupportedSecurityQuestions() {
        return null;
    }

    @Override
    public void endAllSessions(Boolean oAuthTokens) {

    }

    @Override
    public void endAllSessions() {

    }

    @Override
    public void addToGroup(String groupId) {

    }

    @Override
    public Role addRole(Role role) {
        return null;
    }

    @Override
    public Factor addFactor(Boolean updatePhone, String templateId, Integer tokenLifetimeSeconds, Boolean activate, Factor body) {
        return null;
    }

    @Override
    public Factor addFactor(Boolean updatePhone, String templateId, Factor body) {
        return null;
    }

    @Override
    public ForgotPasswordResponse forgotPassword(Boolean sendEmail, UserCredentials userCredentials) {
        return null;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public Double getNumber(String key) {
        return null;
    }

    @Override
    public Boolean getBoolean(String key) {
        return null;
    }

    @Override
    public Integer getInteger(String key) {
        return null;
    }

    @Override
    public List<String> getStringList(String key) {
        return null;
    }

    @Override
    public List<Double> getNumberList(String key) {
        return null;
    }

    @Override
    public List<Integer> getIntegerList(String key) {
        return null;
    }

    @Override
    public String getResourceHref() {
        return null;
    }

    @Override
    public void setResourceHref(String href) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return false;
    }

    @Nullable
    @Override
    public Object get(@Nullable Object key) {
        return null;
    }

    @Nullable
    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Nullable
    @Override
    public Object remove(@Nullable Object key) {
        return null;
    }

    @Override
    public void putAll(@NonNull Map<? extends String, ?> m) {

    }

    @Override
    public void clear() {

    }

    @NonNull
    @Override
    public Set<String> keySet() {
        return null;
    }

    @NonNull
    @Override
    public Collection<Object> values() {
        return null;
    }

    @NonNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
