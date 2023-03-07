package com.atguigu.spark.sparkcore.top3;

import org.apache.spark.SparkConf;

public class Top3 {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("SparkSql").setMaster("local[2]");

    }
}
