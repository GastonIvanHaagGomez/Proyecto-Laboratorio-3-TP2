package ar.edu.utn.frbb.tup.persistence.entity;

import ar.edu.utn.frbb.tup.model.Cuenta;

public class BaseEntity {
    private final Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}