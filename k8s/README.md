# Deploy personal-service ke Kubernetes

Semua perintah di bawah dijalankan dari dalam folder `k8s/`.

```
cd k8s
```

## 1. ConfigMap

`application.yml` dan `application-personal-service.yml` sengaja ditulis polos (tanpa `apiVersion`/`kind`) supaya gampang di-diff dengan `src/main/resources`. ConfigMap-nya di-generate dari file itu, bukan ditulis manual.

```
kubectl create configmap personal-service-common-config \
  --from-file=application.yml \
  -o yaml --dry-run=client | kubectl apply -f -

kubectl create configmap personal-service-config \
  --from-file=application-personal-service.yml \
  -o yaml --dry-run=client | kubectl apply -f -
```

> Setiap kali isi `application.yml` / `application-personal-service.yml` berubah, jalankan ulang perintah yang sesuai di atas untuk update ConfigMap-nya, lalu restart deployment (lihat langkah 4).

## 2. Secret

`secret-common.json` dan `secret-personal-service.json` sudah berupa manifest Kubernetes (`kind: Secret`) dalam format JSON, key-nya (`REDIS_PASSWORD`, `DB_USERNAME`, `DB_PASSWORD`) match dengan variabel `${...}` di ConfigMap.

```
kubectl apply -f secret-common.json
kubectl apply -f secret-personal-service.json
```

## 3. Hostname/domain per environment

Host/port Postgres, Redis, dan endpoint Zipkin dikumpulkan jadi property biasa di bagian `infra:` paling atas `application.yml` — cari komentar `# hostname/domain infra`. Property Spring lain (`spring.datasource.url`, `spring.data.redis.host`, `management.zipkin.tracing.endpoint`) tinggal reference lewat placeholder, sama seperti `${spring.application.name}`.

Kalau deploy ke environment lain yang nama service-nya beda (misal staging vs prod), tinggal ubah value di blok `infra:` itu langsung, lalu regenerate ConfigMap (langkah 1) — tidak perlu sentuh `deployment.yaml`.

## 4. ServiceAccount, Deployment, Service

```
kubectl apply -f service-account.yaml
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

## 5. Restart deployment setelah ConfigMap/Secret berubah

Kubernetes tidak otomatis restart Pod kalau cuma ConfigMap/Secret yang berubah (kalau di-mount sebagai volume, isinya memang ke-update otomatis tapi JVM Spring Boot tidak re-read config saat runtime). Restart manual:

```
kubectl rollout restart deployment/personal-service
```

## Verifikasi

```
kubectl get pods -l app=personal-service
kubectl logs -l app=personal-service -f
kubectl port-forward svc/personal-service 8080:80
# lalu cek: curl http://localhost:8080/personal-service/actuator/health
```
