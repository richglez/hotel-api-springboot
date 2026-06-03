# ── Etapa 1: compilar ──────────────────────────────────
FROM node:22-alpine AS builder
WORKDIR /app

# Habilitar pnpm vía corepack (ya viene en Node 20)
RUN corepack enable && corepack prepare pnpm@latest --activate

# pnpm usa pnpm-lock.yaml, no package-lock.json
COPY package.json pnpm-lock.yaml ./
RUN pnpm install --frozen-lockfile

COPY . .
RUN pnpm run build

# ── Etapa 2: servir con nginx ──────────────────────────
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]