---
config:
  theme: redux
  layout: fixed
---
flowchart TB
    n7["GameZone"] --> n10["Person &lt;&gt;"] & n20["Product &lt;&gt;"] & n23["Sale"]
    n10 --> n14["customer"] & n15["seller"]
    n20 --> n21["videogame"] & n22["console"]
    n1["HIERARCHY DIAGRAM FOR GAMEZONE MANAGEMENT"]

    n7@{ shape: rect}
    n10@{ shape: rect}
    n1@{ shape: text}
    style n7 fill:#FFCDD2,stroke:#dbabb0
    style n10 fill:#C8E6C9,stroke:#00C853
    style n20 fill:#C8E6C9,stroke:#00C853
    style n23 fill:#C8E6C9,stroke:#00C853
    style n14 fill:#BBDEFB,stroke:#2962FF
    style n15 fill:#BBDEFB,stroke:#2962FF
    style n21 fill:#BBDEFB,stroke:#2962FF
    style n22 fill:#BBDEFB,stroke:#2962FF