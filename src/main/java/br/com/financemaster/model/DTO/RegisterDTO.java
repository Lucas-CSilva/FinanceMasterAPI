package br.com.financemaster.model.DTO;

import br.com.financemaster.model.systemenums.UserRoleEnum;

public record RegisterDTO(String login, String password, String nome, UserRoleEnum role) {

}
