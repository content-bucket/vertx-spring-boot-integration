FROM python:3.10-slim
WORKDIR /app
COPY requirements.txt requirements.txt
RUN python3 -m pip install -r requirements.txt
COPY locust-hello.py locust-hello.py
CMD python3 -m locust -f locust-hello.py --master
