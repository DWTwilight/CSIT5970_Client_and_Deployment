FROM python:3.12.10-slim-bookworm

WORKDIR /app

# RUN apt-get update && apt-get install -y \
#     libgl1 \
#     libglib2.0-0 \
#     libsm6 \
#     libxext6 \
#     libxrender-dev \
#     && rm -rf /var/lib/apt/lists/*

COPY distributed_yolo.py requirements.txt /app/

RUN pip install --no-cache-dir -r requirements.txt

ENTRYPOINT ["python", "distributed_yolo.py"]
CMD ["--help"]