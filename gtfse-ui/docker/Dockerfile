FROM node:22-alpine AS build

ENV CI=true
RUN npm install -g pnpm
WORKDIR /app

COPY pnpm-lock.yaml /
COPY pnpm-workspace.yaml /
COPY package.json /
RUN pnpm install --frozen-lockfile

COPY . .
RUN pnpm build

FROM nginx:1.31.0-alpine-slim

WORKDIR /etc/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY docker/nginx.conf /etc/nginx/conf.d
COPY --from=build /app/dist/client /usr/share/nginx/html
COPY docker/.env /
COPY docker/env.sh /
COPY docker/entrypoint.sh /

RUN mkdir -p /var/cache/nginx /var/run /var/log/nginx && \
    chgrp -R 0 /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /etc/nginx && \
    chmod -R g=u /usr/share/nginx/html /var/cache/nginx /var/run /var/log/nginx /etc/nginx

RUN chmod +x /env.sh /entrypoint.sh && \
    chgrp -R 0 /env.sh /entrypoint.sh && \
    chmod -R g=u /env.sh /entrypoint.sh

EXPOSE 3000
ENTRYPOINT ["/entrypoint.sh"]