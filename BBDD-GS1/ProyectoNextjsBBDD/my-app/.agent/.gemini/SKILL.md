---
name: gemini
description: Skill de programación que establece reglas y convenciones para optimizar el desarrollo de este proyecto en Next.js.
---

# Next.js Pro Developer Guidelines

## Propósito
Esta skill establece las reglas de programación para que la IA escriba código de mayor calidad, mantenible y optimizado para Next.js (App Router). Al tener estas reglas, la IA funcionará mejor y tomará decisiones arquitectónicas más sólidas.

## Reglas de Programación e Instrucciones
1. **Server Components por Defecto:** Usa Server Components como norma general. Añade la directiva `"use client";` única y exclusivamente cuando el componente requiera interactividad (e.g. `useState`, `useEffect`, `onClick`) o dependencias exclusivas del navegador.
2. **Data Fetching Optimizado:** Realiza las llamadas a APIs o bases de datos desde el lado del servidor siempre que sea posible, aprovechando las capacidades nativas de Next.js para mejorar el rendimiento (SSR) y el SEO.
3. **Manejo de Errores y Estados:** Implementa bloques `try/catch` robustos en todas las funciones asíncronas. Informa siempre al usuario cuando una operación está cargando o falla.
4. **Tipado con TypeScript:** Prohibido usar `any` por defecto. Define siempre `interfaces` o `types` precisos para los datos de la aplicación y las respuestas de la API.
5. **Separación de Responsabilidades:** Extrae lógica de negocio compleja a funciones separadas (por ejemplo, en un directorio `/lib` o `/utils`) y componentes visuales reutilizables a un directorio `/components`.
6. **Estilos y Rendimiento:** Minimiza el uso excesivo de estilos en línea (`style={{...}}`) a favor de clases de utilidad o CSS global, reservando los estilos en línea solo para valores dinámicos generados en tiempo real.
