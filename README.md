# API Rest CRUD Users

API para la gestión de usuarios.

## Installation

Usar Maven para instalar dependencias y compilar el proyecto. El formato compilado es en formato .jar

```bash
mvn clean install
```

## Usage

Para ejecutar el proyecto, usar el siguiente comando. Por defecto se usa el puerto 13531

```bash
java -jar filename.jar
```

### Settings
Para cambiar el puerto por defecto, agregar el flag *-Dserver.port*

```bash
java -jar -Dserver.port=12345 filename.jar 
```

Para cambiar la expresión regular por defecto de validación del password, agregar el flag *-Dpassword.regex*

```bash
java -jar -Dpassword.regex='^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$' filename.jar 
```

## Docs

Puede descargar la colección de postman desde [acá](https://api.postman.com/collections/24324434-4f95ebed-ad09-41ac-8b4c-ab9994b72c6f?access_key=PMAT-01HQ8MTC3JRNQMBHRP8JPFMEVH)

