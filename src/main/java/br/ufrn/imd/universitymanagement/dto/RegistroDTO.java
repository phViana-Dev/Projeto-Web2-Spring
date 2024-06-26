package br.ufrn.imd.universitymanagement.dto;

import br.ufrn.imd.universitymanagement.enuns.UserRoles;

public record RegistroDTO(
        String login,
        String password,
        UserRoles role) {
}
