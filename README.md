# Readme

## privilege issue

```sh
sudo usermod -aG docker $USER && newgrp docker
eval $(minikube docker-env)
```

## start dashboard

```sh
nohup minikube dashboard > dashboard.log 2>&1 &
```

## Kafka

### Install via Strimiz

```sh
kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka

kubectl apply -f https://strimzi.io/examples/latest/kafka/kraft/kafka-single-node.yaml -n kafka
```

- `kafka`(internal): `my-cluster-kafka-bootstrap:9092`, `kafka`, `my-cluster-kafka-bootstrap.kafka.svc.cluster.local:9092`
- `kafka-ui`(external): `192.168.49.2:30080`

## Spark

### install spark-operator:

```sh
# 添加 Helm 仓库并安装（推荐）
helm repo add spark-operator https://kubeflow.github.io/spark-operator
helm repo update
helm install my-release spark-operator/spark-operator --namespace spark-operator --create-namespace --set webhook.enable=true --set "spark.jobNamespaces={spark-apps}" --wait
```

### create service account for spark job

```sh
kubectl create namespace spark-apps
kubectl create serviceaccount spark --namespace=spark-apps
kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=spark-apps:spark --namespace=spark-apps
```

### create job

```sh
# kubectl delete sparkapplication test-spark -n spark-apps
kubectl apply -f spark-job.yml
```

## External

- `postgres`: `postgres:5432`, `default`, `postgres.default.svc.cluster.local:5432`
- `redis`: `redis:6379`, `default`, `redis.default.svc.cluster.local:6379`

### test

```sh
kubectl run test-redis --image=redis:alpine -it --rm --restart=Never -- sh
/data # redis-cli -h redis -p 6379

kubectl run test-pod --image=postgres:alpine --rm -it --restart=Never -- sh
/ # psql -h postgres -p 5432 -U postgres
```

## API Definition

### Video Upload: `POST /upload`

Returns:

```json
{ "job_id": "jobid" }
```

### Check job status: `GET /job/{job_id}`

Returns:

Success:

```json
{
  "status": 0,
  "frames": 1000,

  "labels": {
    "0": "person",
    "1": "cat"
  },

  "results": [
    [
      // frame 0
      [0, 0.9, 100, 100, 200, 200], // [label_id, confidence, x1, y1, x2, y2]
      [1, 0.8, 100, 100, 200, 200]
    ],
    [
      // frame 1
      [0, 0.9, 100, 100, 200, 200]
    ]
  ]
}
```

In progress:

```json
{
  "status": 1,
  "frames": 1000,
  "processed_frames": 100
}
```

Failed:

```json
{
  "status": 2,
  "message": "error message"
}
```
