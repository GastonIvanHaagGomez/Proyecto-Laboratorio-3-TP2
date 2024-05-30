package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;

public class ClienteService {

    public static final String agrega = null;
    ClienteDao clienteDao = new ClienteDao();

    public void darDeAltaCliente(Cliente cliente) throws ClienteAlreadyExistsException {
        if (clienteDao.find(cliente.getDni()) != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }

        if (cliente.getEdad() < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.save(cliente);
    }

  
        public void agregarCuentaAlCliente(Cuenta cuenta, Cliente cliente) throws TipoCuentaAlreadyExistsException {
            if (cliente.tieneCuenta(cuenta.getTipoCuenta(), cuenta.getMoneda())) {
                throw new TipoCuentaAlreadyExistsException("El cliente ya tiene una cuenta de este tipo y moneda.");
            }
            cliente.agregarCuenta(cuenta);
        }
    }

    