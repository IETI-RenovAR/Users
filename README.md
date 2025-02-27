# RenovAR 🏠

## Servicio de Usuarios (Users Service)
El Servicio de Usuarios es un microservicio desarrollado en Spring Boot que forma parte del proyecto RenovAR. Este servicio se encarga de gestionar la información de los usuarios, incluyendo su registro, autenticación, actualización y eliminación. Además, proporciona endpoints para interactuar con otros servicios relacionados, como el catálogo de productos y los diseños de los usuarios.

## Tecnologías Utilizadas 🧑🏻‍💻
- **Lenguaje:** Java 17  
- **Framework:** Spring Boot 3.4.2  
- **Base de Datos:** MongoDB
- **Maven** 

## Dependencias
- `spring-boot-starter-web`  
- `spring-boot-starter-data-mongodb`  
- `spring-security-crypto`  
- `springdoc-openapi-starter-webmvc-ui`  
- `spring-boot-starter-test` (para pruebas)  

## **Configuración** ⚙️

1. **Clona el repositorio:**
    
    ```
    git clone https://github.com/IETI-RenovAR/Users.git
    ```
    
2. **Configura la base de datos:**
    - Asegúrate de tener MongoDB instalado y en ejecución.
    - Configura la conexión a MongoDB en el archivo `application.properties`.
3. **Instala las dependencias:**
    
    ```
    mvn clean install
    ```
    

---

## **Ejecución** 💻

### **Ejecución Local**

1. **Compila y ejecuta el servicio:**
    
    ```
    mvn spring-boot:run
    ```
    
2. **Accede al servicio:**
    - El servicio estará disponible en `http://localhost:8080`.

### **Ejecución con Docker**

1. **Construye la imagen de Docker:**
    
    ```
    docker build -t users .
    ```
    
2. **Ejecuta el contenedor:**
    
    ```
    docker run -d --name users --network renovar --ip 10.0.0.x
    ```
    

---

## **Endpoints** 🎯

El servicio expone los siguientes endpoints (de manera general):

### **Usuarios**

- **Crear un usuario:**
    
    `POST /v1/users`
    
    ```
    {
        "name": "admin",
        "lastName" : "admin",
        "email" : "admin@admin.com",
        "password" : "admin"
    }
    ```
    
- **Obtener un usuario por ID:**
    
    `GET /v1/users/{id}`
    
- **Actualizar un usuario:**
    
    `PUT /v1/users/{id}`
    
    ```
    {
        "name": "adminSup",
        "lastName" : "admin",
        "email" : "adminSup@admin.com",
        "password" : "adminNew"
    }
    ```
    
- **Eliminar un usuario:**
    
    `DELETE /v1/users/{id}`
    

---

## **Pruebas** ✅

El servicio incluye pruebas unitarias para garantizar su correcto funcionamiento. Para ejecutar las pruebas, usa el siguiente comando:

bash

Copy

```
mvn test
```

### **Casos de Prueba Cubiertos** 🫡

1. Crear un usuario y verificar que se guarde correctamente.
2. Obtener un usuario por ID y validar que la respuesta es correcta.
3. Intentar obtener un usuario inexistente y verificar que se maneje adecuadamente el error (404).
4. Actualizar un usuario existente y verificar que se actualice correctamente.
5. Eliminar un usuario existente y verificar que se elimine correctamente.

---

## Authors

- Ana Maria Duran
- Johan Estrada
- Juan David Contreras
- Laura Natalia Rojas
- Mauricio Monroy
- Samuel Rojas
