package com.atguigu.spark.sparkcore.created;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Test03_list_partition {
    public static void main(String[] args) throws InterruptedException {

        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[*]");

        JavaSparkContext jsc  = new JavaSparkContext(sparkConf);

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        JavaRDD<Integer> javaRDD = jsc.parallelize(list,5);

        javaRDD.saveAsTextFile("output");



        //jsc.stop();
    }
}
