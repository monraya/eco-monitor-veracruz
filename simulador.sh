#!/bin/bash
echo "=== Iniciando Simulador de Estaciones Ambientales (Veracruz Eco-Monitor) ==="

# Ciudades a monitorear en la región
CIUDADES=("PozaRica" "Coatzintla" "Papantla")

while true
do
    for CIUDAD in "${CIUDADES[@]}"
    do
        # Generar métricas aleatorias simuladas
        TEMP=$(( 25 + RANDOM % 15 ))   # Temperatura entre 25°C y 40°C
        HUMID=$(( 60 + RANDOM % 30 ))  # Humedad entre 60% y 90%
        TIMESTAMP=$(date +%s)

        # Formatear los datos en un JSON simple
        DATA="{\"ciudad\":\"$CIUDAD\",\"temperatura\":$TEMP,\"humedad\":$HUMID,\"timestamp\":$TIMESTAMP}"

        echo "Enviando datos de $CIUDAD -> $DATA"

        # Guardar en Redis en una lista para cada ciudad
        redis-cli LPUSH "estacion:$CIUDAD" "$DATA"
        
        # Opcional: Limitar el historial en Redis a las últimas 50 lecturas para optimizar memoria
        redis-cli LTRIM "estacion:$CIUDAD" 0 49
    done
    
    # Esperar 3 segundos antes de la siguiente lectura
    sleep 3
done