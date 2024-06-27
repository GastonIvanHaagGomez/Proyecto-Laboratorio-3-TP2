package ar.edu.utn.frbb.tup.presentation.input;

import ar.edu.utn.frbb.tup.model.Cliente;  
import ar.edu.utn.frbb.tup.model.TipoPersona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClienteInputProcessor extends BaseInputProcessor {

    private static List<Cliente> clientes = new ArrayList<>();

    public Cliente ingresarCliente() {
        Cliente cliente = new Cliente();
        clearScreen();
        
        Scanner scanner = new Scanner(System.in);

        // Ingreso del nombre del cliente:
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine().trim();

        // Valida que se ingrese el nombre correctamente.
        while (!nombre.matches("[A-Za-záéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
            System.out.print("Nombre inválido. Por favor, ingrese un nombre válido: ");
            nombre = scanner.nextLine().trim();
        }
        cliente.setNombre(nombre);

        // Ingreso del apellido del cliente:
        System.out.print("Ingrese el apellido del cliente: ");
        String apellido = scanner.nextLine().trim();

        // Valida que se ingrese el apellido correctamente.
        while (!apellido.matches("[A-Za-záéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
            System.out.println("Apellido inválido. Por favor, ingrese un apellido válido: ");
            apellido = scanner.nextLine().trim();
        }
        cliente.setApellido(apellido);

        // Ingreso y validación del número de DNI
        boolean validDni = false;
        while (!validDni) {
            try {
                System.out.print("Ingrese el número de DNI del cliente (8 dígitos): ");
                long dni = scanner.nextLong();
                scanner.nextLine(); // Consumir el salto de línea

                // Verificar si el DNI tiene 8 dígitos
                if (String.valueOf(dni).length() != 8) {
                    System.out.print("Error. El DNI debe tener exactamente 8 dígitos.");
                } else {
                    // Verificar si el DNI ya está en uso por otro cliente
                    boolean dniExists = clientes.stream().anyMatch(c -> c.getDni() == dni);
                    if (dniExists) {
                        System.out.println("El DNI ingresado ya está asociado a otro cliente. Por favor, ingrese un DNI único.");
                    } else {
                        validDni = true;
                        cliente.setDni(dni);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.print("DNI inválido. Por favor, ingrese un número de DNI válido: ");
                scanner.nextLine(); // Consumir el salto de línea y limpiar el buffer de entrada
            }
        }

        // Ingreso de la dirección del cliente.
        System.out.print("Ingrese la dirección del cliente: ");
        String direccion = scanner.nextLine().trim();

        // Validación que la dirección no este vacía.
        while (direccion.isEmpty()) {
            System.out.println("Dirección inválida. Por favor, ingrese una dirección válida: ");
            direccion = scanner.nextLine().trim();
        }
        cliente.setDireccion(direccion);

        // Ingreso y validación del numero de telefono del cliente.
        long telefono = 0;
        boolean validTelefono = false;
        while (!validTelefono) {
            try {
                System.out.print("Ingrese el teléfono del cliente: ");
                telefono = scanner.nextLong();
                scanner.nextLine(); // Consumir el salto de línea
                if (telefono <= 0) {
                    System.out.println("Teléfono inválido. Por favor, ingrese un número de teléfono válido.");
                } else {
                    validTelefono = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Teléfono inválido. Por favor, ingrese un número de teléfono válido.");
                scanner.nextLine(); // Consumir el salto de línea y limpiar el buffer de entrada
            }
        }
        cliente.setTelefono(telefono);

        // Ingreso de tipo de persona.
        System.out.print("Ingrese el tipo de persona Física(F) o Jurídica(J): ");
        String tipoPersonaStr = scanner.nextLine().toUpperCase();

        // Validación de tipo de persona.
        while (!tipoPersonaStr.equals("F") && !tipoPersonaStr.equals("J")) {
            System.out.println("Tipo de persona inválido. Ingrese FISICA (F) o JURIDICA (J): ");
            tipoPersonaStr = scanner.nextLine().toUpperCase();
        }
        TipoPersona tipoPersona = TipoPersona.fromString(tipoPersonaStr);
        cliente.setTipoPersona(tipoPersona);

        // Ingreso del banco del cliente.
        System.out.print("Ingrese el banco del cliente: ");
        String banco = scanner.nextLine().trim();

        // Validación que el banco no este vacío.
        while (banco.isEmpty()) {
            System.out.println("Banco inválido. Por favor, ingrese un banco válido: ");
            banco = scanner.nextLine().trim();
        }
        cliente.setBanco(banco);

       // Ingreso y validación de fecha de alta del cliente.
		System.out.print("Ingrese la fecha de alta del cliente (Formato: YYYY-MM-DD): ");
		LocalDate fechaAlta = null;
		boolean fechaValida = false;
		while (!fechaValida) {
		    try {
		        fechaAlta = LocalDate.parse(scanner.nextLine());
		        LocalDate hoy = LocalDate.now();
		        int edad = hoy.getYear() - fechaAlta.getYear();
		        
		        // Comprobar si el cliente tiene entre 18 y 100 años
		        if (edad >= 18 && edad <= 100) {
		            fechaValida = true;
		        } else {
		            System.out.print("El cliente debe tener entre 18 y 100 años. Ingrese nuevamente la fecha en formato YYYY-MM-DD: ");
		        }
		    } catch (Exception e) {
		        System.out.print("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD: ");
		    }
		}
		cliente.setFechaAlta(fechaAlta);
		
		// Añadir el nuevo cliente a la lista de clientes
		clientes.add(cliente);
		
		clearScreen();
		scanner.close(); // Cierre el scanner.
		return cliente;
		
		}
    
    // Método para mostrar la lista de clientes generados.
    public void mostrarClientes() {
        System.out.println("---------------------------------------");
        System.out.println("Lista de clientes generados:");
        for (Cliente cliente : clientes) {
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println("DNI: " + cliente.getDni());
            System.out.println("Dirección: " + cliente.getDireccion());
            System.out.println("Teléfono: " + cliente.getTelefono());
            System.out.println("Tipo de persona: " + cliente.getTipoPersona());
            System.out.println("Banco: " + cliente.getBanco());
            System.out.println("Fecha de alta: " + cliente.getFechaAlta());
            System.out.println("---------------------------------------");
        }
    }

    // Método para modificar la lista de clientes generados.
    public void modificarCliente() {
        Scanner scanner = new Scanner(System.in);

        // Mostrar lista de clientes para seleccionar uno a modificar
        System.out.println("Lista de clientes:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNombre() + " " + clientes.get(i).getApellido());
        }

        // Pedir al usuario que seleccione el cliente a modificar
        System.out.print("Seleccione el cliente que desea modificar (1-" + clientes.size() + "): ");
        int clienteIndex = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        scanner.close(); // Cierre el scanner.
        
        if (clienteIndex < 1 || clienteIndex > clientes.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        // Obtener el cliente seleccionado
        Cliente cliente = clientes.get(clienteIndex - 1);

        // Pedir al usuario que seleccione qué campo desea modificar
        System.out.println("Seleccione qué desea modificar:");
        System.out.println("1.- Nombre");
        System.out.println("2.- Apellido");
        System.out.println("3.- DNI");
        System.out.println("4.- Dirección");
        System.out.println("5.- Teléfono");
        System.out.println("6.- Tipo de Persona");
        System.out.println("7.- Banco");
        System.out.println("8.- Fecha de Alta");
        System.out.print("\nIngrese su opción (1-8): ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // Se despliegan opciones para modificar el Cliente:
        switch (opcion) {
        
            case 1: // Opcion para ingresar el nuevo nombre del cliente.
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = scanner.nextLine().trim();
                // Valida que el nombre sea solo con letras, no estan permitidos simbolos/numeros.
                while (!nuevoNombre.matches("[A-Za-záéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
                    System.out.print("Nombre inválido. Por favor, ingrese un nombre válido: ");
                    nuevoNombre = scanner.nextLine().trim();
                }
                cliente.setNombre(nuevoNombre);
                break;
                
            case 2: // Opcion para ingresar el nuevo apellido del cliente.
                System.out.print("Ingrese el nuevo apellido: ");
                String nuevoApellido = scanner.nextLine().trim();
                // Valida que el nombre sea solo con letras, no estan permitidos simbolos/numeros.
                while (!nuevoApellido.matches("[A-Za-záéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
                    System.out.println("Apellido inválido. Por favor, ingrese un apellido válido: ");
                    nuevoApellido = scanner.nextLine().trim();
                }
                cliente.setApellido(nuevoApellido);
                break;
                
            case 3: // Opcion para ingresar el nuevo DNI del cliente.
                boolean validDni = false;
                do {
                    System.out.print("Ingrese el nuevo DNI: ");
                    long nuevoDni = scanner.nextLong();
                    scanner.nextLine(); // Consumir el salto de línea
                    // Valida que el DNI tenga 8 dígitos y sea único
                    if (String.valueOf(nuevoDni).length() != 8) {
                        System.out.println("Error. El DNI debe tener exactamente 8 dígitos.");
                    } else {
                        boolean dniExists = clientes.stream().anyMatch(c -> c.getDni() == nuevoDni && c != cliente);
                        if (dniExists) {
                            System.out.println("El DNI ingresado ya está asociado a otro cliente. Por favor, ingrese un DNI único.");
                        } else {
                            validDni = true;
                            cliente.setDni(nuevoDni);
                        }
                    }
                } while (!validDni);
                break;
                
            case 4: // Opcion para ingresar la nueva direccion del cliente.
                System.out.print("Ingrese la nueva dirección: ");
                String nuevaDireccion = scanner.nextLine().trim();
                cliente.setDireccion(nuevaDireccion);
                break;
                
            case 5: // Opcion para ingresar el nuevo telefono del cliente.
                long nuevoTelefono;
                boolean validTelefono = false;
                do {
                    System.out.print("Ingrese el nuevo teléfono: ");
                    nuevoTelefono = scanner.nextLong();
                    scanner.nextLine(); // Consumir el salto de línea
                    if (nuevoTelefono <= 0) {
                        System.out.println("Teléfono inválido. Por favor, ingrese un número de teléfono válido.");
                    } else {
                        validTelefono = true;
                    }
                } while (!validTelefono);
                cliente.setTelefono(nuevoTelefono);
                break;
                
            case 6: // Opcion para modificar el tipo de persona del cliente.
                System.out.print("Ingrese el nuevo tipo de persona (F para Física, J para Jurídica): ");
                String nuevoTipoPersona = scanner.nextLine().trim().toUpperCase();
                cliente.setTipoPersona(TipoPersona.fromString(nuevoTipoPersona));
                break;
                
            case 7: // // Opcion para ingresar el nuevo banco asociado del cliente.
                System.out.print("Ingrese el nuevo banco: ");
                String nuevoBanco = scanner.nextLine().trim();
                cliente.setBanco(nuevoBanco);
                break;
                
            case 8: // Opcion para ingresar la nueva fecha de alta del cliente.
                LocalDate nuevaFechaAlta = null;
                boolean fechaValida = false;
                do {
                    System.out.print("Ingrese la nueva fecha de alta (Formato: YYYY-MM-DD): ");
                    try {
                        nuevaFechaAlta = LocalDate.parse(scanner.nextLine().trim());
                        fechaValida = true;
                        // Validacion en caso de que la fecha se ingrese incorrectamente.
                    } catch (Exception e) {
                        System.out.print("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD: ");
                    }
                } while (!fechaValida);
                cliente.setFechaAlta(nuevaFechaAlta);
                break;
            default:
                System.out.println("Opción inválida.");
        }
        scanner.close();
    }
    
    // Metodo para eliminar un cliente.
    public void eliminarCliente() {
        Scanner scanner = new Scanner(System.in);

        // Mostrar lista de clientes para seleccionar uno a eliminar
        System.out.println("Lista de clientes: ");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNombre() + " " + clientes.get(i).getApellido());
        }

        // Pedir al usuario que seleccione el cliente a eliminar
        System.out.print("Seleccione el cliente que desea eliminar (1-" + clientes.size() + "): ");
        int clienteIndex = scanner.nextInt();
        scanner.nextLine();

        scanner.close(); // Cierre el scanner.
        if (clienteIndex < 1 || clienteIndex > clientes.size()) {
            System.out.println("Error. Seleccion invalida.");
            return;
            
        }

        // Obtener el cliente seleccionado
        Cliente cliente = clientes.get(clienteIndex - 1);

        // Se consulta si desea la eliminacion del cliente seleccionado.
        System.out.println("¿Está seguro que desea eliminar a " + cliente.getNombre() + " " + cliente.getApellido() + "? (S/N)");
        String confirmacion = scanner.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) { 
            clientes.remove(cliente); // Eliminar el cliente de la lista
            System.out.println("El cliente ha sido eliminado exitosamente.");
        } else {
            System.out.println("Operación cancelada."); // Cancela la eliminacion del cliente de la lista.
        }
        scanner.close(); // Cierre el scanner.
    }
}


