// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ro.roda.User;
import ro.roda.UserMessage;

privileged aspect UserMessage_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User UserMessage.userId;
    
    @Column(name = "message", columnDefinition = "text")
    @NotNull
    private String UserMessage.message;
    
    @Column(name = "to", columnDefinition = "int4")
    @NotNull
    private Integer UserMessage.to;
    
    public User UserMessage.getUserId() {
        return userId;
    }
    
    public void UserMessage.setUserId(User userId) {
        this.userId = userId;
    }
    
    public String UserMessage.getMessage() {
        return message;
    }
    
    public void UserMessage.setMessage(String message) {
        this.message = message;
    }
    
    public Integer UserMessage.getTo() {
        return to;
    }
    
    public void UserMessage.setTo(Integer to) {
        this.to = to;
    }
    
}
