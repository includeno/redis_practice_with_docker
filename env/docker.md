# Docker 相关命令

## 拉取镜像

```bash
docker pull redis:6.2.14
docker pull mysql:5.7.44
```

## 启动 Redis

```bash
docker run -itd --name redis_container -p 6379:6379 redis:6.2.14 redis-server --appendonly yes --requirepass "redis123456"
```