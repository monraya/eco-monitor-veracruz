# Veracruz Eco-Monitor 🌍🛰️

Sistema inteligente de monitoreo ambiental modular en tiempo real para la región norte de Veracruz (Poza Rica, Coatzintla y Papantla). Este proyecto implementa una arquitectura desacoplada utilizando un simulador de sensores en Bash, una base de datos en memoria con Redis y un backend de procesamiento de datos con interfaz interactiva en Java 17 gestionado por Maven.

## 🚀 Arquitectura del Sistema
- **Simulador (Capa de Captura):** Script en Bash (`simulador.sh`) que genera de forma continua métricas aleatorias de temperatura y humedad para las distintas estaciones meteorológicas regionales, inyectándolas en estructuras de datos de tipo lista en Redis.
- **Persistencia (Capa de Almacenamiento):** Servidor Redis local que actúa como bróker de datos en memoria a alta velocidad.
- **Dashboard (Capa de Procesamiento y Visualización):** Aplicación Java (`Dashboard.java`) que extrae los datos JSON de Redis mediante la librería Jedis, procesa reglas de negocio (alertas por altas temperaturas) y renderiza una interfaz interactiva en consola mediante códigos ANSI de refresco automático.

---

## 🛠️ Requisitos Previos
Asegúrese de contar con las siguientes herramientas instaladas en su entorno Linux/Ubuntu:
- Java JDK 17
- Apache Maven 3.x
- Servidor Redis (ejecutándose en el puerto estándar 6379)

---

## 💻 Instrucciones de Ejecución

Siga estos pasos en orden utilizando terminales independientes dentro del directorio raíz del proyecto:

### 1. Iniciar el Servidor Redis (Si no se ejecuta en segundo plano)
```bash
sudo service redis-server start