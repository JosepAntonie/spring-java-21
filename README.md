# DevOps
Spring Java 21

## Docker
1. start service
    ``` bash
    docker compose up -d
    ```
2. stop service
    ``` bash
    docker compose stop
    ```
3. delete service
    ``` bash
    docker compose down
    ```
4. delete service + volume + image
    ``` bash
    docker compose down -v --rmi all
    ```
5. list services
    ``` bash
    docker compose ps
    ```
6. list content
    ``` bash
    docker ps
    ```
7. list image + remove image
    ``` bash
    docker image ls;
    docker image rm <image_name>;
    ```
8. list image + remove volume
    ``` bash
    docker volume ls;
    docker volume rm <volume_name>;
    ```
9. list network + remove network
    ``` bash
    docker network ls;
    docker network rm <network_name>;
    ```
10. ReBuild 
    ``` bash
    docker compose up -d --build backend;
    ```