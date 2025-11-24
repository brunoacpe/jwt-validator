# ================================
#  Makefile for jwt-validator API
# ================================

APP_NAME=jwt-validator
DOCKER_IMAGE=$(APP_NAME):latest
PORT=8080

# -------- MAVEN COMMANDS --------

mvn-build:
	mvn clean package -DskipTests

mvn-test:
	mvn clean test

mvn-clean:
	mvn clean


# -------- DOCKER COMMANDS --------

docker-build:
	docker build -t $(DOCKER_IMAGE) .

docker-run:
	docker run -d --name $(APP_NAME) -p $(PORT):8080 $(DOCKER_IMAGE)

docker-stop:
	docker stop $(APP_NAME) || true
	docker rm $(APP_NAME) || true

docker-clean:
	docker rmi -f $(DOCKER_IMAGE) || true

docker-rebuild: docker-stop docker-clean docker-build docker-run


# -------- HELP --------

help:
	@echo ""
	@echo "Comandos disponíveis:"
	@echo "  make mvn-build        - Gera o .jar sem rodar testes"
	@echo "  make mvn-test         - Roda todos os testes"
	@echo "  make mvn-clean        - Limpa os artefatos do Maven"
	@echo "  make docker-build     - Constrói a imagem Docker"
	@echo "  make docker-run       - Sobe o container na porta 8080"
	@echo "  make docker-stop      - Para e remove o container"
	@echo "  make docker-clean     - Remove a imagem Docker"
	@echo "  make docker-rebuild   - Faz rebuild completo e roda"
	@echo ""


# -------- DEFAULT --------

all: mvn-build docker-build docker-run