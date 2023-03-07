package com.atguigu.spark.sparkcore.value;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.List;

public class Test08_sortBy {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<Integer> list = Arrays.asList(3, 23, 5, 2, 3, 4, 6, 7, 8, 23, 4, 233444);
        JavaRDD<Integer> javaRDD = jsc.parallelize(list, 2);
        JavaRDD<Integer> sortBy = javaRDD.sortBy(new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) throws Exception {
                return integer;
            }
        }, true,1);
        sortBy.collect().forEach(System.out::println);

        //4.关闭资源
        jsc.stop();
    }
}
