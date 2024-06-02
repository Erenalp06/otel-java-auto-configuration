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

### Docker Compose ile Servisleri Başlatma

Aşağıdaki `docker-compose.yml` dosyasını kullanarak Elasticsearch, Kibana ve Jaeger servislerini başlatabilirsiniz. 

```yaml
version: '3.7'

services:
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.9.3
    container_name: elasticsearch
    networks:
      - shared_network
    environment:
      - discovery.type=single-node
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - network.host=0.0.0.0için
    ports:
      - "9200:9200"
      - "9300:9300"
    healthcheck:
      test: ["CMD-SHELL", "curl --silent --fail localhost:9200/_cluster/health?wait_for_status=yellow&timeout=50s || exit 1"]
      interval: 30s
      timeout: 10s
      retries: 5

  kibana:
    image: docker.elastic.co/kibana/kibana:7.9.3
    container_name: kibana
    networks:
      - shared_network
    ports:
      - "5601:5601"
    depends_on:
      elasticsearch:
        condition: service_healthy
    environment:
      - ELASTICSEARCH_URL=http://elasticsearch:9200

  jaeger:
    image: jaegertracing/all-in-one:latest
    container_name: jaeger-es
    networks:
      - shared_network
    ports:
      - "5775:5775/udp"
      - "6831:6831/udp"
      - "6832:6832/udp"
      - "5778:5778"
      - "16686:16686"
      - "14268:14268"
      - "14250:14250"
      - "9411:9411"
      - "4317:4317"
      - "4318:4318"
    environment:
      - COLLECTOR_ZIPKIN_HTTP_PORT=9411
      - SPAN_STORAGE_TYPE=elasticsearch
      - ES_SERVER_URLS=http://elasticsearch:9200
      - COLLECTOR_OTLP_ENABLED=true
    depends_on:
      elasticsearch:
        condition: service_healthy

networks:
  shared_network:
    external: true

```

Bu yapılandırmayı docker-compose.yml dosyası olarak kaydedin ve aşağıdaki komutu çalıştırarak tüm servisleri başlatın:

```bash
docker-compose up
```

## Uygulamayı Çalıştırmak

Uygulamayı Docker Compose ile başlatmak için aşağıdaki adımları izleyin:

### 1.  Repository'i klonlayın

```bash
git clone ...
cd ...
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

##  Jaeger ile İzleme

Uygulamanız çalıştığında, izleme verileri otomatik olarak Jaeger'a gönderilir. Jaeger UI'ya http://localhost:16686 adresinden erişebilir ve izleme verilerinizi görselleştirebilirsiniz.

## Kibana ile Görselleştirme

Kibana'ya http://localhost:5601 adresinden erişerek, Elasticsearch'te saklanan veriler üzerinde görselleştirmeler ve analizler yapabilirsiniz.
