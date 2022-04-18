#!/bin/bash
echo "> 현재 구동중인 profile 확인"
CURRENT_PROFILE=$(curl -s http://localhost/profile)
echo "> $CURRENT_PROFILE"
if [ $CURRENT_PROFILE == prod1 ]
then
	IDLE_PROFILE=prod2
	IDLE_PORT=8090
elif [ $CURRENT_PROFILE == prod2 ]
then
	IDLE_PROFILE=prod1
	IDLE_PORT=8089
else
	echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
	echo "> prod1을 할당합니다. IDLE_PROFILE: prod1"
	IDLE_PROFILE=prod1
	IDLE_PORT=8089
fi
echo "> $IDLE_PROFILE 배포"
sudo fuser -k -n tcp $IDLE_PORT
sudo nohup java -jar core-0.0.1-SNAPSHOT.jar --spring.config.location=file:config/prod-application.yml --spring.profiles.active=$IDLE_PROFILE $
echo "> $IDLE_PROFILE 10초 후 Health check 시작"
echo "> curl -s http://localhost:$IDLE_PORT/actuator/health"
sleep 10
for retry_count in {1..10}
do
	response=$(curl -s http://localhost:$IDLE_PORT/actuator/health)
	up_count=$(echo $reponse | grep 'UP' | wc -l)
	if [ $up_count -ge 1 ]
	then
		echo "> Health check 성공"
		break
	else
		echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
		echo "> Health check: ${response}"
	fi
	if [ $retry_count -eq 10 ]
	then
		echo "> Health check 실패. "
		echo "> Nginx에 연결하지 않고 배포를 종료합니다."
		exit 1
	fi
	echo "> Health check 연결 실패. 재시도..."
	sleep 10
done
echo "> 스위칭을 시도합니다..."
sleep 10
/home/ec2-user/backend-phonedyguard/core/build/libs/switch.sh
