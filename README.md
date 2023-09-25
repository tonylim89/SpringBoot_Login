# Login Application

## Overview:
The application provides a login, registration system with user role management, and supports internationalization.

## Table of Contents:

- [Backend Logic or Server-side Components](#backend-logic-or-server-side-components)
  - [UserController](#usercontroller)
  - [HomeController](#homecontroller)
  - [SecurityConfiguration](#securityconfiguration)
  - [InternationalizationConfig](#internationalizationconfig)
  - [UserService](#userservice)
  - [UserServiceImpl](#userserviceimpl)
  - [LoginappApplication](#loginappapplication)
- [Static Files](#static-files)
  - [fragments.html](#fragmentshtml)
  - [login.html](#loginhtml)
  - [register.html](#registerhtml)
  - [registermanager.html](#registermanagerhtml)
  - [restricted.html](#restrictedhtml)
  - [welcome.html](#welcomehtml)
- [Other Implementations](#other-implementations)

## Backend Logic or Server-side Components

### UserController
This controller deals with the login, registration, and main welcome page logic.

#### Key Methods:
- **login()**: Renders the login page.
- **register()**:
  - Resolves the current locale for internationalization purposes.
  - Adds necessary attributes for user registration, including localization messages for error handling.
  - Renders the registration page.
- **postRegister()**:
  - Handles the POST request from the registration form.
  - Validates input fields.
  - Checks username's format.
  - Saves a new user or handles exceptions, e.g., when a username already exists.
- **registerManager()**: Registers a manager. Checks if a manager already exists. If so, redirects to the login page.
- **postRegisterManager()**: Handles the POST request for registering a manager.
- **welcome()**: Renders the welcome page, showing details of the authenticated user.


### HomeController
This controller handles the homepage redirection, restricted page, and locale changes.

#### Key Methods:
- **home()**: Redirects to the login page.
- **restricted()**: Renders a restricted page, presumably for managers or specific roles.
- **setLocale()**: Handles the change of language based on the user's preference.

### SecurityConfiguration
Sets up the security configuration for the application.

#### Key Components:
- **passwordEncoder()**: Provides an instance of BCryptPasswordEncoder for password hashing.
- **securityFilterChain()**:
  - Defines URL patterns and their access permissions.
  - Sets up the form login details.
  - Configures the logout behavior.

### InternationalizationConfig
Configures internationalization features.

#### Key Components:
- **localeResolver()**: Determines the current locale.
- **localeChangeInterceptor()**: Intercepts requests to change the locale.
- **messageSource()**: Sets up message sources for internationalization.
- **addInterceptors()**: Registers interceptors, like the locale change interceptor.

### UserService
This service layer provides functionality related to the User entity.

#### Key Methods:
- **findByUsername()**: Fetches a user based on their username.
- **checkIfManagerExists()**: Checks if a manager exists in the system.
- **save()**:
  - Validates whether the username is unique.
  - Encodes the password and saves the user to the database.

### UserServiceImpl
Implements Spring's UserDetailsService for loading user-specific data for security.

#### Key Method:
- **loadUserByUsername()**:
  - Fetches a User entity based on a username.
  - Converts it into Spring's UserDetails for authentication and authorization purposes.

### LoginappApplication
The main Spring Boot application class that kick-starts the application.

#### Key Method:
- **main()**: The entry point to run the Spring Boot application.

## Static Files

### fragments.html
Contains reusable HTML fragments for the application.

### login.html
Presents the user with a form to enter their login credentials.

### register.html
Allows a user to register for an account.

### registermanager.html
For registering a manager.

### restricted.html
Displayed when users try to access a restricted area.

### welcome.html
Welcomes authenticated users and displays their details.

## Other Implementations

### Internationalization
The application supports Chinese and English languages. This can be performed using MessageSource, LocaleResolver, and the /lang endpoint to switch languages.

### User Roles
The code provides a way to distinguish between regular users and managers.

### Validation
Before registering a user, there are validation checks to ensure the fields are not empty and the username adheres to a specified format.

### Exception Handling
In case a username already exists, a `UsernameAlreadyExistsException` is thrown and handled, informing the user about the issue.

### Security
Passwords are hashed using BCrypt, and Spring Security is used to handle authentication, authorization, login, and logout functionalities.
