FROM node:10

WORKDIR /usr/scr/app

COPY ./ ./

# express, redis 설치
RUN npm install

CMD ["node", "server.js"]