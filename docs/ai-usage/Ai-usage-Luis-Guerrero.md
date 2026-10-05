# AI Log / Luis Alejandro Guerrero Gonzalez

## Entry 1
- **Date:** 2026-10-04
- **Tool:** Gemini
- **Objective:** Resolver dudas conceptuales sobre cómo estructurar la presentación de los 20 minutos según los requisitos del docente.
- **Query:** "¿Cómo debemos repartir los patrones GRASP básicos entre los 4 integrantes del grupo para cumplir con el plan de exposiciones?"
- **Response:** Se sugirió asignar un patrón por integrante y repartir los patrones restantes o la integración técnica según las directrices del formato oficial.
- **Decision:** Se organizó el orden de exposición definiendo qué parte teórica y práctica defenderá cada integrante en la sesión.

---

## Entry 2
- **Date:** 2026-10-04
- **Tool:** Gemini
- **Objective:** Preparar argumentos sólidos para el momento del debate y la defensa de trade-offs.
- **Query:** "¿Qué son los trade-offs de aplicar los patrones GRASP y en qué casos se consideraría sobreingeniería?"
- **Response:** Explicó que los trade-offs evalúan el balance entre flexibilidad y complejidad innecesaria, ayudando a argumentar cuándo un patrón aporta valor real.
- **Decision:** Se integraron notas conceptuales sobre los trade-offs de cada patrón para responder con seguridad en el debate contra los grupos pares.

---

## Entry 3
- **Date:** 2026-10-04
- **Tool:** Gemini
- **Objective:** Comprender a fondo la relación y diferencia entre los patrones Creator e Information Expert.
- **Query:** "¿Cómo se relacionan el patrón Creator y el patrón Information Expert al momento de decidir qué clase debe instanciar un nuevo objeto?"
- **Response:** Explicó que el patrón Creator es en realidad una especialización del Information Expert, donde la clase elegida para crear el objeto es aquella que posee una relación cercana o la información de inicialización necesaria.
- **Decision:** Se preparó una aclaración teórica para explicar esta conexión durante la sustentación ante el docente y el grupo.

---

## Entry 4
- **Date:** 2026-10-04
- **Tool:** Gemini
- **Objective:** Comprender a profundidad el funcionamiento y la importancia del patrón Controller dentro de la arquitectura de la aplicación.
- **Query:** "¿Por qué es un error arquitectónico que la interfaz de usuario ('ConsoleUS') contenga lógica de negocio como validaciones de tipo (instanceof) y cómo lo resuelve el patrón Controller?"
- **Response:** Explicó que la UI debe mantenerse "tonta" para desacoplar la presentación de las reglas del sistema, delegando dichas decisiones a un controlador o servicio para evitar código frágil ante futuros cambios.
- **Decision:** Se estructuró la explicación teórica y el ejemplo práctico del patrón Controller para la defensa de la exposición.