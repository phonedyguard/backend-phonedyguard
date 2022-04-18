#!/bin/bash
echo "> 현재 구동중인 Port 확인"
CURRENT_PROFILE=$(curl -s http://localhost/profile)

if [ $CURRENT_PROFILE == prod1 ]
then
	IDLE_PORT=8090
elif [ $CURRENT_PROFILE == prod2 ]
then
	IDLE_PORT=8089
else
	echo "> 일치하는 Profile이 없습니다. Profile:$CURRENT_PROFILE"
	echo "> 8089를 할당합니다."
	IDLE_PORT=8089
fi

PROXY_PORT=$(curl -s http://localhost/profile)
echo "> 현재 구동중인 Port: $PROXY_PORT"

echo "> 전환할 Port : $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Nginx Reload"
sudo service nginx reload
