package com.atguigu.spark.sparkcore.action;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test01_action {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<Integer> list = Arrays.asList(1, 3, 56, 6, 4, 4);
        JavaRDD<Integer> javaRDD = jsc.parallelize(list,2);
        System.out.println(javaRDD.count());
        System.out.println(javaRDD.first());
        List<Integer> take = javaRDD.take(4);
        System.out.println(take);
        ArrayList<Tuple2<String,Integer>> list1 = new ArrayList<>();
        list1.add(new Tuple2("A",1));
        list1.add(new Tuple2("B",1));
        list1.add(new Tuple2("C",1));
        list1.add(new Tuple2("D",1));
        JavaPairRDD<String, Integer> pairRDD = jsc.parallelizePairs(list1,2);
        Map<String, Long> map = pairRDD.countByKey();
        System.out.println(map);
        //javaRDD.saveAsTextFile("output");
        pairRDD.saveAsObjectFile("output");
        //4.关闭资源
        jsc.stop();
    }

}
