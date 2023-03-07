package com.atguigu.spark.sparkcore.action;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Test03_foreach_partiton {
    public static void main(String[] args) {
        //1.创建Spark Conf
        SparkConf sparkConf = new SparkConf().setAppName("SparkCore").setMaster("local[2]");
        //2.创建SparkContext
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        //3.编写代码
        List<Integer> list = Arrays.asList(2, 3, 5, 6, 76, 4);
        JavaRDD<Integer> javaRDD = jsc.parallelize(list,2);
        //多线程
        javaRDD.foreachPartition(new VoidFunction<Iterator<Integer>>() {
            @Override
            public void call(Iterator<Integer> integerIterator) throws Exception {
                while (integerIterator.hasNext()){
                    System.out.println(integerIterator.next());
                }
            }
        });
        //4.关闭资源
        jsc.stop();
    }
}
