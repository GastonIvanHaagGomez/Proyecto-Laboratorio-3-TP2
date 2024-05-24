package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Persona;
import ar.edu.utn.frbb.tup.model.Cuenta.TipoCuenta;
import ar.edu.utn.frbb.tup.model.Cuenta;


import java.time.LocalDateTime;

public class CuentaEntity extends BaseEntity{
    String nombre;
    LocalDateTime fechaCreacion;
    Double saldo;
    TipoCuenta tipoCuenta;
    String cliente;

    public CuentaEntity(Cuenta cuenta) {
        super(cuenta.getNumeroCuenta());
    
        
        this.saldo = cuenta.getSaldo();
        this.tipoCuenta = cuenta.getTipoCuenta();
       // this.cliente = cuenta.getPersona().getDni();
        this.fechaCreacion = cuenta.getFechaCreacion();
    }
}
