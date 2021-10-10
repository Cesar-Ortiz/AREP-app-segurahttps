package edu.escuelaing.arep.apiExterno;

import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.util.HashMap;

import static spark.Spark.*;

public class App 
{
    public static void main( String[] args ) {
        secure("keystores/ecikeystore.p12", "123456", null, null);
        port(getPort());
        get("/hello", (req, res) -> "ma nigga");
        get("/getData", (req, res) -> getData(req, res));
    }

    public static String getData(Request req, Response res){
        res.type("application/json");
        Data data = new Data();
        HashMap<String,String> cache=data.getCache();
        Gson gson=new Gson();
        String JSON= gson.toJson(cache);
        return JSON;
    }

    public static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
