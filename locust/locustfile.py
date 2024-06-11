from locust import HttpUser, task, between
import random

class WebsiteUser(HttpUser):
    wait_time = between(1, 5)

    @task(3)
    def get_external_api(self):
        endpoint = random.randint(1, 5)
        self.client.get(f"/api/v1/external/{endpoint}", name="/api/v1/external/[param]")

    @task(2)
    def get_all_users(self):
        self.client.get("/api/v1/users")

    @task(1)
    def add_new_user(self):
        user_data = {
            "name": "John Doe"
        }
        self.client.post("/api/v1/users", json=user_data)
