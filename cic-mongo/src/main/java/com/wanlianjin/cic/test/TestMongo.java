package com.wanlianjin.cic.test;

import com.wanlianjin.cic.mongo.model.UserModel;
import com.wanlianjin.cic.mongo.util.MongoTemplate;
import com.wanlianjin.cic.mongo.util.Query;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

/**
 * Created by ychen on 2017/1/18.
 */
public class TestMongo {
    public static void main(String[] args) {

        String[] configFiles = { "classpath:applicationContext1.xml",
                "classpath:app-mongo1.xml"
        };
        ConfigurableApplicationContext appContext;
        appContext = new FileSystemXmlApplicationContext(configFiles);

        MongoTemplate mongoTemplate = (MongoTemplate) appContext.getBean("mongoTemplate");

            testFind(mongoTemplate);
    }

    public static void testFind(MongoTemplate mongoTemplate){
        Query query = new Query("userName").eq("test").and("password").eq("123");
        try {
            List<UserModel> users = mongoTemplate.find("userModel", query, UserModel.class);
            for(UserModel user:users){
                System.out.println(user.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
