# Payments Api


### Descripción
Resolución de examen técnico. Api de gestión de estado de pagos. 
En rol de procesador, recibe solicitudes de pago, las cursa, y gestiona sus estados.



### Requisitos
- JDK 17
- Maven 3.8+ apuntando al JDK 17

### Ejeuctar el proyecto
Con las variables de entorno configuradas:
1) "mvn clean package" - para descargar dependencias y compilar el JAR
2) "java -jar target/paymentsApi-0.0.1-SNAPSHOT.jar " - ejecución en el servidor de test

## Analisis del requerimineto

### Objetivos
Mejorar los servicios dedicados a la gestión de pagos y monedas (no conocemos el sistema anterior).

### Requisitos técnicos

#### Seguridad de la información
- Consistencia
- Idempotencia

#### Arquitectura
- Procesamiento en tiempo real (¿100% sincrónico?)
- Escalabilidad
- Resiliencia
- Volumen
- Confiabilidad
- Estructuras y patrones que permitan la integración de nuevos métodos de pago sin necesidad de refactorización significativa.

#### Requisitos de implementación
- Java 17 o 11
- Spring Boot

### Requisitos funcionales

#### Generalidades
- Soporte para múltiples monedas (con conversión) y tipos de transacciones.
- Mecanismo de compensación.
- Presentar una solución que se integre fácilmente con un frontend que pueda consultar el historial de transacciones y el estado de cada una en tiempo real.
- Fácil de extender con nuevos tipos de transacciones, con una separación clara entre componentes y lógica de negocio.
- Incluir diagramas de arquitectura y especificar los servicios involucrados, con un README claro.

#### Endpoints
- Iniciar una transacción de pago (con soporte para tarjeta, transferencia y P2P).
- Consultar el estado de una transacción.
- Listar transacciones de un usuario, con soporte para búsqueda, filtrado y ordenación.