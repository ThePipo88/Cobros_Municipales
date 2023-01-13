package org.una.municipalidad.components;

public enum RolesTypes {
    ROLE_GESTOR("GESTOR"),
    ROLE_GERENTE("GERENTE"),
    ROLE_AUDITOR("AUDITOR"),
    ROLE_ADMINISTRADOR("ADMINISTRADOR"),
    ROLE_TELEGRAM("TELEGRAM");

    private final String codigo;

    RolesTypes(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
