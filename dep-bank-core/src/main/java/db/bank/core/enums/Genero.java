package db.bank.core.enums;

public enum Genero {
    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private final String descripcion;

    Genero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}