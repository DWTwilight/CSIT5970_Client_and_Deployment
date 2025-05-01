# Readme

## K8S Dashboard Token

```sh
kubectl -n kubernetes-dashboard create token dashboard-admin
```

## Kafka

- `kafka`(internal): `kafka-headless:9092`, `kafka`, `kafka-headless.kafka.svc.cluster.local:9092`
- `kafka-ui`(external): `192.168.49.2:30080`

## Spark

### install spark-operator:

```sh
# 添加 Helm 仓库并安装（推荐）
helm repo add spark-operator https://kubeflow.github.io/spark-operator
helm repo update
helm install my-release spark-operator/spark-operator --namespace spark-operator --create-namespace --set webhook.enable=true --set "spark.jobNamespaces={spark-apps}" --wait
```

```sh
kubectl create namespace spark-apps
kubectl create serviceaccount spark --namespace=spark-apps
kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=spark-apps:spark --namespace=spark-apps
```

## External

- `postgres`: `postgres:5432`, `default`, `postgres.default.svc.cluster.local:5432`
- `redis`: `redis:6379`, `default`, `redis.default.svc.cluster.local:6379`

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
