# Pipeline Observer

Pipeline Observer, sistem kaynaklarını, PostgreSQL metriklerini ve uygulama loglarını gerçek zamanlı olarak izleyen bir **backend observability / telemetry** uygulamasıdır.

Uygulama; **CPU, RAM, Disk, PostgreSQL** ve **uygulama loglarını** toplar, verileri PostgreSQL üzerinde saklar ve canlı verileri **Server-Sent Events (SSE)** üzerinden istemcilere aktarır.

Mimari tasarımda **Hexagonal Architecture (Ports & Adapters)** ve **Event-Driven Architecture (EDA)** yaklaşımı kullanılmıştır.

---

## Özellikler

- CPU kullanımını izleme
- RAM kullanımını izleme
- Disk kapasitesi ve kullanımını izleme
- PostgreSQL aktif bağlantı ve veritabanı boyutu metriklerini izleme
- Uygulama loglarını canlı olarak yayınlama
- SSE ile gerçek zamanlı veri akışı
- Metrik ve log verilerini PostgreSQL üzerinde saklama
- Zamanlanmış data retention işlemleri
- Event-driven yapı
- Hexagonal Architecture ile ayrıştırılmış katmanlar
- I/O ağırlıklı kayıt işlemlerinde `@Async` kullanımı
- Threshold tabanlı alert mekanizması

---

## Teknoloji Yığını

| Alan | Teknoloji |
|---|---|
| Runtime | Java 21 |
| Framework | Spring Boot 3 |
| Web | Spring MVC |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL |
| Real-Time | Server-Sent Events (`SseEmitter`) |
| Scheduling | Spring Scheduling |
| Async | Spring Async |
| Logging | SLF4J / Logback |
| Build | Maven |
| Utility | Lombok |

---

## Mimari

Proje üç temel bölüme ayrılmıştır:

### Domain

Uygulamanın çekirdeğidir. Domain modelleri ve port sözleşmeleri burada bulunur. Domain katmanı HTTP, PostgreSQL veya Spring gibi dış teknolojilere doğrudan bağlı değildir.

### Application

Use case implementasyonları, servisler ve application event'leri burada bulunur. İş akışlarını orkestre eder.

### Infrastructure

REST endpoint'leri, scheduler'lar, event listener'lar, Logback entegrasyonu ve PostgreSQL adaptörleri gibi dış dünya bileşenlerini içerir.

Genel akış:

```text
Scheduler / Event / REST
          │
          ▼
     Inbound Port
          │
          ▼
   Application Service
       │       │
       │       └──────────► SSE Stream
       │
       ▼
   Outbound Port
          │
          ▼
   PostgreSQL Adapter
          │
          ▼
      PostgreSQL
```

---

## Mimari Kararlar

### Hexagonal Architecture

Inbound portlar uygulamanın dış dünyadan çağrılabilecek use case'lerini tanımlar.

Örnek:

```text
SaveCpuMetricUseCase
StreamCpuUseCase
DataRetentionUseCase
```

Outbound portlar ise uygulamanın veritabanı gibi dış sistemlerle olan iletişimini soyutlar.

Örnek:

```text
SaveCpuMetricPort
SaveRamMetricPort
SaveLogPort
MetricRetentionPort
```

Bu sayede PostgreSQL veya başka bir altyapı implementasyonu domain/application kodundan ayrılmış olur.

### Metot Düzeyinde `@Async`

`@Async`, özellikle veritabanına kayıt gibi I/O ağırlıklı işlemlerde kullanılmıştır. Böylece persistence işlemlerinin gecikmesi canlı SSE akışını gereksiz şekilde bekletmez.

```text
Metric ölçümü
     │
     ├──► SSE ─────► Anlık yayın
     │
     └──► Database ─► Async I/O
```

### Open/Closed Principle ve Liste Enjeksiyonu

Retention mekanizmasında `MetricRetentionPort` implementasyonları liste olarak enjekte edilir:

```java
@Service
@RequiredArgsConstructor
public class DataRetentionService implements DataRetentionUseCase {

    private final List<MetricRetentionPort> metricRetentionPorts;
    private final LogRetentionPort logRetentionPort;

    @Override
    public void purgeHistoricalData() {
        LocalDateTime cutoffDateForMetrics =
                LocalDateTime.now().minusDays(2);

        LocalDateTime cutoffDateForLogs =
                LocalDateTime.now().minusDays(7);

        for (MetricRetentionPort port : metricRetentionPorts) {
            port.deleteMetricsOlderThan(cutoffDateForMetrics);
        }

        logRetentionPort.deleteLogsOlderThan(cutoffDateForLogs);
    }
}
```

Yeni bir telemetri türü eklendiğinde mevcut retention orchestration kodunu değiştirmeden yeni bir adapter eklenebilir.

---

## Proje Yapısı

README açısından yalnızca uygulamanın yapısını açıklayan dosya ve klasörler gösterilmiştir. IDE dosyaları ve derleme çıktıları özellikle dahil edilmemiştir.

```text
pipeline-observer/
├── src/
│   ├── main/
│   │   ├── java/com/pipeline/observer/
│   │   │   ├── application/
│   │   │   │   └── management/
│   │   │   │       ├── event/          # Application event'leri
│   │   │   │       └── service/        # Use case implementasyonları
│   │   │   │           ├── alert/
│   │   │   │           ├── cpu/
│   │   │   │           ├── database/
│   │   │   │           ├── disk/
│   │   │   │           ├── log/
│   │   │   │           ├── ram/
│   │   │   │           └── retention/
│   │   │   │
│   │   │   ├── domain/
│   │   │   │   ├── model/              # Domain modelleri
│   │   │   │   └── ports/
│   │   │   │       ├── inbound/usecase/ # Inbound portlar
│   │   │   │       └── outbound/        # Outbound portlar
│   │   │   │
│   │   │   └── infrastructure/
│   │   │       ├── inbound/
│   │   │       │   ├── event/           # Event listener'lar
│   │   │       │   ├── rest/            # Controller ve DTO'lar
│   │   │       │   └── scheduler/       # Zamanlanmış görevler
│   │   │       ├── log/                  # Logback entegrasyonu
│   │   │       └── outbound/database/    # PostgreSQL adapter, entity ve repository'ler
│   │   │
│   │   └── resources/
│   │       ├── application.properties   # Uygulama / DB ayarları
│   │       ├── logback-spring.xml        # Log yapılandırması
│   │       └── static/                   # Monitoring arayüzü
│   │           ├── index.html
│   │           ├── css/monitor.css
│   │           └── js/dashboard.js
│   │
│   └── test/
│       └── java/com/pipeline/observer/  # Testler
│
├── docker-compose.yml                    # Docker servisleri
├── pom.xml                               # Maven bağımlılıkları ve build ayarları
├── README.md
└── .gitignore
```

---

## API Endpoints

Canlı veri endpoint'leri `text/event-stream` döndürür ve bağlantıyı açık tutarak periyodik veri gönderir.

| Modül | Method | Endpoint | Açıklama |
|---|---|---|---|
| CPU | `GET` | `/api/v1/metrics/cpu/stream` | Anlık CPU kullanım yüzdesi |
| RAM | `GET` | `/api/v1/metrics/ram/stream` | Toplam, boşta ve kullanılan RAM |
| Disk | `GET` | `/api/v1/metrics/disk/stream` | Disk alanı ve kullanım yüzdesi |
| Database | `GET` | `/api/v1/metrics/database/stream` | Aktif PostgreSQL bağlantıları ve DB boyutu |
| Logs | `GET` | `/api/v1/metrics/log/stream` | Canlı uygulama logları |

### SSE Örneği

```bash
curl -N http://localhost:8080/api/v1/metrics/cpu/stream
```

---

## Data Retention

`DataRetentionScheduler` her gece **03:00**'te retention işlemini tetikler.

| Veri | Saklama Süresi |
|---|---:|
| CPU metrikleri | 2 gün |
| RAM metrikleri | 2 gün |
| Disk metrikleri | 2 gün |
| Database metrikleri | 2 gün |
| Uygulama logları | 7 gün |

Eski kayıtlar toplu delete sorguları ile temizlenir. Retention işlemlerinde `@Transactional` kullanılır.

Örnek sorgu yaklaşımı:

```java
deleteByTimestampBefore(...)
```

---

## Gereksinimler

- **JDK 21+**
- **PostgreSQL 15+**
- **Maven 3.8+**

---

## Kurulum

### 1. Projeyi Klonla

```bash
git clone <repository-url>
cd pipeline-observer
```

### 2. PostgreSQL Veritabanını Oluştur

```sql
CREATE DATABASE observer_db;
```

### 3. Veritabanı Ayarlarını Yapılandır

`src/main/resources/application.properties` içindeki PostgreSQL bağlantı bilgilerini kendi ortamınıza göre düzenleyin.

Örnek:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/observer_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 4. Docker ile Çalıştırma

Projede `docker-compose.yml` bulunduğu için desteklenen servisleri Docker üzerinden de başlatabilirsiniz:

```bash
docker compose up -d
```

### 5. Projeyi Derleme

Linux/macOS:

```bash
./mvnw clean install
```

Windows:

```powershell
mvnw.cmd clean install
```

### 6. Uygulamayı Başlatma

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
mvnw.cmd spring-boot:run
```

Uygulama varsayılan olarak:

```text
http://localhost:8080
```

adresinde çalışır.

---

## Canlı Akışı Test Etme

CPU:

```bash
curl -N http://localhost:8080/api/v1/metrics/cpu/stream
```

RAM:

```bash
curl -N http://localhost:8080/api/v1/metrics/ram/stream
```

Disk:

```bash
curl -N http://localhost:8080/api/v1/metrics/disk/stream
```

Database:

```bash
curl -N http://localhost:8080/api/v1/metrics/database/stream
```

Log:

```bash
curl -N http://localhost:8080/api/v1/metrics/log/stream
```

---

## Sistem Akışı

```text
System Metrics / Application Logs
               │
               ▼
        Application Service
               │
        ┌──────┴──────┐
        ▼             ▼
 Application Event    Outbound Port
        │             │
        ▼             ▼
 Event Listener    DB Adapter
        │             │
        ▼             ▼
    SSE Stream     PostgreSQL
```

Bu ayrıştırma, canlı veri yayını ile persistence sorumluluklarının birbirinden bağımsız ilerlemesini sağlar.

---

## Genişletilebilirlik

Yeni bir telemetri kaynağı mevcut mimariye benzer şekilde eklenebilir:

```text
Domain Model
     ↓
Inbound Use Case
     ↓
Application Service
     ↓
Outbound Port
     ↓
Infrastructure Adapter
     ↓
PostgreSQL Repository
```

Örneğin ileride **GPU** veya **Network** metrikleri eklenebilir.

---

## Amaç

Pipeline Observer; gerçek zamanlı telemetri toplama, event-driven veri akışı, SSE streaming, persistence, scheduling ve retention gibi backend kavramlarını tek bir proje içerisinde uygulamaya yönelik geliştirilmiştir.

Öne çıkan çalışma alanları:

- Hexagonal Architecture
- Event-Driven Architecture
- Spring Boot
- Server-Sent Events
- Asynchronous I/O
- PostgreSQL / JPA
- Scheduled Tasks
- Data Retention
- Application Logging
- Modüler backend tasarımı

---

## Gelecek Geliştirmeler

- GPU monitoring
- Network monitoring
- Gelişmiş alert mekanizması
- Authentication / Authorization
- Monitoring dashboard geliştirmeleri
- Docker containerization'ın genişletilmesi
- Metric aggregation ve filtreleme
- Gelişmiş log arama ve filtreleme
