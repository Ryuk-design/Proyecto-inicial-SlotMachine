# Proyecto-inicial-SlotMachine
## Prueba de aceptación

Esta historia recorre todo lo que hace el programa. Participan dos actores: el usuario, que maneja la máquina, y el solver, que resuelve el problema de la maratón.

### Capítulo 1: el casino abre
El usuario crea una máquina vacía y la hace visible. Si intenta girarla, el sistema avisa que no hay ruedas.

### Capítulo 2: llegan los símbolos
El usuario agrega cinco símbolos: purple, orange (Shy), green (Ephemeral), yellow y brown, y los consulta con `symbols`. Si intenta repetir un color o usar un tipo de símbolo que no existe, el sistema lo rechaza.

### Capítulo 3: se arman las ruedas
El usuario agrega cinco ruedas: normal, Lefty en la posición 2, normal, Rebel en la posición 4 y normal. Un tipo de rueda que no existe es rechazado.

### Capítulo 4: primera jugada
El usuario coloca un símbolo distinto en cada rueda: purple, orange, green, yellow y brown. Al consultar la configuración ve los cinco colores, hay 5 símbolos distintos y no hay jackpot.

### Capítulo 5: se gira
- La rueda 1 avanza 2 pasos y se detiene en green, el símbolo Ephemeral se encoge.
- La rueda 3 retrocede 1 paso, se detiene en Orange, el símbolo Shy se esconde.
- El usuario gira todas las ruedas, la rueda Lefty siempre muestra el mismo símbolo que la rueda de su izquierda.
- El usuario fija una configuración completa: brown, yellow, green, orange y purple.

### Capítulo 6: se reorganiza la máquina
- Se intercambian las ruedas 1 y 3. La Lefty pasa a copiar a su nueva vecina de la izquierda.
- La Rebel se niega a ser intercambiada, bloqueada o eliminada.
- Una rueda bloqueada no gira, ni sola ni junto con las demás, hasta que se desbloquea.
- Se elimina la última rueda y quedan cuatro.

### Capítulo 7: cambia el repertorio
El usuario elimina el símbolo brown. Su color queda libre y lo vuelve a agregar. Si intenta eliminar un color que nunca agregó, el sistema lo rechaza.

### Capítulo 8: jackpot
El usuario coloca yellow en todas las ruedas: hay jackpot y un solo símbolo distinto. Al cambiar una rueda, el jackpot desaparece.

### Capítulo 9: se hace invisible la máquina
Se consigue el jackpot a la vista y se oculta la maquina.

### Capítulo 10: llega el solver
El Solver resuelve el problema para 50 ruedas con menos de 10 000 acciones y simula un caso de 6 ruedas de forma visual. Una cantidad de ruedas fuera del rango de 3 a 50 es rechazada.

### Cierre
El usuario termina la noche cerrando el simulador.
