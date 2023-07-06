FROM python:3.10-slim
WORKDIR /app
COPY requirements.txt requirements.txt
RUN python3 -m pip install -r requirements.txt
COPY locust-hello-dynamic.py locust-hello-dynamic.py
CMD python3 -m locust -f locust-hello-dynamic.py --worker
