package edu.escuelaing.arep.apiExterno;

import java.util.Date;
import java.util.HashMap;

public class Data {
    private Date date = new Date();
    private String author = "Cesar Fernando Ortiz Rocha";
    private HashMap<String,String> cache = new HashMap<>();

    public Data() {
        llenando();
    }

    public void llenando(){
        cache.put("Author", author);
        cache.put("Date", date.toString());
    }

    public HashMap<String, String> getCache(){
        //String cachestr = cache.toString();
        //System.out.println("cache:   "+cachestr);
        return cache;
    }
}
