package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        lenient().when(clienteDao.find(anyLong())).thenReturn(null); // Ejemplo de uso de lenient
    }

    @Test
    public void testClienteMenor18Años() {
        Cliente clienteMenorDeEdad = new Cliente();
        clienteMenorDeEdad.setFechaNacimiento(LocalDate.of(2020, 2, 7));
        assertThrows(IllegalArgumentException.class, () -> clienteService.darDeAltaCliente(clienteMenorDeEdad));
    }

    @Test
    public void testClienteSuccess() throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        cliente.setDni(29857643);
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        clienteService.darDeAltaCliente(cliente);

        verify(clienteDao, times(1)).save(cliente);
    }

    @Test
    public void testClienteAlreadyExistsException() throws ClienteAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456437);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        when(clienteDao.find(26456437)).thenReturn(new Cliente());

        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darDeAltaCliente(pepeRino));
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

        clienteService.agregarCuentaAlCliente(cuenta, pepeRino);

        verify(clienteDao, times(1)).save(pepeRino);
        assertEquals(1, pepeRino.getCuentas().size());
        assertTrue(pepeRino.getCuentas().contains(cuenta));
    }

    @Test
    public void testAgregarCuentaAClienteDuplicada() throws TipoCuentaAlreadyExistsException {
        Cliente luciano = new Cliente();
        luciano.setDni(26456439);
        luciano.setNombre("Pepe");
        luciano.setApellido("Rino");
        luciano.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        luciano.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(12345L);
        cuenta.setSaldo(500000);
        cuenta.setTipoCuenta(Cuenta.TipoCuenta.CAJA_AHORRO);
        cuenta.setMoneda(1L);

        clienteService.agregarCuentaAlCliente(cuenta, luciano);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumeroCuenta(54321L);
        cuenta2.setSaldo(600000);
        cuenta2.setTipoCuenta(Cuenta.TipoCuenta.CAJA_AHORRO);
        cuenta2.setMoneda(1L);

        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuentaAlCliente(cuenta2, luciano));
        verify(clienteDao, times(1)).save(luciano);
        assertEquals(1, luciano.getCuentas().size());
    }


    @Test
    public void testAgregarCAyCCSucces() throws TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cajaAhorro = new Cuenta();
        cajaAhorro.setNumeroCuenta(12345L);
        cajaAhorro.setSaldo(50000);
        cajaAhorro.setTipoCuenta(Cuenta.TipoCuenta.CAJA_AHORRO);
        cajaAhorro.setMoneda(1L);

        Cuenta cuentaCorriente = new Cuenta();
        cuentaCorriente.setNumeroCuenta(54321L);
        cuentaCorriente.setSaldo(10000);
        cuentaCorriente.setTipoCuenta(Cuenta.TipoCuenta.CUENTA_CORRIENTE);
        cuentaCorriente.setMoneda(1L);

        clienteService.agregarCuentaAlCliente(cajaAhorro, pepeRino);
        clienteService.agregarCuentaAlCliente(cuentaCorriente, pepeRino);

        verify(clienteDao, times(2)).save(pepeRino);
        assertEquals(2, pepeRino.getCuentas().size());
        assertTrue(pepeRino.getCuentas().contains(cajaAhorro));
        assertTrue(pepeRino.getCuentas().contains(cuentaCorriente));
    }

    @Test
    public void testBuscarPorDniExito() {
        Cliente pepeRino = new Cliente();
        pepeRino.setDni(26456439);
        pepeRino.setNombre("Pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setFechaNacimiento(LocalDate.of(1978, 3, 25));
        pepeRino.setTipoPersona(TipoPersona.PERSONA_FISICA);

        when(clienteDao.find(26456439)).thenReturn(pepeRino);

        Cliente cliente = clienteService.buscarClientePorDni(26456439);

        assertNotNull(cliente);
        assertEquals(26456439, cliente.getDni());
        verify(clienteDao, times(1)).find(26456439);
    }

    @Test
    public void testBuscarPorDniNoExistente() {
        when(clienteDao.find(12345678)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> clienteService.buscarClientePorDni(12345678));
    }
}
