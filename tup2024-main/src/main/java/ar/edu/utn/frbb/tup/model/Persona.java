package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;

public class Persona {
    private String nombre;
    private String apellido;
    private long dni;
    private String direccion;
    private long telefono;
    protected LocalDate fechaNacimiento;
    private Long edad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Long getEdad() {
        return edad;
    }
    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

     public void setDni(long dni) {
        this.dni = dni;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getDireccion() {
        return direccion;

    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
    public long getTelefono() {
        return telefono;
    }
   

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}

