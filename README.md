# Mailplatform Receiver

Spring Boot webhook for receiving Mailgun inbound emails and storing them in PostgreSQL.

## Endpoint

```
POST /webhook/email
Content-Type: multipart/form-data
```

Champs Mailgun acceptés : `from`, `to`, `recipient`, `sender`, `subject`, `body-plain`, `body-html`, `stripped-text`, `timestamp`.

---

## Déploiement sur Railway

### 1. Pousser le code sur GitHub

```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/<votre-user>/<votre-repo>.git
git push -u origin main
```

### 2. Créer le projet Railway

1. Aller sur [railway.app](https://railway.app) et créer un nouveau projet.
2. Cliquer **Deploy from GitHub repo** et sélectionner votre dépôt.
3. Railway détecte automatiquement le `Dockerfile` et lance le build.

### 3. Ajouter PostgreSQL

1. Dans le projet Railway, cliquer **+ New** > **Database** > **PostgreSQL**.
2. Railway injecte automatiquement les variables d'environnement :
   - `PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`

Aucune configuration manuelle de la datasource n'est nécessaire.

### 4. Variables d'environnement Railway

Les variables suivantes sont injectées automatiquement par le plugin PostgreSQL de Railway :

| Variable     | Description              |
|--------------|--------------------------|
| `PGHOST`     | Hôte PostgreSQL (privé)  |
| `PGPORT`     | Port (5432)              |
| `PGDATABASE` | Nom de la base (`railway`)|
| `PGUSER`     | Utilisateur (`postgres`) |
| `PGPASSWORD` | Mot de passe             |
| `PORT`       | Port HTTP (injecté auto) |

---

## Configuration Mailgun

### Créer une Route Mailgun

Dans l'interface Mailgun > **Receiving** > **Create Route** :

- **Expression type** : `Match Recipient`
- **Recipient** : `votre-adresse@votre-domaine.com` (ou `.*` pour tout capturer)
- **Actions** : cocher **Forward** et entrer l'URL du webhook :

```
https://<votre-app>.up.railway.app/webhook/email
```

- **Priority** : `10`
- Cocher **Store and notify** si vous voulez une copie Mailgun en plus.

### Tester le webhook

```bash
curl -X POST https://<votre-app>.up.railway.app/webhook/email \
  -F from="expediteur@example.com" \
  -F to="destinataire@votre-domaine.com" \
  -F subject="Test webhook" \
  -F body-plain="Contenu du mail de test" \
  -F timestamp="1700000000"
```

Réponse attendue : `HTTP 200`

---

## Structure du projet

```
src/main/java/com/mailplatform/receiver/
├── MailReceiverApplication.java     # Point d'entrée Spring Boot
├── entity/
│   └── Email.java                   # Entité JPA
├── repository/
│   └── EmailRepository.java         # Repository JPA
└── controller/
    └── WebhookController.java       # POST /webhook/email
```

## Build local

```bash
./mvnw package -DskipTests
java -jar target/receiver-0.0.1-SNAPSHOT.jar
```

Nécessite les variables `PGHOST`, `PGPASSWORD` (et optionnellement les autres) dans l'environnement local.
