# Readme

## Kafka

- `kafka`(internal): `kafka-headless:9092`, `kafka`, `kafka-headless.kafka.svc.cluster.local:9092`
- `kafka-ui`(external): `192.168.49.2:30080`

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

## K8S Dashboard Token

```txt
eyJhbGciOiJSUzI1NiIsImtpZCI6Ii1BNURqckVRbnd1ZU1qbE9WUVJRUnpsSEhNVHBzS3RuRXQwbkpCR3RmWmcifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiXSwiZXhwIjoxNzQ2MDI3MDEyLCJpYXQiOjE3NDYwMjM0MTIsImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwianRpIjoiMjFmNzVkZjEtNzdjNS00YTMwLWE5MjMtMjgzMjhjMzYxZjVmIiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsInNlcnZpY2VhY2NvdW50Ijp7Im5hbWUiOiJkYXNoYm9hcmQtYWRtaW4iLCJ1aWQiOiI2MzdiMTU1Ny1kNTZmLTQzYjYtODdmOS1iMDMyMjQ2Y2UxZDAifX0sIm5iZiI6MTc0NjAyMzQxMiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmVybmV0ZXMtZGFzaGJvYXJkOmRhc2hib2FyZC1hZG1pbiJ9.ZRlYb9dvKZpNKkYEntgqV86bNww5JJyTEPCT60O-zrTRXwNnqI8SXVBnja8ERQpUoyeAiWRSN6xp9plE2UdPMpmuWGStR9_OTMhXvZFw5LOKHk7EioUKMyUnxXxNPBTbB84J1-9Af5UbK7a4n5eN5tPZmZZBbHiicrm_Prh6Um7yOqoW5gLdnuF037D6gnuZgLwuIlUcf_T9DBTSXf5bM9tiFvG5618dwuK8RCsoCDS1oeco46D1Ijqnr59T1QFc0ESlVuKRVoAFg8Tk0ul_xTTO5zJO0XUyYMaMNS3OReRnWzigNyUCcCTfMwlErescPqbc2d40avR_f6nWyxG9eA
```
