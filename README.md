# 📈 EcoSystems Pro: Analizador de Inversiones y Consultoría Operativa-(En proceso) 💼💻

Este proyecto es una solución integral desarrollada en **Java Swing** para la automatización de análisis financieros y la toma de decisiones operativas. Diseñado para el ámbito de la **Ingeniería Económica**, el software permite proyectar escenarios de viabilidad, calcular indicadores de rentabilidad y optimizar procesos empresariales.

El software destaca por su robustez técnica, implementando el patrón de diseño **MVC** (Modelo-Vista-Controlador), procesamiento asíncrono y una interfaz gráfica de última generación.

## Características Principales

### 🏛️ Arquitectura y Gestión de Ventanas (MDI)
* **MDI (Multiple Document Interface):** Arquitectura centralizada que permite la ejecución de múltiples formularios internos (`JInternalFrame`) sobre un escritorio virtual (`JDesktopPane`).
* **VentManager:** Algoritmo controlador que audita la apertura de ventanas en el sistema. Asegura que solo se pueda abrir una instancia de cada herramienta a la vez, mostrando alertas si se intenta duplicar, optimizando así el consumo de memoria RAM y el orden del escritorio.

### 📊 Módulo de Evaluación Financiera (VAN y TIR)
* **Motor de Cálculo Iterativo:** Implementación del algoritmo matemático de **Newton-Raphson** para calcular con alta precisión la Tasa Interna de Retorno (TIR) en milisegundos.
* **Conversión de Tasas:** Adaptabilidad dinámica para procesar tasas de interés anuales, semestrales, trimestrales y mensuales.
* **Simulación de Amortización:** Generación de tablas de pago detallando cuota, interés, abono al capital y saldos.

### ⚙️ Optimizador Operativo (Punto de Equilibrio)
* **Simulación Dinámica:** Cálculo del volumen mínimo de ventas requerido para cubrir los costos fijos y variables.
* **Matrices de Escenarios:** Generación de tablas de proyección con múltiples escenarios de venta, identificando claramente las zonas de pérdida y ganancia operativa.
* **Persistencia Profesional:** Motor de exportación integrado que permite guardar las simulaciones en archivos de texto plano **(.csv)** para su análisis externo.

### ⚡ Rendimiento y Experiencia de Usuario (UX)
* **Procesamiento Asíncrono (Multithreading):** Ejecución de los cálculos pesados matemáticos en hilos secundarios (`Thread`) utilizando `SwingUtilities.invokeLater` para actualizar la interfaz, garantizando que el sistema nunca sufra bloqueos (UI Freezing).
* **Diseño Moderno:** Implementación de la librería **FlatLaf** (Look & Feel) para una interfaz profesional, limpia y escalable.
* **Feedback Visual:** Implementación de barras de progreso indeterminadas y renderizado de colores en tiempo real para alertar sobre viabilidad.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java (JDK 8 o superior).
* **GUI:** Java Swing & AWT.
* **Patrones de Diseño:** MVC (Modelo-Vista-Controlador).
* **Librerías Externas:**
    * `flatlaf-3.5.x.jar`: Para el aspecto visual moderno y el renderizado de la interfaz.

## 📂 Estructura del Proyecto
* `MenuPrincipal.java`: Punto de entrada, contenedor `JDesktopPane` y barra de navegación.
* `VentManager.java`: Gestor de instancias de ventanas internas.
* `CalculadoraFinanciera.java` / `MdlSensibilidadLaboral.java`: Clases **Modelo** que encapsulan la lógica matemática y de negocio pura.
* `ControladorEconomico.java` / `CtrlSensibilidadLaboral.java`: Clases **Controlador** que gestionan los eventos `ActionListener` y los hilos de ejecución.
* `V_EvaluacionProyecto.java` / `FrmSensibilidadLaboral.java`: Clases **Vista** que definen la topología de la interfaz gráfica.

## ⚙️ Instalación y Ejecución
1.  Clona el repositorio:
    ```bash
    git clone [https://github.com/tu-usuario/ecosystems-pro.git](https://github.com/tu-usuario/ecosystems-pro.git)
    ```
2.  Importa el proyecto en tu IDE favorito (VS Code, IntelliJ, NetBeans).
3.  Asegúrate de incluir la librería `.jar` (`flatlaf`) en el *Build Path* o en la carpeta `lib/` de las dependencias de tu proyecto.
4.  Ejecuta la clase principal (`Main.java`) para inicializar el tema visual y lanzar el `MenuPrincipal`.

## ✍️ Autor
* **Jeslyng David Maldonado Vivas** - Estudiante de Ingeniería de Sistemas - **Universidad Nacional de Ingeniería (UNI)**.

---
*Este proyecto fue desarrollado aplicando fundamentos de Ingeniería Económica, Programación Estructurada y Patrones de Diseño Arquitectónico basado en los requerimientos de la clase de Programacion II.*
