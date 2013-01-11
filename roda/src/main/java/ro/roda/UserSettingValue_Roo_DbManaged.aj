// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ro.roda.User;
import ro.roda.UserSetting;
import ro.roda.UserSettingValue;

privileged aspect UserSettingValue_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private User UserSettingValue.userId;
    
    @ManyToOne
    @JoinColumn(name = "user_setting_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private UserSetting UserSettingValue.userSettingId;
    
    @Column(name = "value", columnDefinition = "text")
    @NotNull
    private String UserSettingValue.value;
    
    public User UserSettingValue.getUserId() {
        return userId;
    }
    
    public void UserSettingValue.setUserId(User userId) {
        this.userId = userId;
    }
    
    public UserSetting UserSettingValue.getUserSettingId() {
        return userSettingId;
    }
    
    public void UserSettingValue.setUserSettingId(UserSetting userSettingId) {
        this.userSettingId = userSettingId;
    }
    
    public String UserSettingValue.getValue() {
        return value;
    }
    
    public void UserSettingValue.setValue(String value) {
        this.value = value;
    }
    
}
