FROM node:18-alpine

WORKDIR /app

# Copier les fichiers de configuration
COPY package.json package-lock.json ./

# Installer les dépendances et Angular CLI globalement
RUN npm install && npm install -g @angular/cli

# Copier le reste du code source
COPY . .

# Exposer le port utilisé par le serveur de développement Angular
EXPOSE 4200

# Lancer le serveur de développement Angular
CMD ["ng", "serve", "--host", "0.0.0.0"]
