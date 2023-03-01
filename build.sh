docker rm -f app db
docker build -t api_backend .
docker-compose up