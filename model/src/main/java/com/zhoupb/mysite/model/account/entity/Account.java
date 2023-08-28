package com.zhoupb.mysite.model.account.entity;

import com.zhoupb.mysite.model.BaseEntity;
import io.mybatis.provider.Entity;

@Entity.Table("account")
public class Account extends BaseEntity {

    @Entity.Column(id = true, insertable = false, updatable = false)
    private Long id;

    @Entity.Column
    private String username;

    @Entity.Column
    private String password;

    @Entity.Column
    private String email;

    @Entity.Column
    private Boolean active;

    @Entity.Column
    private String avatar;

    @Entity.Column
    private String introduction ;

    @Entity.Column
    private String nickname;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
