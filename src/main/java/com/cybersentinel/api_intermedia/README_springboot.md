# `api_intermedia` — Cyber Sentinel (Wilmer — Líder Spring Boot)

Esta carpeta contiene el servicio Spring Boot del caso CyberSentinel, actuando como intermediario entre el frontend y el modelo de predicción.


este Contexto exige construir una solución donde React envía datos a Spring Boot, y este los reenvía a un servicio Python. El controlador de esta carpeta actúa como un "Backend for Frontend" (BFF): recibe las variables, protege el sistema filtrando datos sucios mediante validaciones estrictas y consume la IA para devolver el resultado visual a la interfaz.

## Requisitos del servicio

- Java 21
- Spring Boot (Dependencias: Spring Web, Validation)
- Puerto de ejecución: 8080
- Endpoint principal: POST /api/prediccion/cyber-sentinel
- Conexión saliente: Requiere que el servicio Python esté corriendo en el puerto 8001.


## Lógica de intermediación

la lógica de esta capa se centra en seguridad y enrutamiento:
- Validación DTO: Se utiliza `CyberRequestDTO` con Jakarta Validation (`@Min`, `@Max`, `@NotNull`) para asegurar que las variables técnicas respeten los rangos del negocio antes de procesarlas.
- Consumo REST: Se utiliza `RestTemplate` para encapsular la llamada HTTP hacia el modelo.
-CORS: Se configuran los orígenes cruzados (`@CrossOrigin`) para permitir las peticiones exclusivas desde el puerto 5173 (React)[cite: 1].

## Instalación y Preparación

1. Abrir la carpeta `api_intermedia` en VSCode.
2. Esperar a que el IDE reconozca el archivo `pom.xml` y descargue automáticamente las dependencias de Maven.


## Ejecutación localmente 

Desde el IDE de VSCode:
- Abrir el archivo `src/main/java/com/cybersentinel/api_intermedia/ApiIntermediaApplication.java`.
- Hacer clic en el botón de "Run".
- La terminal confirmará el inicio con el mensaje: `Tomcat initialized with port 8080`.


## Flujo simple para alguien nuevo

1. El frontend (React) envía un JSON con las seis variables al endpoint de Spring Boot en el puerto 8080[cite: 1].
2. Spring Boot intercepta el request y valida que ningún número sea negativo o sobrepase los límites.
3. Si los datos son válidos, Spring Boot hace un POST interno a la API de Python en el puerto 8001[cite: 1].
4. Python responde, y Spring Boot toma ese JSON enriquecido (score, ranking, recomendaciones) y se lo devuelve a React[cite: 1].
5. Si Python está caído o los datos son inválidos, Spring Boot devuelve un error HTTP 400 o 500 controlado.

## Probamos el endpoint en Postman

Se debe apuntar a la ruta de Spring Boot (`8080`), asegurándose de que Python (`8001`) esté encendido en segundo plano.


```bash
curl -X POST http://localhost:8080/api/prediccion/cyber-sentinel \
  -H "Content-Type: application/json" \
  -d '{"intentos_login_fallidos":120,"puertos_abiertos":35,"vulnerabilidades_criticas":8,"trafico_anomalo_pct":76,"equipos_afectados":90,"parcheado_pct":48}'
```

## Ejemplo de request

```json
{
  "intentos_login_fallidos": 120,
  "puertos_abiertos": 35,
  "vulnerabilidades_criticas": 8,
  "trafico_anomalo_pct": 76,
  "equipos_afectados": 90,
  "parcheado_pct": 48
}
```
ingresamos estos datos en JSON en Postman, con POST para hacer las peticiones, luego le damos a "SEND".


## (Ejemplo Response) Respuesta Esperada por Postman

```json
{
  "prediccion": "CRITICO",
  "confianza": 0.99,
  "ranking": 4,
  "score": 212.55,
  "recomendaciones": [
    "Priorizar parcheo de vulnerabilidades criticas.",
    "Analizar trafico y aislar segmentos con comportamiento anomalo.",
    "Activar bloqueo temporal y revision de credenciales."
  ],
  "justificacion": "Prediccion CRITICO con score de riesgo 212.55. Variables clave..."
}
```
y esto sería la conexión de pruebas exitosa.
