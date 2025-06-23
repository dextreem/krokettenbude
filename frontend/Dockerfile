# Stage 1: Build the app
FROM docker.io/node:20-alpine AS builder

ARG VITE_API_BASE_PATH
ENV VITE_API_BASE_PATH=$VITE_API_BASE_PATH

WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .

RUN npm run build

FROM docker.io/nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
