I have a local minikube cluster deployed on my ubuntu machine. I want to deploy a spark stream job on this cluster.
This spark stream job should subscribe to a kafka topic "test-spark", and the kafka service address is: `kafka-headless.kafka.svc.cluster.local:9092`; and the message format is just a short string(e.g. "hello"), upon receiving the message, the spark job should increament a key in redis(inc test-spark:{msg}), and redis address is: `redis.default.svc.cluster.local:6379`.
Show me step by step how to write and deploy this spark stream job.
