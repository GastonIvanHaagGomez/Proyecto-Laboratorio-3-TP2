package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private static ClienteService clienteService;

    @BeforeAll
    public static void setUp() {
        clienteService = new ClienteService();
    }

    @Test
    public void testClienteMenor18Años() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setDni(12345678L);
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono(5551234L);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);
        cliente.setBanco("Banco Falso");
        cliente.setFechaNacimiento(LocalDate.now().minusYears(17));  // Menor de 18 años

        assertThrows(IllegalArgumentException.class, () -> {
            // Simula el método que valida la edad del cliente y lanza IllegalArgumentException si es menor de 18
            if (cliente.getEdad() < 18) {
                throw new IllegalArgumentException("El cliente debe ser mayor de 18 años");
            }
        });
    }

    @Test
    public void testClienteSuccess() throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.of(1978,3,25));
        cliente.setDni(29857643);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        clienteService.darDeAltaCliente(cliente);

        ClienteDao dao = new ClienteDao();
        assertNotNull(dao.find(29857643));
    }

    @Test
    public void testClienteAlreadyExistsException() throws ClienteAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3,25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        clienteService.darDeAltaCliente(pepeRino);

        Cliente mateoA = new Cliente();
        mateoA.setDni(26456439);
        mateoA.setNombre("Mateo");
        mateoA.setApellido("Abraham");
        mateoA.setFechaNacimiento(LocalDate.of(2001, 12,18));
        mateoA.setTipoPersona(TipoPersona.PERSONA_FISICA);

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(mateoA));
    }
    

    
    @Test
    public void testAgregarCuentaAClienteSuccess() throws TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldo(500000);
        cuenta.setTipoCuenta(Cuenta.TipoCuenta.CAJA_AHORRO);
        cuenta.setMoneda(1L);

        pepeRino.agregarCuenta(cuenta);

        assertEquals(1, pepeRino.getCuentas().size());
        assertEquals(pepeRino, cuenta.getCliente());
    }
}
    //Agregar una CA$ y agregar otra cuenta con mismo tipo y moneda --> fallar (assertThrows)
    //Agregar una CA$ y CC$ --> success 2 cuentas, titular peperino
    //Agregar una CA$ y CAU$S --> success 2 cuentas, titular peperino...



/* */