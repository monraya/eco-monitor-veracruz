package com.ecomonitor;

import redis.clients.jedis.Jedis;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;

public class Dashboard {
    private static final String REDIS_HOST = "127.0.0.1";
    private static final int REDIS_PORT = 6379;

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            while (true) {
                // Códigos ANSI para limpiar la pantalla de la terminal y mandar el cursor al inicio
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println("=========================================================");
                System.out.println("    SISTEMA DE MONITOREO AMBIENTAL - VERACRUZ ECO-MONITOR ");
                System.out.println("=========================================================");
                System.out.printf("%-15s | %-12s | %-10s | %-10s\n", "ESTACIÓN", "TEMPERATURA", "HUMEDAD", "ESTADO");
                System.out.println("---------------------------------------------------------");

                String[] ciudades = {"PozaRica", "Coatzintla", "Papantla"};

                for (String ciudad : ciudades) {
                    List<String> datos = jedis.lrange("estacion:" + ciudad, 0, 0);
                    
                    if (datos != null && !datos.isEmpty()) {
                        JsonObject json = JsonParser.parseString(datos.get(0)).getAsJsonObject();
                        int temp = json.get("temperatura").getAsInt();
                        int hum = json.get("humedad").getAsInt();
                        
                        // Lógica de Alertas (Sección de Calidad/Reglas de negocio)
                        String estado = "NORMAL";
                        if (temp > 35) {
                            estado = "⚠️ ALTA TEMP";
                        }

                        System.out.printf("%-15s | %-12d | %-10d | %-10s\n", 
                                          ciudad, temp, hum, estado);
                    } else {
                        System.out.printf("%-15s | %-33s\n", ciudad, "Sin datos (Inicie simulador)");
                    }
                }

                System.out.println("=========================================================");
                System.out.println(" Actualizando cada 2 segundos. Presione Ctrl+C para salir.");
                
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.err.println("Error en el Dashboard: " + e.getMessage());
        }
    }
}