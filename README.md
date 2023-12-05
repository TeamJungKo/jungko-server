# JungkoServer

모든 중고거래 마켓을 한 곳에 보는 "중코거래" 서비스의 애플리케이션 서버 프로젝트입니다.

## Prerequisites

- Docker Desktop 설치가 필요합니다.
  https://www.docker.com/products/docker-desktop/
- Docker Compose 설치가 필요합니다.
  https://docs.docker.com/compose/install/

```bash
docker compose down --rmi all && docker compose up --build -d
```

해당 명령어를 통해 Nginx와 MariaDB 컨테이너를 구동시킬 수 있습니다.
Nginx는 [클라이언트 애플리케이션](https://github.com/TeamJungKo/jungko-client)과 서버 애플리케이션에 대한 리버스 프록시 역할을 수행합니다.

## Build

```bash
chmod +x gradlew && ./gradlew clean build
```

해당 명령어를 통해 프로젝트를 빌드를 할 수 있습니다.

```bash
java -Dspring.profiles.active=[활성화할 프로필 이름] -jar ./build/libs/jungko-server-0.0.1-SNAPSHOT.jar
```

빌드된 jar 파일을 실행하여 스프링 부트 애플리케이션을 구동할 수 있습니다.

## Quickstart

```bash
chmod +x quickstart.sh && bash quickstart.sh [활성화할 프로필 이름]
```

해당 명령어를 통해 위 과정을 한 번에 실행할 수 있습니다.

```bash
chmod +x quickstart.sh && bash quickstart.sh demo
```

⚠️ 중코거래 프로젝트 팀원이 아니라면 demo 라는 이름으로 프로필을 지정해주세요.

## 클라이언트 애플리케이션과의 연동

클라이언트 프로젝트: https://github.com/TeamJungKo/jungko-client

해당 프로젝트의 Readme를 참고하여 클라이언트 애플리케이션을 구동시킬 수 있습니다.

구동을 완료 후, http://localhost 로 접속하면 로컬에서 중코거래 서비스를 이용할 수 있습니다.

## 추가 설정

<br/>

<img width="733" alt="스크린샷 2023-12-05 오전 10 21 27" src="https://github.com/TeamJungKo/jungko-server/assets/83565255/67257db6-cdf6-4969-a0a5-e1850a0c588d">

소셜 로그인을 위해서는 구글, 카카오, 네이버 애플리케이션 등록 및 Client ID, Client Secret 키 설정이 필요합니다.

구글 클라우드 콘솔: https://console.cloud.google.com/apis/api/people.googleapis.com/
Kakao Developers: https://developers.kakao.com/console/app
네이버 개발자 센터: https://developers.naver.com/apps
