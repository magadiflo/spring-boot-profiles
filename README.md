# Perfiles en Spring Boot

Fuente: [javatechonline](https://javatechonline.com/profiles-in-spring-boot/?fbclid=IwAR1Etc8VN0AAfmvPSD4dau-OzzP6HVnu5cO4hhUjBZvwnW2B5YQ4O9o6_hU)

---

## ¿Qué es el perfil predeterminado?

Spring Boot viene con un archivo de propiedades, llamado archivo `application.properties` de forma predeterminada
(default). Por lo tanto, `este archivo es el perfil predeterminado, es el perfil por default`. Del mismo modo,
el archivo `application.yml` también será el perfil predeterminado. `El perfil predeterminado siempre está activo`.

`Spring Boot carga primero todas las propiedades del perfil predeterminado`.

Si una propiedad se define en el perfil predeterminado, pero no en el perfil prod, el valor de la propiedad se rellenará
a partir del perfil predeterminado. Esto es muy útil para definir valores predeterminados que son válidos en todos los
perfiles. Por lo tanto, **debemos mantener todas las propiedades en el perfil predeterminado
** (`application.properties`) que son **comunes en todos los perfiles**.

**Perfil por default:** `application.properties`

````properties
server.port=8080
````
