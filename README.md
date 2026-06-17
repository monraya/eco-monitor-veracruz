# Veracruz Eco-Monitor 🌍🛰️

Sistema inteligente de monitoreo ambiental modular en tiempo real para la región norte de Veracruz (Poza Rica, Coatzintla y Papantla). Este proyecto implementa una arquitectura desacoplada utilizando un simulador de sensores en Bash, una base de datos en memoria con Redis y un backend de procesamiento de datos con interfaz interactiva en Java 17 gestionado por Maven.

## 🚀 Arquitectura del Sistema
- **Simulador (Capa de Captura):** Script en Bash (`simulador.sh`) que genera de forma continua métricas aleatorias de temperatura y humedad para las distintas estaciones meteorológicas regionales, inyectándolas en estructuras de datos de tipo lista en Redis.
- **Persistencia (Capa de Almacenamiento):** Servidor Redis local que actúa como bróker de datos en memoria a alta velocidad.
- **Dashboard (Capa de Procesamiento y Visualización):** Aplicación Java (`Dashboard.java`) que extrae los datos JSON de Redis mediante la librería Jedis, procesa reglas de negocio (alertas por altas temperaturas) y renderiza una interfaz interactiva en consola mediante códigos ANSI de refresco automático.

---

## 🔄 Flujo de Información en Tiempo Real
1. El script **`simulador.sh`** genera métricas ambientales en un bucle continuo.
2. Los datos se envían mediante la instrucción `LPUSH` a la base de datos distribuida en memoria **Redis**.
3. El conector **Jedis** en Java extrae la última lectura mediante un mapeo con un rango indexado (`lrange key 0 0`).
4. La librería **Gson** deserializa la cadena de texto plano a un objeto estructurado `JsonObject`.
5. El **`Dashboard.java`** procesa los umbrales de temperatura y refresca la vista interactiva automáticamente cada 2 segundos.

---

## 🛠️ Requisitos Previos e Infraestructura
Asegúrese de contar con las siguientes herramientas instaladas y configuradas en su entorno Linux:
- **Java JDK 17** y **Apache Maven 3.x** instalados.
- **Servidor Redis** ejecutándose en el puerto estándar 6379.
- **Entorno de desarrollo:** Ubuntu Linux alojado en un entorno virtualizado local.
- **Mecanismo de acceso:** Conexión remota mediante Servidor SSH a través de reenvío de puertos.

---

## 💻 Instrucciones de Ejecución

Siga estos pasos en orden utilizando terminales independientes dentro del directorio raíz del proyecto:

### 1. Iniciar el Servidor Redis (Si no se ejecuta en segundo plano)

    sudo service redis-server start

### 2. Ejecutar el Simulador de Sensores (Bash)
Abra una terminal, otorgue permisos de ejecución al script e inícielo para comenzar a transmitir lecturas meteorológicas en tiempo real:

    chmod +x simulador.sh
    ./simulador.sh

### 3. Arrancar el Dashboard Interactivo (Java)
Abra una **segunda terminal** independiente y ejecute el comando de Maven para compilar las dependencias e iniciar la interfaz de usuario en tiempo real:

    mvn compile exec:java

### 4. Generar el Empaquetado Distribuible (.JAR)
Para compilar y empaquetar el proyecto completo con todas sus dependencias en un único archivo binario ejecutable:

    mvn clean package

El archivo binario se generará dentro de la carpeta `target/` con el nombre `eco-monitor-veracruz-1.0.0-jar-with-dependencies.jar`.

---

## 📌 Control de Versiones y Gestión
- **Flujo de Trabajo:** Uso de ramas locales de características (`feature/`) para el aislamiento de código.
- **Versionamiento Semántico:** Hito formal de entrega etiquetado bajo el estándar Git Tags con la versión oficial `v1.0.0`.