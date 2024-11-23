import Conversor.Convertidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner lectura = new Scanner(System.in);
        List<String> historial = new ArrayList<>();
        Convertidor convertidor = new Convertidor();

        //Menu interactivo

        while (true) {
            String menuInteractivo = """
                    *********************************
                    Bienvenidos al conversor de monedas
                    Elija la moneda que desee convertir a dólar estadounidense:
                    
                    1- ARS - Peso Argentino
                    2- BRL - Real Brasileño
                    3- PEN - Sol Peruano
                    4- CLP - Peso Chileno
                    5- COP - Peso colombiano
                    6- Mostrar historial de conversiones
                    7- Salir
                    ***********************************
                    """;

            System.out.println(menuInteractivo);
            System.out.print("Ingrese su opción: ");
            int opcion = lectura.nextInt();

            if (opcion == 7) {
                System.out.println("¡Gracias por usar el conversor! Hasta luego.");
                break;
            }

            switch (opcion) {
                case 1 -> convertirMoneda("ARS", convertidor, historial, lectura);
                case 2 -> convertirMoneda("BRL", convertidor, historial, lectura);
                case 3 -> convertirMoneda("PEN", convertidor, historial, lectura);
                case 4 -> convertirMoneda("CLP", convertidor, historial, lectura);
                case 5 -> convertirMoneda("COP", convertidor, historial, lectura);
                case 6 -> mostrarHistorial(historial);
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void convertirMoneda(String moneda, Convertidor convertidor, List<String> historial, Scanner lectura) {
        String apiUrl = "https://v6.exchangerate-api.com/v6/52db3e7588e05139ed2a3a62/latest/" + moneda;

        try {
            // Solicitar el monto al usuario
            System.out.printf("Ingrese el monto en %s que desea convertir a USD: ", moneda);
            double monto = lectura.nextDouble();

            // Realizar la solicitud y procesar la respuesta
            String respuestaJson = convertidor.realizarSolicitud(apiUrl);
            Map<String, Object> datos = convertidor.procesarRespuesta(respuestaJson);

            // Extraer la conversión a USD
            Map<String, Double> tasas = (Map<String, Double>) datos.get("conversion_rates");
            double tasaUSD = tasas.get("USD");

            // Calcular el resultado
            double montoConvertido = monto * tasaUSD;

            // Mostrar el resultado y guardar en el historial
            System.out.printf("%.2f %s equivalen a %.2f USD%n", monto, moneda, montoConvertido);
            historial.add(monto + " " + moneda + " = " + montoConvertido + " USD");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al realizar la solicitud: " + e.getMessage());
        }
    }

    private static void mostrarHistorial(List<String> historial) {
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones en el historial.");
        } else {
            System.out.println("Historial de conversiones:");
            for (String registro : historial) {
                System.out.println(registro);
            }
        }
    }
}

