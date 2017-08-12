package com.wanlianjin.cic.test;

import com.wanlianjin.cic.mongo.model.UserModel;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by ychen on 2017/1/18.
 */
public class TestJackson {
    public static void main(String[] args) {
        String json = "{ \"_id\" : \"587338cacb47955ce8da1e71\", \"userName\" : \"test\", \"password\" : \"123\" }";

        ObjectMapper mapper = new ObjectMapper();
        UserModel um = null;
        try {
            um = mapper.readValue(json, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != um) {
            System.out.println(11111111);
        } else {
            System.out.println(22222222);
        }
    }
}
