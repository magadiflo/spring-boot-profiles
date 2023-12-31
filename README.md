# Perfiles en Spring Boot

Fuente: [javatechonline](https://javatechonline.com/profiles-in-spring-boot/?fbclid=IwAR1Etc8VN0AAfmvPSD4dau-OzzP6HVnu5cO4hhUjBZvwnW2B5YQ4O9o6_hU)

---

## ¿Qué es el perfil predeterminado?

Spring Boot viene con un archivo de propiedades, llamado archivo `application.properties` de forma predeterminada
**(default)**. Por lo tanto, `este archivo es el perfil predeterminado, es el perfil por default`. Del mismo modo,
el archivo `application.yml` también será el perfil predeterminado. `El perfil predeterminado siempre está activo`.

`Spring Boot carga primero todas las propiedades del perfil predeterminado (default)`.

Si una propiedad se define en el perfil predeterminado, pero no en el perfil prod, el valor de la propiedad se rellenará
a partir del perfil predeterminado. Esto es muy útil para definir valores predeterminados que son válidos en todos los
perfiles. Por lo tanto, **debemos mantener todas las propiedades en el perfil predeterminado**
(`application.properties`) que son **comunes en todos los perfiles**.

A continuación se muestran algunas propiedades iniciales agregadas en el **perfil por default**:

**Perfil por default:** `application.properties`

````properties
spring.application.name=Perfiles en Spring
server.port=8081
app.info=Este es el archivo de propiedades por default
app.parametro=${APP_PARAMETER}
````

Si ejecutamos, tan solo teniendo el perfil por defecto `application.properties` en la aplicación, es decir no tenemos
ningún otro archivo de perfil adicional, veremos lo siguiente:

![perfil default](./assets/perfil-default.png)

El resultado muestra que al **no tener más perfiles en la aplicación** y además al no haber establecido uno en
particular, **el perfil por default es el que se usa**.

## ¿Cómo crear un perfil en Spring?

En lugar de estar cambiando constantemente las entradas en el `perfil predeterminado (por default)`, **crearemos un
archivo dedicado independiente para cada entorno**. Además, podemos **activar el perfil requerido** cambiando solo una
entrada en **el perfil por default**.

Supongamos que tenemos tres entornos, tales como: `development`, `production` y `test`, así que, crearemos tres archivos
de propiedades más en la misma ubicación donde reside el archivo `application.properties`, al final tendremos la
siguiente estructura:

````
src/main/resources/
    application.properties          <-- default
    application-dev.properties      <-- development
    application-prod.properties     <-- production
    application-test.properties     <-- test
````

Debemos **tener en cuenta la convención de la nomenclatura**. A continuación se muestra para los dos tipos de archivos
`.properties` y `.yml`:

> `application-{entorno}.properties`
>
> `application-{entorno}.yml`

**DONDE**

El `{entorno}` puede tomar cualquier nombre, en mi caso nombré a los entornos: `dev`, `prod` y `test`.

Ahora, insertemos algunas propiedades de configuración en estos archivos. Por ejemplo, agregaremos configuraciones
de bases de datos que son diferentes en cada archivo `(estas configuraciones serán a modo de prueba, no son
configuraciones propias de las bases de datos)`. Supongamos que la BD h2 es para desarrollo, MySQL para prueba y
Oracle DB para producción:

**Perfil development:** `application-dev.properties`

````properties
server.port=8082
app.info=Este es el archivo de propiedades del entorno dev
base.datos.h2.console.enabled=true
base.datos.h2.console.path=/h2
base.datos.fuente.driver-class-name=org.h2.Driver
base.datos.fuente.url=jdbc:h2:mem:db
base.datos.fuente.usuario=sa
base.datos.fuente.password=sa
APP_PARAMETER=Enviado desde Development
````

**Perfil test:** `application-test.properties`

````properties
server.port=8083
app.info=Este es el archivo de propiedades del entorno test
base.datos.fuente.url=jdbc:mysql://localhost:3306/myTestDB
base.datos.fuente.usuario=root
base.datos.fuente.password=123
base.datos.fuente.driver-class-name=com.mysql.cj.jdbc.Driver
base.datos.jpa.hibernate.ddl-auto=update
base.datos.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
APP_PARAMETER=Enviado desde Test
````

**Perfil production:** `application-prod.properties`

````properties
server.port=8084
app.message=Este es el archivo de propiedades del entorno prod
base.datos.fuente.url=jdbc:oracle:thin:@localhost:1521:xe
base.datos.fuente.usuario=username
base.datos.fuente.password=password
base.datos.jpa.hibernate.ddl-auto=update
base.datos.jpa.show-sql=true
base.datos.fuente.driver-class-name=oracle.jdbc.OracleDriver
base.datos.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect
APP_PARAMETER=Enviado desde Production
````

Si no implementamos perfiles, tendremos que acomodar todas las entradas anteriores en un solo archivo
`application.properties`. Además, si queremos cambiar a un perfil diferente, tendremos que hacer muchos cambios cada
vez. Por lo tanto, mantener un perfil separado para cada entorno ahorra mucho esfuerzo.

## ¿Cómo activar un perfil en particular?

Aunque, tenemos múltiples formas de hacer que un perfil en particular sea activo. Discutamos algunos de ellos uno por
uno.

### Enfoque n° 1: Configurando `spring.profiles.active` en `application.properties`

El archivo `application.properties` **será el jefe entre todos los archivos de propiedades**. Aquí especificaremos qué
perfil está activo estableciendo el valor de la propiedad `spring.profiles.active`. Por ejemplo, a continuación el
archivo `application.properties` nos indica que actualmente el perfil "development (dev)" está activo.

**Perfil por default:** `application.properties`

````properties
spring.application.name=Perfiles en Spring
server.port=8081
spring.profiles.active=dev
app.info=Este es el archivo de propiedades por default
app.parametro=${APP_PARAMETER}
````

En el código anterior, el valor de la propiedad `spring.profiles.active` **indica a Spring qué perfil usar**.
Aquí hemos establecido el perfil de desarrollo como activo. **Este es el enfoque más comúnmente utilizado para hacer
que un perfil particular sea activo.**

**¡Importante!**, el valor de la configuración `spring.profiles.active` tiene que ser el nombre del `{entorno}` que le
dimos al archivo de propiedades.

Si ejecutamos la aplicación, ahora veremos que Spring selecciona el perfil que fue configurado `dev` con su puerto
`8082`:

![perfil dev](./assets/perfil-dev.png)

### Enfoque n° 2: Configurando el parámetro del sistema JVM

También podemos pasar el nombre del perfil en un parámetro del sistema JVM como se muestra a continuación. **El perfil
especificado se activará durante el inicio de la aplicación.**

En nuestro caso, para ejemplificar este enfoque nos posicionaremos mediante una terminal en la raíz de nuestra
aplicación, y ejecutaremos nuestro proyecto con el siguiente comando (note que estamos especificando el perfil que
queremos que se active, en este caso es el `test`:

````bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=test
````

![perfil test](./assets/perfil-test.png)

Existen otras formas de configurar el perfil activo, para más información ir a la fuente
[javatechonline](https://javatechonline.com/profiles-in-spring-boot/#How_to_Activate_a_Particular_Profile).

## Prioridad en el uso de múltiples enfoques

Supongamos que hemos utilizado varios enfoques en el mismo proyecto para establecer un perfil activo, entonces Spring
priorizará los enfoques en el siguiente orden:

1. Configurando un parámetro de contexto en web.xml
2. Implementando la interfaz WebApplicationInitializer
3. Configurando el parámetro del sistema JVM
4. Configurando perfiles activos en el pom.xml

## Ejecutando perfil

A modo de ejemplo **ejecutaremos el perfil de producción** para ver qué valores están tomando las propiedades:

- Primero debemos configurar en el `application.properties` el perfil activo en `prod`:
  > spring.profiles.active=prod
- Ejecutamos la aplicación:

  ![perfil-prod](./assets/perfil-prod.png)

- Creamos un controlador que nos está retornando información de las propiedades del perfil:

    ````bash
    curl -v http://localhost:8084/api/v1/products | jq
    
    >
    < HTTP/1.1 200
    {
      "port": 8084,
      "parameter": "Enviado desde Production",
      "url": "jdbc:oracle:thin:@localhost:1521:xe",
      "info": "Este es el archivo de propiedades por default"
    }
    ````

Como observamos, el perfil `prod` se ha seleccionado correctamente, incluso vemos que no tenemos la propiedad
`app.info` en este perfil, pero sí lo tenemos en el perfil por default `application.properties`, por lo tanto, el valor
lo tomará de este perfil por default.

## ¿Cómo comprobar qué perfil está activo actualmente?

Hay dos formas de hacerlo:

### Enfoque n° 1: Uso del objeto environment

Podemos usar el objeto `Environment` de Java como un `bean` a través del `@Autowired` o usando `inyección de dependencía
vía constructor`, para obtener el perfil activo como se muestra a continuación:

````java

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    /* other code */
    private final Environment environment;

    public ProductController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public Map<String, Object> showMessage() {
        Map<String, Object> response = new HashMap<>();
        response.put("port", port);
        response.put("info", info);
        response.put("parameter", parameter);
        response.put("url", url);
        response.put("profiles", this.getProfiles());

        return response;
    }

    private String getProfiles() {
        return String.join(", ", environment.getActiveProfiles());
    }
}
````

![get-profiles-actives](./assets/get-profiles-actives.png)

````bash
curl -v http://localhost:8084/api/v1/products | jq

>
< HTTP/1.1 200
{
  "port": 8084,
  "parameter": "Enviado desde Production",
  "profiles": "prod",
  "url": "jdbc:oracle:thin:@localhost:1521:xe",
  "info": "Este es el archivo de propiedades por default"
}
````

### Enfoque n° 2: Inyectando la propiedad 'spring.profiles.active'

Similar al siguiente código:

````java

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    /* other code */

    @Value("${spring.profiles.active}")
    private String activeProfiles;

    @GetMapping
    public Map<String, Object> showMessage() {
        Map<String, Object> response = new HashMap<>();
        response.put("port", port);
        response.put("info", info);
        response.put("parameter", parameter);
        response.put("url", url);
        response.put("profiles-environment", this.getProfiles());
        response.put("profiles-properties", activeProfiles);

        return response;
    }
}
````

````bash
curl -v http://localhost:8084/api/v1/products | jq

>
< HTTP/1.1 200
<
{
  "profiles-properties": "prod",
  "port": 8084,
  "parameter": "Enviado desde Production",
  "profiles-environment": "prod",
  "url": "jdbc:oracle:thin:@localhost:1521:xe",
  "info": "Este es el archivo de propiedades por default"
}
````
