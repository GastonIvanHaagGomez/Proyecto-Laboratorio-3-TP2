package ar.edu.utn.frbb.tup.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    public enum TipoCuenta {
        CAJA_AHORRO, CUENTA_CORRIENTE
    }

    private Long numeroCuenta;
    private LocalDateTime fechaCreacion;
    private double saldo;
    private TipoCuenta tipoCuenta;
    private Cliente cliente;
    private Long moneda;
    private LocalDateTime ultimaOperacion;
    private List<Movimiento> movimientos;

    public Cuenta() {
        this.fechaCreacion = LocalDateTime.now();
        this.movimientos = new ArrayList<>();
    }

    public Cuenta(Long numeroCuenta, TipoCuenta tipoCuenta, Cliente cliente, double saldoInicial, Long moneda) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.cliente = cliente;
        this.saldo = saldoInicial;
        this.moneda = moneda;
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaOperacion = LocalDateTime.now();
        this.movimientos = new ArrayList<>();
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Long getMoneda() {
        return moneda;
    }

    public void setMoneda(Long moneda) {
        this.moneda = moneda;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getUltimaOperacion() {
        return ultimaOperacion;
    }

    public void setUltimaOperacion(LocalDateTime ultimaOperacion) {
        this.ultimaOperacion = ultimaOperacion;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public void depositar(double monto) {
        this.saldo += monto;
        this.ultimaOperacion = LocalDateTime.now();
    }

    public boolean retirar(double monto) {
        if (monto <= this.saldo) {
            this.saldo -= monto;
            this.ultimaOperacion = LocalDateTime.now();
            return true;
        } else {
            return false;
        }
    }

    public void agregarMovimiento(Movimiento movimiento) {
        this.movimientos.add(movimiento);
    }

    // Método para obtener el titular de la cuenta
    public Cliente getTitular() {
        return cliente;
    }
}
