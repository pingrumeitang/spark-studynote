package com.atguigu.spark.sparkcore.created;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Test04_file_partition {
    public static void main(String[] args) {
         //1.创建Spark Conf
         SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
         //2.创建SparkContext
         JavaSparkContext jsc = new JavaSparkContext(sparkConf);
         //3.编写代码
        JavaRDD<String> lineRDD = jsc.textFile("input/1.txt",2);
         lineRDD.saveAsTextFile("output");


        //4.关闭资源
         jsc.stop();
    }
}
