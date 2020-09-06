FROM maven:3-openjdk-8

WORKDIR /app

COPY . /app

ENV MODE=production
ENV PORT=12128

# http
EXPOSE 12128

CMD ["./mvnw", "clean", "compile", "exec:java"]
