from pyspark.sql import SparkSession
from pyspark.sql.functions import col
import redis


def process_batch(df, epoch_id):
    # 每个批次处理函数
    def process_partition(partition):
        pool = redis.ConnectionPool(
            host="redis.default.svc.cluster.local", port=6379, decode_responses=True
        )
        r = redis.Redis(connection_pool=pool)
        for row in partition:
            key = f"test-spark:{row['value']}"
            r.incr(key)
        pool.disconnect()

    df.foreachPartition(process_partition)


if __name__ == "__main__":
    spark = (
        SparkSession.builder.appName("StructuredStreamingKafkaRedis")
        .config("spark.sql.shuffle.partitions", "4")
        .getOrCreate()
    )

    # Kafka配置（参考网页7）
    df = (
        spark.readStream.format("kafka")
        .option(
            "kafka.bootstrap.servers", "kafka-headless.kafka.svc.cluster.local:9092"
        )
        .option("subscribe", "test-spark")
        .option("group.id", "spark-consumer-group")
        .load()
    )

    # 数据转换（参考网页3）
    stream_data = df.select(col("value").cast("string"))

    # 输出到Redis（参考网页9、10）
    query = (
        stream_data.writeStream.foreachBatch(process_batch)
        .trigger(processingTime="10 seconds")
        .option("checkpointLocation", "/tmp/checkpoint")
        .start()
    )

    query.awaitTermination()
