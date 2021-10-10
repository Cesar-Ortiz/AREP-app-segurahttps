package edu.escuelaing.arep.securityprimerlive;

import spark.Request;
import spark.Response;

import static spark.Spark.*;
public class HelloService {

    public static void main(String[] args) {
        secure("keystores/ecikeystore.p12", "123456", null, null);
        port(getPort());

        //sc.validate("hola","98765");
        get("/hello", (req, res) -> "Hello World");
        get("/login", (req, res) -> login(req, res));
    }

    public static String login(Request req, Response res){
        //res.type("application/json");
        SecurityContext sc = new SecurityContext();
        return sc.validate(req.queryParams("usuario"),req.queryParams("password"));
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
