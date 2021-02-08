package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.net.ssl.HttpsURLConnection;


public class ServerIntegration {


    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        System.out.println("Server integration run");

    }

    // HTTP GET request
      String sendGet(String param)  {
        try {

            System.out.println(param);

            if(!param.equals("")) {
                                            /// where is the title
                String url = "https://localhost:8000/api/timeTracker" + "/" + param +  "/" + "Test";

                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response);

                return response.substring(1,response.length()-1);
            }
        }catch (Exception e){
            System.out.format("%s",e);
        }

        return "No token";
    }

    // HTTP POST request
     String sendPost(String param,int time,String name)  {
    try{

        System.out.println(param);

        if(!param.equals("@")) {

            String url = "https://localhost:8000/api/timeTracker" + "/" + param;

            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            String urlParameters = "time" ;

            // Send post request
            con.setDoOutput(true);
            Map<String,String> arguments = new HashMap<>();
            arguments.put("title", name);
            arguments.put("time", Integer.toString(time)); // This is a fake password obviously
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.connect();
            try(OutputStream os = con.getOutputStream()) {
                os.write(out);
            }

            /*
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            */

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            //System.out.println(response.substring(1,response.length()-1));

            if(response.substring(1,response.length()-1).equals("User does not exist")) {
                JOptionPane.showMessageDialog(new JFrame(), response.substring(1, response.length() - 1));
            }

            return response.substring(1,response.length()-1);

        }

}catch (Exception e){
    System.out.format("%s",e);
}
    return "No token";
    }
}


