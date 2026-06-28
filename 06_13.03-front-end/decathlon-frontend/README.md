# Kümnevõistluse front-end

Lihtne React + TypeScript (Vite) front-end `decathlon` back-endile (tehtud kausta `03_20.02-klassikomplekt/decathlon`).

Kaetud back-end endpointid:
- `GET /athletes` – sportlaste nimekiri
- `POST /athletes` – uue sportlase lisamine
- `POST /athletes/{id}/results` – sportlasele tulemuse lisamine
- `GET /athletes/{id}/results/sum` – sportlase punktide kogusumma

## Käivitamine

1. Käivita back-end (`decathlon` projekt) pordil 8080.
2. Selles kaustas:
   ```
   npm install
   npm run dev
   ```
3. Ava brauseris näidatud aadress (vaikimisi http://localhost:5173).

Kui back-end annab CORS vea, lisa `AthleteController` klassi peale `@CrossOrigin(origins = "*")`.
