package com.nextshopper.bean;

/**
 * Created by siyiliu on 6/20/15.
 */
public class RegisterRequest extends UserBasicInfo{
    private String email;
    private String password;
    private String invitationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
