# 📈 EcoSystems Pro: Analizador de Inversiones y Consultoría Operativa - C&S (Capital & Systems) 💼💻

Este proyecto es una solución integral desarrollada en **Java Swing** por la firma de software **C&S (Capital & Systems)** para la automatización de análisis financieros y la toma de decisiones gerenciales. Diseñado para el ámbito de la **Ingeniería Económica**, el software permite evaluar escenarios de viabilidad, comparar proyectos mutuamente excluyentes y diagnosticar la salud operativa de una empresa.

El sistema destaca por su robustez técnica y comercial, implementando el patrón de diseño estricto **MVC** (Modelo-Vista-Controlador), procesamiento asíncrono y una interfaz gráfica de última generación inspirada en aplicaciones web y Dashboards en la nube.

## 🚀 Características Principales

### 🏛️ Arquitectura y Gestión de Ventanas (MDI)
* **Gestión Inteligente (VentManager):** Algoritmo controlador que audita la apertura de ventanas en el escritorio virtual (`JDesktopPane`). Para optimizar la memoria y evitar colisiones visuales, el sistema cierra automáticamente cualquier módulo activo y maximiza la nueva ventana solicitada, garantizando una experiencia fluida.
* **Motor de Diagnóstico Universal (Principio DRY):** Implementación de la clase maestra `FrmAnalisisDialog`, capaz de interceptar cadenas HTML generadas por los controladores y renderizar reportes gerenciales responsivos y estandarizados para cualquier módulo del sistema.

### 📊 Módulos de Inteligencia Financiera
* **Evaluación de Proyectos (VAN y TIR):** Motor iterativo con el algoritmo de **Newton-Raphson** para cálculos de alta precisión. Adaptabilidad para tasas anuales, semestrales, trimestrales y mensuales, junto con simulación de amortización.
* **Comparador de Alternativas:** Sistema de desempate para proyectos mutuamente excluyentes. Aplica la regla de oro de la ingeniería económica: priorizando matemáticamente la magnitud del Valor Actual Neto (VAN) sobre la Tasa Interna de Retorno (TIR).
* **Dashboard de Salud Financiera:** Panel semántico que evalúa en tiempo real el Capital de Trabajo, Margen de Utilidad y Nivel de Deuda, generando alertas automáticas sobre el riesgo de apalancamiento y liquidez.

### ⚙️ Optimizador y Análisis de Riesgo
* **Punto de Equilibrio (Volumen Crítico):** Simulación del volumen mínimo de ventas requerido para cubrir costos operacionales, apoyado por exportación de matrices de escenarios a CSV.
* **Análisis de Sensibilidad (What-If):** Interfaz basada en *Sliders* interactivos que proyectan escenarios optimistas y pesimistas alterando ingresos, costos y tasas de interés dinámicamente.

### ✨ Experiencia de Usuario (UI/UX)
* **Diseño "Card UI":** Abandono del lienzo plano tradicional de Java por un diseño de "Tarjetas Flotantes". Los paneles blancos con bordes de sombra suave se superponen sobre un fondo corporativo "Gris Humo", reduciendo la carga cognitiva.
* **FlatLaf:** Inyección de *Client Properties* nativas que dotan a los cuadros de texto de botones de limpieza rápida (`showClearButton`), anillos de enfoque dinámicos (`outline: focus`) y botones redondeados interactivos (`roundRect`).
* **Multithreading:** Ejecución de los cálculos pesados en hilos secundarios utilizando `SwingUtilities.invokeLater`, garantizando que la interfaz (UI) nunca sufra bloqueos.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java (JDK 8 o superior).
* **GUI:** Java Swing & AWT.
* **Arquitectura:** MVC (Modelo-Vista-Controlador).
* **Renderizado Visual:** Librería `flatlaf-3.5.x.jar`.

## 📂 Estructura Arquitectónica del Proyecto
El software respeta la Separación de Responsabilidades (*Separation of Concerns*):
* `📁 models`: Lógica matemática pura y estructuras de datos (Agnóstica a la interfaz gráfica).
* `📁 views`: Clases de presentación visual e interfaces *Card UI* (`FrmDashboardFinanciero`, `FrmPuntoEquilibrio`, etc.).
* `📁 controllers`: Orquestadores que capturan eventos (`ActionListeners`) y comunican a las Vistas con los Modelos.
* `📁 utils`: Herramientas transversales como el `VentManager`.
* `📁 main`: Punto de arranque (*Bootstrapping*) y configuración de temas.

## ⚙️ Instalación y Ejecución
1. Clona el repositorio:
   ```bash
   git clone [https://github.com/tu-usuario/ecosystems-pro.git](https://github.com/tu-usuario/ecosystems-pro.git)
2.  Importa el proyecto en tu IDE favorito (VS Code, IntelliJ, NetBeans).
3.  Asegúrate de incluir la librería `.jar` (`flatlaf`) en el *Build Path* o en la carpeta `lib/` de las dependencias de tu proyecto.
4.  Ejecuta la clase principal (`Main.java`) para inicializar el tema visual y lanzar el `MenuPrincipal`.

## ✍️ Autor
* **Jeslyng David Maldonado Vivas** - Estudiante de Ingeniería de Sistemas - **Universidad Nacional de Ingeniería (UNI)**.

---
*Este proyecto fue desarrollado aplicando fundamentos de Ingeniería Económica, Programación Estructurada y Patrones de Diseño Arquitectónico basado en los requerimientos de la clase de Programacion II.*
