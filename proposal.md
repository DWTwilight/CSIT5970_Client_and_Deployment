# CSIT5970 Course Project Proposal - Group 8

- JIANG, YihangLIU
- YishanLIU, Yishang
- ZHAO, Tianci

## Project Topic: Distributed Video Detection Using Yolo, Kafka and Spark

## Project Architecture (Provisional)

![Project Architecture](arch.png)

### **1. Core Components & Responsibilities**

| Component          | Role                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| Client             | Uploads videos to the backend and receives detection results.                             |
| Backend Server     | Handles user authentication, video preprocessing (frame extraction), and result delivery. |
| Kafka              | Acts as a distributed message buffer for video frames.                                    |
| Spark Streaming    | Processes frames in real-time using YOLO for object detection.                            |
| PostgreSQL & Redis | Stores detection metadata (PostgreSQL) and caches real-time results (Redis).              |
| Kubernetes Cluster | Hosts backend, Kafka, Spark, and databases as scalable pods.                              |

### **2. Workflow**

1. Client Upload

   - Clients upload videos via HTTP/HTTPS to the backend API, exposed through Nginx Ingress.
   - Backend validates user credentials and begin preprocessing the video.

2. Frame Extraction & Kafka Ingestion

   - Backend splits the video into individual frames, do resolution adjustment and other preprocess steps.
   - Each frame is serialized and pushed to a Kafka topic.

3. Spark Streaming & YOLO Processing

   - Spark consumes the Kafka stream and distributes frames across worker nodes.
   - YOLO model detects objects in each frame and output bounding boxes.
   - Results are stored in PostgreSQL (for historical queries) and Redis (for real-time client polling).

4. Result Delivery
   - Backend retrieves detection results from Redis or PostgresSQL and streams them to the client via WebSocket or HTTP long-polling.

### **3. Kubernetes Deployment**

- Backend: Deployed as a scalable K8s Deployment with horizontal pod autoscaling (HPA).
- Kafka: Uses Strimzi Operator for K8s-native Kafka cluster management.
- Spark: Runs as a K8s Job or Spark Operator-managed cluster, leveraging GPU nodes for YOLO inference.
- Nginx Ingress: Routes external traffic to backend services.
