# Escuela Colombiana de Ingeniería Julio Garavito
## Aplicación segura
## 👤 Autor
César Fernando Ortiz Rocha
## Descrioción general

En este laboratorio se realizó un prototipo de aplicación segura haciendo uso del protocolo https, dicha aplicación cuenta con dos servicios, uno de los servicios es el de login el cual se va a encargar de recibir un usuario y una contraseña para autenticarse, una vez autenticado se diriguirá al otro servicio el cual tiene los datos almacenados, traera esos datos y los mostrará.

### Servicio login

En este serviocio cuento con dos endpoints un **(get)/hello** el cual cumple con la función de verificar que el servicio funciona y un **(get)/login** el cual se encarga de recibir el usuario y contraseña por el path.

Tambien se puede ver el metodo **login** el cual se encarga de sacar los parametros puestos en el path haciendo uso de request.queryParams() y enviar estos para que sean verificados.
![](img/loginmain.PNG)

En el contructor de SecurityContext se realiza la implementación del hash de la contraseña ya registrada

![](img/hash.PNG)

Aqui se puede apreciar el metodo en el que se validan los datos que se registraron a la hora de hacer el logeo, si los datos concuerdan entonces se procedera a realzar la confianza del documento con la ip del servicio que trae los datos, de lo contrareo saldra acceso denegado.

![](img/validate1.PNG)

### Servicio login

![](img/datamain.PNG)


![](img/dataClass.PNG)

## Desarrollo de los servicios

## Diagrama de despliegue 

