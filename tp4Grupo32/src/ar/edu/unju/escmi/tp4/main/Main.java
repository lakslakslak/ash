package ar.edu.unju.escmi.tp4.main;

import ar.edu.unju.escmi.tp4.collections.CollectionInmueble;
import ar.edu.unju.escmi.tp4.collections.CollectionCliente;
import ar.edu.unju.escmi.tp4.collections.CollectionContrato;
import ar.edu.unju.escmi.tp4.dominio.Terreno;
import ar.edu.unju.escmi.tp4.dominio.Vivienda;
import ar.edu.unju.escmi.tp4.dominio.Cliente;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        
        do {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Registrar terreno");
            System.out.println("2. Registrar vivienda");
            System.out.println("3. Registrar cliente");
            System.out.println("4. Alquiler de vivienda");
            System.out.println("5. Venta de terreno");
            System.out.println("6. Consultar inmuebles");
            System.out.println("7. Consultar viviendas alquiladas");
            System.out.println("8. Consultar terrenos vendidos");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    registrarTerreno();
                    break;
                case 2:
                    registrarVivienda();
                    break;
                case 3:
                    registrarCliente();
                    break;
                case 4:
                    alquilerVivienda();
                    break;
                case 5:
                    ventaTerreno();
                    break;
                case 6:
                    consultarInmuebles();
                    break;
                case 7:
                    consultarViviendasAlquiladas();
                    break;
                case 8:
                    consultarTerrenosVendidos();
                    break;
                case 9:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (option != 9);
    }

    private static void registrarTerreno() {
        // Aquí va la lógica para registrar un terreno.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresar los datos del terreno:");
        System.out.print("Código de identificación: ");
        String codigo = scanner.nextLine();
        System.out.print("Latitud: ");
        double latitud = scanner.nextDouble();
        System.out.print("Longitud: ");
        double longitud = scanner.nextDouble();
        System.out.print("Superficie (m²): ");
        double superficie = scanner.nextDouble();
        System.out.print("Precio de venta: ");
        double precio = scanner.nextDouble();
        System.out.print("Estado de disponibilidad (true o false): ");
        boolean disponibilidad = scanner.nextBoolean();

        Terreno terreno = new Terreno(codigo, latitud, longitud, superficie, precio, disponibilidad);
        CollectionInmueble.terrenos.add(terreno);
        System.out.println("Terreno registrado con éxito.");
    }

    private static void registrarVivienda() {
        // Aquí va la lógica para registrar una vivienda.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresar los datos de la vivienda:");
        System.out.print("Código de identificación: ");
        String codigo = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Cantidad de habitaciones: ");
        int habitaciones = scanner.nextInt();
        System.out.print("Precio de alquiler mensual: ");
        double precio = scanner.nextDouble();
        System.out.print("Estado de disponibilidad (true o false): ");
        boolean disponibilidad = scanner.nextBoolean();

        Vivienda vivienda = new Vivienda(codigo, direccion, habitaciones, precio, disponibilidad);
        CollectionInmueble.viviendas.add(vivienda);
        System.out.println("Vivienda registrada con éxito.");
    }

    private static void registrarCliente() {
        // Aquí va la lógica para registrar un cliente.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresar los datos del cliente:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Cliente cliente = new Cliente(nombre, apellido, dni, telefono, direccion, email);
        CollectionCliente.clientes.add(cliente);
        System.out.println("Cliente registrado con éxito.");
    }

   private static void alquilerVivienda() {
    // Lógica para registrar un alquiler de vivienda
    Scanner scanner = new Scanner(System.in);

    System.out.println("Alquiler de Vivienda:");
    
    System.out.print("Ingrese el DNI del cliente: ");
    String dni = scanner.nextLine();
    Cliente cliente = CollectionCliente.buscarClientePorDni(dni);
    if (cliente == null) {
        System.out.println("Cliente no encontrado.");
        return;
    }
    
    System.out.print("Ingrese el código de la vivienda a alquilar: ");
    String codigoVivienda = scanner.nextLine();
    Vivienda vivienda = CollectionInmueble.buscarViviendaPorCodigo(codigoVivienda);
    if (vivienda == null || !vivienda.isDisponible()) {
        System.out.println("Vivienda no disponible o no encontrada.");
        return;
    }
    
    System.out.print("Ingrese la duración del alquiler (meses): ");
    int duracion = scanner.nextInt();
    System.out.print("Ingrese los gastos de la inmobiliaria: ");
    double gastosInmobiliaria = scanner.nextDouble();
    
    double montoTotal = vivienda.getPrecioAlquiler() * duracion + gastosInmobiliaria;

    // Registrar el contrato
    ContratoAlquiler contrato = new ContratoAlquiler(duracion, vivienda.getPrecioAlquiler(), gastosInmobiliaria, cliente, vivienda);
    CollectionContrato.contratosAlquiler.add(contrato);
    
    // Marcar la vivienda como no disponible
    vivienda.setDisponible(false);

    System.out.println("Alquiler registrado con éxito. Monto total: " + montoTotal);
}

private static void ventaTerreno() {
    // Lógica para registrar una venta de terreno
    Scanner scanner = new Scanner(System.in);

    System.out.println("Venta de Terreno:");

    System.out.print("Ingrese el DNI del cliente comprador: ");
    String dni = scanner.nextLine();
    Cliente cliente = CollectionCliente.buscarClientePorDni(dni);
    if (cliente == null) {
        System.out.println("Cliente no encontrado.");
        return;
    }
    
    System.out.print("Ingrese el código del terreno a vender: ");
    String codigoTerreno = scanner.nextLine();
    Terreno terreno = CollectionInmueble.buscarTerrenoPorCodigo(codigoTerreno);
    if (terreno == null || !terreno.isDisponible()) {
        System.out.println("Terreno no disponible o no encontrado.");
        return;
    }
    
    System.out.print("Ingrese el valor de los impuestos: ");
    double impuestos = scanner.nextDouble();

    double montoTotal = terreno.getPrecioVenta() + impuestos;

    // Registrar el contrato de venta
    ContratoCompraVenta contrato = new ContratoCompraVenta(terreno.getPrecioVenta(), impuestos, cliente, terreno);
    CollectionContrato.contratosCompraVenta.add(contrato);

    // Marcar el terreno como vendido
    terreno.setDisponible(false);

    System.out.println("Venta registrada con éxito. Monto total: " + montoTotal);
}

private static void consultarInmuebles() {
    // Lógica para consultar los inmuebles disponibles
    Scanner scanner = new Scanner(System.in);
    System.out.println("Consultar Inmuebles:");
    System.out.println("1. Viviendas");
    System.out.println("2. Terrenos");
    System.out.print("Seleccione la opción: ");
    int tipoInmueble = scanner.nextInt();

    if (tipoInmueble == 1) {
        System.out.println("Viviendas disponibles:");
        for (Vivienda vivienda : CollectionInmueble.viviendas) {
            if (vivienda.isDisponible()) {
                vivienda.mostrarDatos();
            }
        }
    } else if (tipoInmueble == 2) {
        System.out.println("Terrenos disponibles:");
        for (Terreno terreno : CollectionInmueble.terrenos) {
            if (terreno.isDisponible()) {
                terreno.mostrarDatos();
            }
        }
    } else {
        System.out.println("Opción no válida.");
    }
}

private static void consultarViviendasAlquiladas() {
    // Lógica para consultar las viviendas alquiladas
    System.out.println("Viviendas alquiladas:");
    for (ContratoAlquiler contrato : CollectionContrato.contratosAlquiler) {
        contrato.mostrarDatos();
    }
}

private static void consultarTerrenosVendidos() {
    // Lógica para consultar los terrenos vendidos
    System.out.println("Terrenos vendidos:");
    double montoTotalVentas = 0;
    for (ContratoCompraVenta contrato : CollectionContrato.contratosCompraVenta) {
        contrato.mostrarDatos();
        montoTotalVentas += contrato.getMontoTotal();
    }
    System.out.println("Monto total de las ventas: " + montoTotalVentas);
}
}