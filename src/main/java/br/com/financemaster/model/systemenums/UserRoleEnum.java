package br.com.financemaster.model.systemenums;

public enum UserRoleEnum {
    USER("user");

    private String role;

    UserRoleEnum(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
