package edu.escuelaing.arep.securityprimerlive;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityContext {

    private String uniquepasword = "98765";
    private String useroficial = "cesarortiz";
    private String passhash = "";

    public SecurityContext() {
        initSegurity();
    }

    public void initSegurity(){
        passhash = hash(uniquepasword);
        System.out.println("password  :"+passhash);
    }

    public String hash(String password) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            String salt = "some_random_salt";
            String passWithSalt = password + salt;
            byte[] passBytes = passWithSalt.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< passHash.length ;i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            //System.out.println("password  :"+generatedPassword);
            return generatedPassword;
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return null;
    }

    public String validate(String usr, String password){
        String result="";
        String passnew = hash(password);
        if(passhash.equals(passnew) && useroficial.equals(usr)){
            urlReader();
            result = readURL("https://ec2-52-70-96-83.compute-1.amazonaws.com:5000/getData");
        }
        else{
            result="acceso denegado";
        }
        return result;
    }

    public void urlReader(){
        try {

            // Create a file and a password representation
            File trustStoreFile = new File("keystores/myTrustStore");
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            //Print the trustManagers returned by the TMF
            //only for debugging
            for(TrustManager t: tmf.getTrustManagers()){
                System.out.println(t);
            }

            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

            // We can now read this URL
            //readURL("https://localhost:5000/getData");

            // This one can't be read because the Java default truststore has been
            // changed.
            //readURL("https://www.google.com");

        } catch (KeyStoreException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String readURL(String sitetoread) {
        String inputLine = null;
        try {
            // Crea el objeto que representa una URL2
            URL siteURL = new URL(sitetoread);
            // Crea el objeto que URLConnection
            URLConnection urlConnection = siteURL.openConnection();
            // Obtiene los campos del encabezado y los almacena en un estructura Map
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            // Obtiene una vista del mapa como conjunto de pares <K,V>
            // para poder navegarlo
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
            // Recorre la lista de campos e imprime los valores
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String headerName = entry.getKey();

                //Si el nombre es nulo, significa que es la linea de estado
                if (headerName != null) {
                    System.out.print(headerName + ":");
                }
                List<String> headerValues = entry.getValue();
                for (String value : headerValues) {
                    System.out.print(value);
                }
                System.out.println("");
            }

            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String input=null;
            while ((input = reader.readLine()) != null) {
                inputLine=input;
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return inputLine;
    }
}
