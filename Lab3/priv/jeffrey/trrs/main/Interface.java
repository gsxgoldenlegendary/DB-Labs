package priv.jeffrey.trrs.main;

import priv.jeffrey.trrs.backend.MySQLDemo;


public class Interface {
    public static String query(String id){
        return MySQLDemo.query(id);
    }
}
