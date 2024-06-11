# OpenTelemetry ile Spring Boot uygulamasının Otomatik İzlenmesi

Bu rehber, Docker kullanarak Spring Boot uygulamanızı, Jaeger, Kibana ve Elasticsearch ile entegre şekilde nasıl çalıştırabileceğinizi açıklar.


## Gereksinimler

Bu uygulamayı yerel ortamınızda çalıştırabilmek için aşağıdaki araçların kurulu olması gerekmektedir.

- Docker
- Docker Compose
- Jaeger Collector
- ElasticSearch & Kibana

## Yapılandırma ve Kurulum

Uygulama, izleme verilerini bir Jaeger instance'ına göndermek için yapılandırılmıştır. Jaeger, izleme verilerini Elasticsearch'te saklar ve Kibana bu verileri görselleştirir.

### Jaeger Collector Kurulumu

Aşağıdaki komut ile Jaeger Collector'ünü başlatın.

```bash
 docker run -d --name jaeger-es \
  -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
  -e SPAN_STORAGE_TYPE=elasticsearch \
  -e ES_SERVER_URLS="http://10.X.X.X:9200" \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  -p 9411:9411 \
  -p 4317:4317 \
  -p 4318:4318 \
  jaegertracing/all-in-one:latest
```

## Uygulamayı Çalıştırmak

Uygulamayı Docker Compose ile başlatmak için aşağıdaki adımları izleyin:

### 1.  Repository'i klonlayın

```bash
git clone https://github.com/limansub/otel-java-client-example
cd otel-java-client-example
```

### 3. Docker Compose kullanarak uygulamayı başlatın

Öncelikle sisteminizde `make` aracının yüklü olduğundan emin olun.

Uygulamanızın bulunduğu dizine gidin.

Aşağıdaki komutu çalıştırarak uygulamanızı arka planda derleyin ve başlatın:

```bash
make run
```

## Uygulamayı Test Etmek

Uygulama çalıştıktan sonra, API'yi test etmek için curl veya herhangi bir API test aracını kullanabilirsiniz:

```bash
curl -X GET http://localhost:8080/api/v1/users
```

```bash
curl -X GET http://localhost:8080/api/v1/external/1
```

```bash
curl -X POST -H "Content-Type: application/json" -d '{ "name": "Deneme" }' http://localhost:8080/api/v1/u
sers
```

##  Jaeger ile İzleme

Uygulamanız çalıştığında, izleme verileri otomatik olarak Jaeger'a gönderilir. Jaeger UI'ya `http://10.X.X.X:16686` adresinden erişebilir ve izleme verilerinizi görselleştirebilirsiniz.

## Kibana ile Görselleştirme

Kibana'ya `http://10.X.X.X:5601` adresinden erişerek, Elasticsearch'te saklanan veriler üzerinde görselleştirmeler ve analizler yapabilirsiniz.
