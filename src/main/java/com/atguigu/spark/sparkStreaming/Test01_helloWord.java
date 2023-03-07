package com.atguigu.spark.sparkStreaming;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;

import java.util.*;

public class Test01_helloWord {
    public static void main(String[] args) throws InterruptedException {
        //创建spark流式环境
        JavaStreamingContext streamingContext = new JavaStreamingContext("local[1]", "sparkStreaming", Durations.seconds(3));
        //创建配置参数
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");
        hashMap.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
        hashMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        hashMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        hashMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");

        //连接kafka获取数据
        JavaInputDStream<ConsumerRecord<String, String>> topic_dbStream= KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent(), ConsumerStrategies.Subscribe(Arrays.asList("topic_db"), hashMap));
        //处理数据,只保留kagka的value值
        topic_dbStream.map(new Function<ConsumerRecord<String, String>, String>() {
            @Override
            public String call(ConsumerRecord<String, String> v1) throws Exception {
                return v1.value();
            }
        }).print(100);
        streamingContext.start();
        streamingContext.awaitTermination();
    }
}




























