# java_kit

![](https://img.shields.io/badge/vert.x-4.0.0.Beta1-purple.svg)

### Building

To launch your tests:
```
./mvnw clean test
```

To package your application:
```
./mvnw clean package
```

To run your application:
```
./mvnw clean compile exec:java
```

⚠ Default port: **12128**

### Use docker

```
docker-compose up -d
```

```
curl -X GET "localhost:12128/api/cat"
```

```
# response
{
	"success": true,
	"data": {
		"query": "Xin Chao 😍😍"
	}
}

```
#### License

Project under the [Vertx License](https://vertx.io/) and [MIT LICENSE](LICENSE)
