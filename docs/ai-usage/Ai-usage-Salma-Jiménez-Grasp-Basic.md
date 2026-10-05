## Entry 5
- **Date:** 2026-10-05
- **Tool:** Claude
- **Objective:** Crear las diapositivas del tema GRASP básicos de acuerdo con los requisitos del plan de exposiciones.
- **Query:** "GRASP básicos, Information Expert, Creator, Controller, Low Coupling, High Cohesion. Quiero unas diapositivas de estos temas" (luego se subió el PDF del Plan de Exposiciones).
- **Response:** Generó 9 diapositivas: concepto de GRASP, un patrón por lámina con código antes/después y trade-off, un caso real (Ariane 5, con fuente) y la relación con otros temas. Las entregó en versión web, PowerPoint y PDF.
- **Decision:** Se adoptó la estructura propuesta. Se verificó el caso Ariane 5 contra el informe original y se aclaró que es una analogía de diseño, no un caso literal de GRASP mal usado.

---

## Entry 6
- **Date:** 2026-10-05
- **Tool:** Claude
- **Objective:** Definir una dinámica para el público que haga la exposición más participativa.
- **Query:** "Quiero que sea una expo chévere y las preguntas pueden ser en Kahoot o un crucigrama, o sea tipo dinámica."
- **Response:** Creó un crucigrama interactivo en HTML con 12 palabras, con botones para comprobar, revelar y reiniciar, y propuso ideas de dinámica como un Kahoot de preguntas de aplicación.
- **Decision:** Se eligió el crucigrama como actividad de cierre y se pidió que funcionara descargado, con sus botones, para usarlo sin depender de internet.

---

## Entry 7
- **Date:** 2026-10-05
- **Tool:** Claude
- **Objective:** Ampliar el crucigrama a 20 pistas conceptuales basadas en una fuente externa.
- **Query:** "3,2,5,11,12, me la dejas en el crucigrama, saca las preguntas de aquí, saca 20, no solo que sean patrón que hace tal sino qué problema arregla, cuándo no usar, qué evita" + enlace del artículo de Medium "GRASP: Guiding Object-Oriented Design in Java".
- **Response:** Armó un crucigrama de 20 palabras conservando las cinco indicadas y agregando pistas sobre el problema que resuelve cada patrón, cuándo no usarlo y qué evita. Algunas ideas, como fábrica y pruebas, van más allá de lo que el artículo desarrolla.
- **Decision:** Se aceptó el enfoque conceptual. Se tomó nota de que algunas pistas no salen textualmente de la fuente y que debían contrastarse.

---

## Entry 8
- **Date:** 2026-10-05
- **Tool:** Claude
- **Objective:** Quitar de las pistas los ejemplos del código y dejarlas solo sobre conceptos GRASP.
- **Query:** "Que no tengan que ver con los ejemplos, solo con los GRASP. 16, 8, 12, 18, 19 me las dejas, y las 9, 2, 1, 7, 6, 3, 11 me dejas esas."
- **Response:** Conservó las 12 palabras indicadas, reescribió la pista de COHESION sin ejemplos y reemplazó las otras 8 por pistas generales (propagación, flexibilidad, reutilización, simplicidad, información, coordinar, inicializar y fragmentación).
- **Decision:** Se adoptó la versión final del crucigrama. Quedó pendiente abrirlo en el navegador y resolverlo una vez antes de exponer, porque solo se verificó que el código no tuviera errores de sintaxis.