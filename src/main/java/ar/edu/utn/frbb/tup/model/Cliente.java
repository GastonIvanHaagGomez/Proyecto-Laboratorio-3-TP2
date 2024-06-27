package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona {
    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private Set<Cuenta> cuentas = new HashSet<>();
    private int numero;

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void agregarCuenta(Cuenta cuenta) {
        cuenta.setCliente(this);
        this.cuentas.add(cuenta);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean tieneCuenta(Cuenta.TipoCuenta tipoCuenta, Long moneda) {
        return cuentas.stream().anyMatch(c -> c.getTipoCuenta() == tipoCuenta && c.getMoneda().equals(moneda));
    }

    public Long getEdad() {
        if (fechaNacimiento == null) {
            return null;
        }
        return (long) Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}


