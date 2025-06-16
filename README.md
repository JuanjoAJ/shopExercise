# 🛒 Java Store App with JavaFX and MySQL

This is a store management application developed in Java and JavaFX, featuring a dynamic product catalog fetched from an external API. The app connects to a MySQL database, handles user authentication with encrypted passwords, manages stock in real time, and sends real confirmation emails after completing purchases.

## 💡 Features

- 📦 Products loaded dynamically from an external API
- 🧮 Real-time stock management
- 📧 Email confirmation after purchase (real email sending)
- 🔐 Encrypted password storage in MySQL
- 👤 User login and authentication
- 🎨 JavaFX graphical interface

## 🛠️ Technologies Used

- JavaFX
- MySQL
- JDBC
- JavaMail (for sending emails)
- BCrypt (for password encryption)
- External Product API (JSON)

## ⚙️ Setup Instructions

1. Clone this repository.
2. Set up a local MySQL database and import the provided SQL script.
3. Configure your database connection in the app (`DBConnection.java`).
4. Add your email credentials to the email sender service.
5. Run the application using your IDE or `javac/java`.
# 🛒 Aplicación de Tienda en Java con JavaFX y MySQL

Esta es una aplicación de gestión de tienda desarrollada en Java y JavaFX, con un catálogo de productos cargado dinámicamente desde una API externa. La app se conecta a una base de datos MySQL, maneja la autenticación de usuarios con contraseñas cifradas, gestiona el stock en tiempo real y envía correos electrónicos reales tras completar una compra.

## 💡 Funcionalidades

- 📦 Productos cargados desde una API externa
- 🧮 Gestión dinámica del stock
- 📧 Confirmación por correo tras realizar una compra
- 🔐 Contraseñas cifradas almacenadas en MySQL
- 👤 Inicio de sesión y autenticación de usuarios
- 🎨 Interfaz gráfica con JavaFX

## 🛠️ Tecnologías Utilizadas

- JavaFX  
- MySQL  
- JDBC  
- JavaMail (para envío de correos)  
- BCrypt (para cifrado de contraseñas)  
- API externa de productos (formato JSON)

## ⚙️ Instrucciones de Instalación

1. Clona este repositorio.
2. Crea una base de datos en MySQL e importa el script SQL proporcionado.
3. Configura la conexión en el archivo `DBConnection.java`.
4. Añade tus credenciales de correo en el servicio de envío de emails.
5. Ejecuta la aplicación desde tu IDE o con `javac/java`.
