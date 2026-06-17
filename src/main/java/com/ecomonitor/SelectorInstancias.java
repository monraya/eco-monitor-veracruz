package com.ecomonitor;

import redis.clients.jedis.Jedis;
import java.util.List;

public class SelectorInstancias {
    // Conexión por defecto al servidor local de Redis en el puerto 6329
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int REDIS_PORT = 6379;

    public static void main(String[] args) {
        System.out.println("=== Conectando al Servidor Redis en Ubuntu ===");
        
        // Crear la conexión usando un bloque try-with-resources para asegurar el cierre del socket
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            // Probar la conexión con un PING
            String respuesta = jedis.ping();
            System.out.println("Respuesta del servidor Redis (PING): " + respuesta);
            
            System.out.println("\n=== Monitoreando últimas lecturas de la región ===");
            String[] ciudades = {"PozaRica", "Coatzintla", "Papantla"};

            for (String ciudad : ciudades) {
                String llave = "estacion:" + ciudad;
                // Recuperar la última lectura (posición 0 en la lista de Redis)
                List<String> lecturas = jedis.lrange(llave, 0, 0);
                
                if (lecturas != null && !lecturas.isEmpty()) {
                    System.out.println("Último registro de [" + ciudad + "]: " + lecturas.get(0));
                } else {
                    System.out.println("No hay datos recientes para la estación de: " + ciudad);
                }
            }
        } catch (Exception e) {
            System.err.println("Error crítico al intentar conectar con Redis: " + e.getMessage());
        }
    }
}