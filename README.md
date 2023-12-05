# Node.js 앱 만들기

## Node.js 앱 생성에 필요한 두가지 파일 만들기

### package.json

- 프로젝트의 정보와 프로젝트에서 사용 중인 패키지의 의존성을 관리
    - 터미널에서 만들기
    1. `npm init`
    2. package name 부터 설정 완료 시 Enter
    3. `package.json` 파일 생성 확인
    4. 라이브러리 추가 (express) 
       - Node.js의 API 를 단순화하고 새로운 기능을 추가해서 Node.js를 더 쉽고 유용하게 사용 가능
          `"dependencies": {`
          `"express": "4.18.2"`
    5. start 스크립트 생성 
      - Node 시작 할 때 Node 라는 명령어를 주고 그 다음 처음 Entry Point(server.js) 를 주면 npm run start → Node.js 애플리케이션을 시작할 수가 있음 **server.js 생성**

```bash
(base) hyeranjang@Hyeranui-MacBookPro nodejs-docker-app % npm init 
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help init` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (nodejs-docker-app) 
version: (1.0.0) 
description: 
entry point: (index.js) server.js
test command: 
git repository: 
keywords: 
author: 
license: (ISC) 
About to write to /Users/hyeranjang/nodejs-docker-app/package.json:

{
  "name": "nodejs-docker-app",
  "version": "1.0.0",
  "description": "",
  "main": "server.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "",
  "license": "ISC"
}

Is this OK? (yes) y
npm notice 
npm notice New major version of npm available! 9.8.1 -> 10.2.4
npm notice Changelog: https://github.com/npm/cli/releases/tag/v10.2.4
npm notice Run npm install -g npm@10.2.4 to update!
npm notice 
(base) hyeranjang@Hyeranui-MacBookPro nodejs-docker-app %
```

```json
{
  "name": "nodejs-docker-app",
  "version": "1.0.0",
  "description": "",
  "main": "server.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "dependencies": {
    "express": "4.18.2"
  }
  "author": "",
  "license": "ISC"
}
```

### **server.js**

- Node.js에서 진입점이 되는 파일
    
    ```jsx
    const express = require('express');
    
    const PORT = 8080;
    
    //APP
    const app = express();
    app.get('/', (req,res) => {
        res.send("Hello World")
    });
    
    app.listen(PORT, HOST); 
    console.log('Running on http://${HOST}:${PORT}');
    ```
    
    - Express 모듈 불러오기
    `const express = require(’express’);`
    - Express 서버를 위한 포트 설정
    `const PORT = 8080;`
    - 호스트 지정
    `const HOST = ‘0.0.0.0’;`
    - 새로운 Express 어플 생성
    `const app = express();`
    - “/” 이 경로로 요청이 오면 Hello World를 결과값으로 전달
        
        ```
        app.get('/', (req,res) => {
            res.send("Hello World")
        });
        ```
        
    - 해당 포트와 호스트에서 HTTP 서버 시작
    `app.listen(PORT, HOST);`
    

**기본적인 Node.js 애플리케이션 완성  ✨**



# Dockerfile 작성

**Nodejs 앱을 도커 환경에서 실행하기 위해 이미지 생성 과정에서 Dockerfile 작성이 필요함**

1. 이미지 생성
2. 생성한 이미지를 이용해 컨테이너 실행
3. 실행 한 컨테이너 안에서 Nodejs 앱 실행

### 💡 Dockerfile 복습

`**베이스 이미지 명시 → 더 필요한 프로그램 다운로드 할 수 있 게 명시 → 시작 시 실행할 명령어 명시**`

- FROM
    - 이미지 생성 시 기반이 되는 이미지 레이어
    <이미지 이름><태그> 형식으로 작성 
    (태그를 안 붙이면 자동적으로 가장 최신것으로 다운받음)
- RUN
    - 도커이미지가 생성되기 전에 수행할 쉘 명령어
- CMD
    - 컨테이너가 시작되었을 때 실행할 실행 파일 또는 쉘 스크립트
    해당 명령어는 DockerFile 내 1회만 작성 가능
    

## Dockerfile 작성하기

```docker
FROM node:10

RUN npm install

WORKDIR /usr/src/app

COPY ./ ./

CMD [ "node", "server.js" ]
```

> `FROM node:10` : 베이스 이미지를 가져옴
`RUN npm install` : 종속성들을 설치 
`WORKDIR /usr/src/app` : 모든 어플리케이션을 위한 소스들을 보관
`CMD [ “node”, “server.js” ]` : 노드 어플리케이션에 node, server.js 명령어를 입력 > 실행 시킴
> 

### `FROM node:10`

- npm install 을 하기 위해서는 npm 이 들어있는 이미지를 찾아야 함
→ 그 중 하나가 `node` 이미지 
    : Alpine 이미지의 경우 에러 남 (가장 최소한 경량화된 파일들이 있기에, 5MB 밖에 안 됨)

### `RUN npm install`

- **npm install은?**

- package.js → dependencies 부분에 적어둔 모듈
    
    ```bash
    "dependencies": {
      "express": "4.18.2"
    ```
    
- 이 부분을 자동적으로 npm이 확인 → 다운받아 설치
    1. npm install → **NPM Resitry에서** 
    2. 모듈들을 D/L 해서 가져옴 → 
    3. 어플리케이션에 install 해줌

> npm 은 Node.js로 만들어진 모듈을 웹에서 받아 설치하고 관리해주는 프로그램
npm install : package.js 에 적혀있는 종속성들을 웹에서 자동으로 설치해주는 명령어 
결론적으로 현재 Node.js. 앱을 만들 때 필요한 모듈들을 다운받아 설치하는 역할을 할 것임
> 

### `COPY ./ ./`

- 호스트 Directory에 있는 파일을 도커 컨테이너에 복사하는 방식

- COPY 로 package.js & node.js 복사하기
- 디렉토리 부분을 전체 COPY 해서 컨테이너에 붙여넣기

- package.json
    - 애플리케이션의 종속성을 설치하는 데 필요
- node.js
    - 애플리케이션을 실행하는 데 필요

### `WORKDIR /usr/src/app`


**workdir 지정하지 않았을 때 리스크**

> COPY 할 때 원래 이미지에 있던 파일과 이름이 같다면 중복되는 폴더가 덮어져서 처리됨
모든 파일이 한 디렉토리에 들어가 정리가 안 되어있음
> 
- 이러한 이유로인해 work 디렉토리를 따로 만들어 보관함
- 애플리케이션과 관련된 소스들은 **work directory** 에 들어감
       
    - 컨테이너 접근 할 때 root 부터 접근 하는게 아닌 work directory에 바로 먼저 접근하게 됨
        

### `CMD [ "node", "server.js" ]`

- **“node”, “server” ?**
    - node 웹 서버를 작동 시키기 위해 node +entry 파일 이름 입력 
    entry file 이름 : `server.js`

### 포트 맵핑

- **포트 맵핑을 위한 컨테이너 실행 사용 명령어**
    
    `docer run -p 49160:8080`
    
    

**포트 맵핑을 하지 않은 상태의 서버 에러**

- 사용 한 컨테이너 실행 명령어 
`docker run 이미지 이름`
        

**포트맵핑으로 해결 (위의 그림 참고)**

- 컨테이너 실행하기 위한 명령어
`docker run -p 49160:8080 이미지이름`
- 로컬 네트워크에 있던 것을 컨테이너 내부에 있는 네트워크에 연결을 시켜주어야 함
- 네트워크 8080으로 들어가기 위해서는 명령어 부분에서 포트 맵핑을 시켜주어야 함 
ex. `-p 49160:8080 이미지이름`
    - 브라우저 주소 네트워크 포트를 49160 줌
    - 컨테이너 안의 포트도 49160으로 변경
    - 49160 포트로 가면 8080과 맵핑이 되어있어서 컨테이너 안에 8080으로 들어감
    - **49160(localhost):8080 (컨테이너)**

## 생성한 이미지 빌드

- 명령어 실행|
`docker run -p 49160:8080 이미지이름`
    - 명령어의 로컬호스트 포트는 다른 번호로 해도 무관함
    ex. `docker run -p 5000:8080 이미지이름`
        
       
- 브라우저 실행
    1. 브라우저 http://hocalhost:49160
    2. 49160 포트로 접속
    3. 컨테이너 속 8080 포트로 접속



# 소스 변경으로 재 빌드

## COPY 단계를 바꿔 모듈 변화에만 빌드되도록 변경

- 모듈을 다시 받는 경우는 모듈에 변화가 생겨야만 다시 받지만, 현재 소스로는 소스 코드에 자그마한 변화만 있어도 모듈 전체를 받고 있음
    - npm install 하기 전 package.json만 따로 변경을 해줌으로써 모듈을 다시 받지 않아도 됨

- npm install 시 불필요한 다운로드를 피하기 위해 수정
    - npm install 하기 전 단계에서 오직 module 에 관한 것만 해주도록 수정 후  Npm install 이후 다시 모든 파일을 COPY 해주도록 수정
        

    
    ```docker
    FROM node:10
    
    WORKDIR /usr/src/app\
    
    COPY package*.json ./
    
    RUN npm install express 
    
    COPY ./ ./
    
    CMD [ "node", "server.js" ]
    ```
    
    - 모듈은 모듈에 변화가 생길 때만 다시 다운로드 해주고 소스 코드에 변화가 생길 때 모듈을 다시 받는 현상을 없애줌
 

  # Docker 볼륨

- 소스를 변경할 때 마다 변경된 소스 부분을 `**COPY → 이미지 재빌드 → 컨테이너 재실행**`의 경로를 없애기 위해 Volume 사용
- **도커 컨테이너에서 호스트 Directory에 있는 파일들을 매핑**해서 사용하는 방식
    


## Volume 사용해서 어플리케이션 실행하기


`docker run -d -p 6000:8080 -v /usr/src/app/node_modules -v $(pwd):/usr/src/app mj.ran91/nodejs`


<aside>
💡 **실행중인 포트 확인하기 & 죽이기☠️
1. 사용 중인 포트 확인하기
    `sudo lsof - i :포트번호`
2. 실행중인 포트 종료하기 
     `kill -9 PID`**
     (PID: Process ID)


[**사용중인 전체 포트 확인하기]
`sudo lsof -i -p -n | grep LISTEN`**


</aside>

<aside>
💡 **PWD (Print Working Directory)**

- 현재 작업 중인 Directory 이름 출력
    

</aside>

## 실행하기

- 이미지 빌드를 따로 하지 않고도 변경한 소스가 반영 됨
    - 변경한 소스만 반영되는 걸 보기 위해선 node 종료 후 다시 켜야 함
    
