from locust import task, between, FastHttpUser


class HttpTest(FastHttpUser):
    wait_time = between(0, 1)
    host = "http://localhost:8000"

    @task
    def endpoint(self):
        self.client.get("/hello")
