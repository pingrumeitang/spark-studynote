package com.atguigu.spark.sparkcore.serialize;

import com.atguigu.spark.sparkcore.bean.User;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test01_Ser {
    public static void main(String[] args) throws SQLException {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //driver

        //3.编写代码
        JavaRDD<String> lineRdd = jsc.textFile("input/user.txt");
        JavaRDD<User> map = lineRdd.map(new Function<String, User>() {
            @Override
            public User call(String line) throws Exception {
                Connection connection = DriverManager.getConnection("jdbc:mysql://hadoop102:3306/gmall?useSSL=false", "root", "000000");
                System.out.println(connection);
                String[] split = line.split(" ");
                return new User(Integer.valueOf(split[0]),
                        split[1],
                        Integer.valueOf(split[2]));
            }
        });
        map.collect().forEach(System.out::println);

        //4.关闭资源
        jsc.stop();
    }
}
