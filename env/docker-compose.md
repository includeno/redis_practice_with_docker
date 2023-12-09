# docker-compose command

## image
```
docker pull redis:6.2.14
docker pull mysql:8.0.35
```

## run
```
docker-compose -f docker-compose.yml up -d
```

## inspect
```
docker-compose -f docker-compose.yml logs mysql
docker-compose -f docker-compose.yml logs redis
```

## stop&remove container
```
docker-compose -f docker-compose.yml stop
docker-compose -f docker-compose.yml down
```

## remove image
```
docker image rm mysql:8.0.35
docker image rm redis:6.2.14
```