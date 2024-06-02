.PHONY: run
run:
	docker-compose up --build -d

.PHONY: clean
clean:
	docker-compose down
