# 🧬 Guía de Uso - Mutant Detector API

Esta guía te explica de forma rápida cómo ejecutar y utilizar la API de detección de mutantes.

## 🚀 Ejecutar la Aplicación

### Opción 1: Ejecutar localmente (Recomendado)
Si tienes Java 17 instalado, simplemente ejecuta:

```bash
# Windows
./gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

### Opción 2: Usar Docker
Si prefieres usar contenedores y no instalar Java:

```bash
# 1. Construir la imagen
docker build -t mutant-detector .

# 2. Ejecutar el contenedor (mapeando el puerto 8080)
docker run -p 8080:8080 mutant-detector
```

La aplicación iniciará en el puerto **8080**.

---

## 📡 Uso de la API

### 1. Detectar Mutante (`POST /mutant`)
Envía una secuencia de ADN para analizar.

- **URL:** `http://localhost:8080/mutant`
- **Método:** `POST`
- **Body:** JSON con la matriz de ADN.

**Criterios:**
- **200 OK**: Es un Mutante.
- **403 Forbidden**: Es un Humano.
- **400 Bad Request**: ADN inválido (caracteres extraños, matriz no cuadrada, null).

**Ejemplo de Body (Mutante):**
```json
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
```

**Comando cURL para probar:**
```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{ "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] }'
```

### 2. Ver Estadísticas (`GET /stats`)
Devuelve el conteo de mutantes y humanos verificados, y el ratio.

- **URL:** `http://localhost:8080/stats`
- **Método:** `GET`

**Ejemplo de Respuesta:**
```json
{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
```

**Comando cURL para probar:**
```bash
curl http://localhost:8080/stats
```

---

## 📚 Herramientas Útiles

Una vez la aplicación esté corriendo, puedes acceder a:

- **Swagger UI (Documentación Interactiva):**
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
  *Desde aquí puedes probar los endpoints visualmente sin usar comandos.*

- **Consola H2 (Base de Datos):**
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  *Para ver los registros guardados en la base de datos en memoria.*

- **Link en Render**
    https://mutantes-fernandezbruno50891.onrender.com/swagger-ui/index.html
