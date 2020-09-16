package com.internet.shop.model;

public class Role {
    private long id;
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public enum RoleName {
        USER,
        ADMIN
    }
}
