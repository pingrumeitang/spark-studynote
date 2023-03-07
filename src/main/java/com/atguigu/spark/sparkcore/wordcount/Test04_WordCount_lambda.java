package com.atguigu.spark.sparkcore.wordcount;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Test04_WordCount_lambda {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        //3.编写代码
        jsc
                .textFile("input/1.txt")
                .flatMapToPair(line->{
                    String[] split = line.split(" ");
                    ArrayList<Tuple2<String, Integer>> result = new ArrayList<>();
                    for (String word : split) {
                        if (!"".equals(word)&&word!=null)
                        result.add(new Tuple2<>(word,1));
                    }
                    return result.iterator();
                })
                .reduceByKey((sum,elem)->sum+elem)
                .collect()
                .forEach(System.out::println);
        //4.关闭资源
        jsc.stop();
    }
}
