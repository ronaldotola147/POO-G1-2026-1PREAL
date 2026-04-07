package pe.edu.upeu;

import java.util.Scanner;

// 1. Clase Abstracta MetodoPago (Implementa Template Method)
abstract class MetodoPago {
    protected double monto;

    // Template Method: Define la secuencia de pasos para realizar una transacción
    public final void realizarTransaccion(double monto) {
        this.monto = monto;
        System.out.println("\nIniciando transacción para S/" + monto);
        procesarPago();
        verificarPago();
        enviarConfirmacion();
        System.out.println("Transacción finalizada.");
    }

    // 2. Método abstracto que las subclases deben implementar de forma específica
    protected abstract void procesarPago();

    // Métodos concretos con implementación por defecto (pueden ser sobrescritos si es necesario)
    protected void verificarPago() {
        System.out.println("  - Verificando estado del pago...");
        System.out.println("  - Pago verificado exitosamente.");
    }

    protected void enviarConfirmacion() {
        System.out.println("  - Enviando confirmación al cliente.");
    }
}

// 3. Clases Concretas de Metodos de Pago

class PagoTarjetaCredito extends MetodoPago {
    private String numeroTarjeta;
    private String titular;

    public PagoTarjetaCredito(String numeroTarjeta, String titular) {
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
    }

    @Override
    protected void procesarPago() {
        System.out.println("  - Procesando pago con Tarjeta de Crédito.");
        System.out.println("  - Tarjeta: " + numeroTarjeta.substring(0, 4) + "xxxxxxxx" + numeroTarjeta.substring(12) + " (Titular: " + titular + ")");
        System.out.println("  - Conectando con pasarela de pago...");
        System.out.println("  - Cargo de S/" + monto + " realizado a la tarjeta.");
    }

    @Override
    protected void enviarConfirmacion() {
        super.enviarConfirmacion(); // Llama al método de la clase padre
        System.out.println("  - Se envió un comprobante electrónico al email registrado de " + titular + ".");
    }
}

class PagoEfectivo extends MetodoPago {
    @Override
    protected void procesarPago() {
        System.out.println("  - Procesando pago en Efectivo.");
        System.out.println("  - Esperando confirmación de recepción de S/" + monto + " en caja.");
        // Simulación de un paso manual o interacción con hardware
        System.out.println("  - Efectivo recibido. Transacción en curso.");
    }

    @Override
    protected void verificarPago() {
        System.out.println("  - Verificando billetes y monedas...");
        System.out.println("  - Verificación manual completada.");
    }
}

class PagoTransferencia extends