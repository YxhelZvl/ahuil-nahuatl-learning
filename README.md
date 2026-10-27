# Ahuil

Aplicación educativa para aprender palabras en náhuatl mediante juegos
interactivos y un catálogo de vocabulario con traducciones al español.

> Este README contiene únicamente información del proyecto. No incluye nombres,
> matrículas, correos personales ni la configuración privada de Firebase.

## Características

- Inicio de sesión con correo y contraseña, Google o modo invitado.
- Juego de **Ahorcado** con teclado de letras y control de intentos.
- Juego de **Memorama** con niveles progresivos de parejas.
- Catálogo de palabras en náhuatl con su significado en español.
- Autenticación y almacenamiento remoto mediante Firebase Authentication y
  Cloud Firestore.
- Interfaz declarativa construida con Jetpack Compose.

## Tecnologías

- Kotlin
- Android SDK (min SDK 24, target SDK 34)
- Jetpack Compose y Material 3
- Navigation Compose
- Firebase Authentication
- Cloud Firestore
- Google Sign-In
- Coil para cargar imágenes desde URL
- Gradle Kotlin DSL

## Estructura principal

```text
Ahuil/
├── app/
│   └── src/main/
│       ├── java/com/yxhelzvl/ahuil/
│       │   ├── core/       # Datos y repositorios
│       │   ├── game/       # Ahorcado y memorama
│       │   ├── home/       # Pantalla principal
│       │   ├── login/      # Inicio de sesión y registro
│       │   ├── navigation/ # Rutas de la aplicación
│       │   └── palabras/   # Catálogo de vocabulario
│       └── res/            # Imágenes, temas y recursos Android
├── build.gradle.kts
└── settings.gradle.kts
```

## Requisitos

- Android Studio actualizado.
- JDK compatible con la versión de Android Gradle Plugin del proyecto.
- Un proyecto propio de Firebase.
- Un dispositivo Android o un emulador con Android 7.0 (API 24) o superior.

## Configuración de Firebase

Por seguridad, `google-services.json` no forma parte del repositorio. Para
ejecutar la aplicación:

1. Crea o selecciona un proyecto en [Firebase Console](https://console.firebase.google.com/).
2. Registra una aplicación Android con el identificador de paquete que aparece
   en `Ahuil/app/build.gradle.kts`.
3. Habilita los proveedores de autenticación que quieras utilizar:
   correo/contraseña, Google y acceso anónimo.
4. Crea una base de datos de Cloud Firestore y configura sus reglas de acceso.
5. Descarga el archivo `google-services.json` y colócalo en:

   ```text
   Ahuil/app/google-services.json
   ```

6. Configura el cliente OAuth de Google y la huella SHA-1 de tu instalación
   local si utilizarás Google Sign-In.

No publiques ese archivo, credenciales de Firebase, contraseñas, archivos
`.keystore` ni datos reales de usuarios. Las reglas de Firestore deben validarse
antes de usar la aplicación en producción.

## Compilar y ejecutar

Desde la carpeta `Ahuil/`:

```bash
./gradlew assembleDebug
```

El APK de depuración se genera en:

```text
Ahuil/app/build/outputs/apk/debug/app-debug.apk
```

Para instalarlo en un dispositivo conectado:

```bash
./gradlew installDebug
```

En Windows se puede utilizar `gradlew.bat` en lugar de `./gradlew`.

## Capturas de la aplicación

Estas capturas muestran la aplicación funcionando. Fueron tomadas del
documento de presentación del proyecto y se seleccionaron para no incluir
datos personales:

### Inicio de sesión

![Pantalla de inicio de sesión](docs/screenshots/login.png)

### Pantalla principal

![Pantalla principal con los juegos disponibles](docs/screenshots/home.png)

### Juego Ahorcado

![Juego Ahorcado en ejecución](docs/screenshots/ahorcado-en-juego.png)

### Juego Memorama

![Memorama con parejas encontradas](docs/screenshots/memorama-completado.png)

## Estado del proyecto

La versión actual es un prototipo funcional académico. El catálogo y las
palabras utilizadas por los juegos dependen de la configuración de Firestore.
La opción para agregar palabras y los permisos asociados deben revisarse y
validarse antes de considerar la aplicación lista para producción.

## Posibles mejoras

- Agregar pruebas automatizadas para los ViewModel y las reglas de los juegos.
- Validar y proteger con roles las operaciones de escritura en Firestore.
- Separar la configuración por entornos (desarrollo y producción).
- Mejorar accesibilidad, navegación y persistencia de puntuaciones.
- Completar la funcionalidad de administración de palabras.

## Licencia

No se ha definido una licencia para este proyecto. Antes de publicar el
repositorio, el propietario debe elegir una licencia compatible con las
dependencias y los recursos incluidos.
