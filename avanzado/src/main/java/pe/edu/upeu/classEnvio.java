package pe.edu.upeu;

// 1. Clase abstracta Envio (implementa el Template Method)
abstract class Envio {
    protected double distanciaKm;
    protected double pesoKg;

    public Envio(double distanciaKm, double pesoKg) {
        this.distanciaKm = distanciaKm;
        this.pesoKg = pesoKg;
    }

    // 2. Template Method: Define la estructura del algoritmo
    public final double calcularCostoTotal() {
        double costoBase = calcularCostoBase();
        double costoAdicionalPorPeso = calcularCostoAdicionalPorPeso();
        double descuento = aplicarDescuento(costoBase + costoAdicionalPorPeso);

        System.out.printf("  - Costo base: S/%.2f\n", costoBase);
        System.out.printf("  - Costo adicional por peso: S/%.2f\n", costoAdicionalPorPeso);
        System.out.printf("  - Descuento aplicado: S/%.2f\n", descuento);

        return (costoBase + costoAdicionalPorPeso) - descuento;
    }

    // 3. Métodos abstractos (pasos que las subclases deben implementar)
    protected abstract double calcularCostoBase();
    protected abstract double calcularCostoAdicionalPorPeso();
    protected abstract double aplicarDescuento(double subtotal);

    // Método hook (opcional): las subclases pueden sobrescribirlo pero no es obligatorio
    protected void imprimirMensajePersonalizado() {
        // Por defecto no hace nada, las subclases pueden añadir su propio mensaje.
    }
}

// 4. Subclase concreta: EnvioEstandar
class EnvioEstandar extends Envio {
    private static final double TARIFA_BASE_KM = 0.5; // S/ por km
    private static final double TARIFA_PESO_ADICIONAL = 0.2; // S/ por kg adicional
    private static final double PESO_MAX_INCLUIDO = 5.0; // kg incluidos en el costo base
    private static final double DESCUENTO_POR_DISTANCIA_LARGA = 0.10; // 10% de descuento

    public EnvioEstandar(double distanciaKm, double pesoKg) {
        super(distanciaKm, pesoKg);
    }

    @Override
    protected double calcularCostoBase() {
        return distanciaKm * TARIFA_BASE_KM;
    }

    @Override
    protected double calcularCostoAdicionalPorPeso() {
        if (pesoKg > PESO_MAX_INCLUIDO) {
            return (pesoKg - PESO_MAX_INCLUIDO) * TARIFA_PESO_ADICIONAL;
        }
        return 0;
    }

    @Override
    protected double aplicarDescuento(double subtotal) {
        if (distanciaKm > 100) { // Descuento si la distancia es larga
            return subtotal * DESCUENTO_POR_DISTANCIA_LARGA;
        }
        return 0;
    }

    @Override
    protected void imprimirMensajePersonalizado() {
        System.out.println("  - Tipo de envío: Estándar (entrega en 3-5 días hábiles).");
    }
}

// 4. Subclase concreta: EnvioExpress
class EnvioExpress extends Envio {
    private static final double TARIFA_BASE_KM = 1.2; // Más caro por km
    private static final double TARIFA_PESO_ADICIONAL = 0.5; // Más caro por kg adicional
    private static final double RECARGO_URGENCIA = 10.0; // Recargo fijo por express
    private static final double DESCUENTO_VOLUMEN = 0.05; // 5% de descuento por gran volumen

    public EnvioExpress(double distanciaKm, double pesoKg) {
        super(distanciaKm, pesoKg);
    }

    @Override
    protected double calcularCostoBase() {
        return (distanciaKm * TARIFA_BASE_KM) + RECARGO_URGENCIA;
    }

    @Override
    protected double calcularCostoAdicionalPorPeso() {
        return pesoKg * TARIFA_PESO_ADICIONAL; // Cada kg adicional tiene costo
    }

    @Override
    protected double aplicarDescuento(double subtotal) {
        if (pesoKg > 20) { // Descuento por gran volumen
            return subtotal * DESCUENTO_VOLUMEN;
        }
        return 0;
    }

    @Override
    protected void imprimirMensajePersonalizado() {
        System.out.println("  - Tipo de envío: Express (entrega en 1-2 días hábiles).");
    }
}

// Clase principal para probar el sistema
class SimuladorEnvios {
    public static void main(String[] args) {
        System.out.println("--- SIMULADOR DE COSTOS DE ENVÍO ---");

        System.out.println("\nCalculando costo para un Envío Estándar:");
        Envio envio1 = new EnvioEstandar(80, 7); // 80 km, 7 kg
        envio1.imprimirMensajePersonalizado(); // Usando el método hook
        double costo1 = envio1.calcularCostoTotal();
        System.out.printf("Costo total Envío Estándar: S/%.2f\n", costo1);

        System.out.println("\nCalculando costo para un Envío Express:");
        Envio envio2 = new EnvioExpress(50, 10); // 50 km, 10 kg
        envio2.imprimirMensajePersonalizado();
        double costo2 = envio2.calcularCostoTotal();
        System.out.printf("Costo total Envío Express: S/%.2f\n", costo2);

        System.out.println("\nCalculando costo para otro Envío Estándar (larga distancia, poco peso):");
        Envio envio3 = new EnvioEstandar(150, 3); // 150 km, 3 kg
        envio3.imprimirMensajePersonalizado();
        double costo3 = envio3.calcularCostoTotal();
        System.out.printf("Costo total Envío Estándar: S/%.2f\n", costo3);

        System.out.println("\nCalculando costo para otro Envío Express (gran volumen):");
        Envio envio4 = new EnvioExpress(30, 25); // 30 km, 25 kg
        envio4.imprimirMensajePersonalizado();
        double costo4 = envio4.calcularCostoTotal();
        System.out.printf("Costo total Envío Express: S/%.2f\n", costo4);
    }
}