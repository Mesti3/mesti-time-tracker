package com;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;

public class UserPattern {

    private static final Logger LOG = Logger.getLogger(TimePattern.class.getName());

    public static  String email;
    private static String token = "NO TOKEN";
    private static List<Error> tokens;

    ServerIntegration server;
   // private final EnumSet<UserPattern> units;

    public UserPattern(String ptoken, List<Error> ptokens) {
        server = new ServerIntegration();
        token = ptoken;
        tokens = ptokens;
    }

    public  String  getToken(){
        System.out.println("GetToken: " + token);
        return  token;
    }

    public void  setToken(String ptoken){

        token = ptoken;
    }

    public String getEmail(){
        return  email;
    }

    public void setEmail(String pemail){
        email = pemail;
    }

    public String userAuth(String pemail, int time,String name){
        return  this.getEmail() != null ? server.sendPost(pemail,time,name) : "No token";
    }

    public String userValid(String ptoken){
        return  this.getEmail() == null ? server.sendGet(ptoken) : "";
    }

    public static UserPattern parse(CharSequence pattern) {
        final ArrayList<Error> errors = new ArrayList<>();
        final UserPattern result = parse(pattern, errors);

        if (!errors.isEmpty()) {
            LOG.warning("Pattern '" + pattern + "' had " + errors.size() + " ignored error(s)");
        }
        return result;
    }

    public static UserPattern parse(CharSequence pattern, List<Error> parseErrors) {

        return new UserPattern(pattern.toString(), parseErrors);
    }
}
