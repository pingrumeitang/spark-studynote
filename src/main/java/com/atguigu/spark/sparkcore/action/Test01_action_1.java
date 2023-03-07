package com.atguigu.spark.sparkcore.action;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test01_action_1 {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        ArrayList<Tuple2<String,Integer>> list = new ArrayList<>();
        list.add(new Tuple2("A",1));
        list.add(new Tuple2("B",1));
        list.add(new Tuple2("C",1));
        list.add(new Tuple2("D",1));
        JavaPairRDD<String, Integer> pairRDD = jsc.parallelizePairs(list,2);
        pairRDD.saveAsObjectFile("output");
        List<Integer> list1 = Arrays.asList(1, 3, 54, 5, 6, 4, 3);
        JavaRDD<Integer> javaRDD = jsc.parallelize(list1);
        javaRDD.saveAsObjectFile("output1");
        javaRDD.saveAsTextFile("output2");
        //4.关闭资源
        jsc.stop();
    }
}
