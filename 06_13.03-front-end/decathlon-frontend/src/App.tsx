import { useEffect, useState } from "react";
import type { Athlete } from "./models/Athlete";
import type { Page } from "./models/Page";
import "./App.css";

function App() {
  const [athletes, setAthletes] = useState<Athlete[]>([]);
  const [error, setError] = useState<string>("");

  // uue sportlase vorm
  const [newName, setNewName] = useState("");
  const [newCountry, setNewCountry] = useState("");

  // tulemuse lisamise vorm (eraldi väljad iga sportlase kohta)
  const [spordiala, setSpordiala] = useState<Record<number, string>>({});
  const [tulemus, setTulemus] = useState<Record<number, string>>({});

  // sportlaste tulemuste summad (laetakse nupuvajutusel)
  const [sums, setSums] = useState<Record<number, number>>({});

  // lehekülgede kaupa liikumine + filter + sort (lesson 10) - kõik läheb back-endi päringuparameetritesse
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [country, setCountry] = useState("");
  const [sortByPoints, setSortByPoints] = useState(false);
  const pageSize = 5;

  function loadAthletes() {
    const params = new URLSearchParams();
    params.set("page", String(page));
    params.set("size", String(pageSize));
    if (sortByPoints) {
      params.set("sort", "totalPoints,desc");
    }
    if (country.trim() !== "") {
      params.set("country", country.trim());
    }

    fetch(import.meta.env.VITE_BACK_URL + "/athletes?" + params.toString())
      .then((res) => res.json())
      .then((pageResult: Page<Athlete>) => {
        setAthletes(pageResult.content);
        setTotalPages(pageResult.totalPages);
      })
      .catch(() => setError("Sportlaste laadimine ebaõnnestus"));
  }

  useEffect(() => {
    loadAthletes();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page, country, sortByPoints]);

  function handleError(res: Response) {
    return res.json().then((body) => {
      if (!res.ok) {
        throw new Error(body.message ?? "Tundmatu viga");
      }
      return body;
    });
  }

  function addAthlete() {
    setError("");
    fetch(import.meta.env.VITE_BACK_URL + "/athletes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: newName, country: newCountry }),
    })
      .then(handleError)
      .then(() => {
        setNewName("");
        setNewCountry("");
        loadAthletes();
      })
      .catch((err) => setError(err.message));
  }

  function addResult(athleteId: number) {
    setError("");
    fetch(import.meta.env.VITE_BACK_URL + `/athletes/${athleteId}/results`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        spordiala: spordiala[athleteId] ?? "",
        tulemus: Number(tulemus[athleteId] ?? 0),
      }),
    })
      .then(handleError)
      .then(() => {
        setSpordiala((prev) => ({ ...prev, [athleteId]: "" }));
        setTulemus((prev) => ({ ...prev, [athleteId]: "" }));
        loadSum(athleteId);
        loadAthletes(); // totalPoints muutus, värskenda nimekirja
      })
      .catch((err) => setError(err.message));
  }

  function loadSum(athleteId: number) {
    fetch(import.meta.env.VITE_BACK_URL + `/athletes/${athleteId}/results/sum`)
      .then((res) => res.json())
      .then((sum) => setSums((prev) => ({ ...prev, [athleteId]: sum })));
  }

  return (
    <div>
      <h1>Kümnevõistlus</h1>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <h2>Lisa uus sportlane</h2>
      <input
        placeholder="Sportlase nimi"
        value={newName}
        onChange={(e) => setNewName(e.target.value)}
      />
      <input
        placeholder="Riik (nt Estonia)"
        value={newCountry}
        onChange={(e) => setNewCountry(e.target.value)}
      />
      <button onClick={addAthlete}>Lisa sportlane</button>

      <h2>Sportlased</h2>

      <div style={{ marginBottom: "12px" }}>
        <input
          placeholder="Filtreeri riigi järgi (nt Estonia)"
          value={country}
          onChange={(e) => {
            setPage(0);
            setCountry(e.target.value);
          }}
        />
        <label style={{ marginLeft: "12px" }}>
          <input
            type="checkbox"
            checked={sortByPoints}
            onChange={(e) => {
              setPage(0);
              setSortByPoints(e.target.checked);
            }}
          />
          Sordi punktide järgi (kõige rohkem ees)
        </label>
      </div>

      {athletes.map((athlete) => (
        <div key={athlete.id} style={{ border: "1px solid #ccc", margin: "8px 0", padding: "8px" }}>
          <strong>{athlete.name}</strong>
          {athlete.country && <span> ({athlete.country})</span>}
          <span> — kokku punkte: {athlete.totalPoints}</span>

          <div>
            <input
              placeholder="Spordiala (nt 100m)"
              value={spordiala[athlete.id] ?? ""}
              onChange={(e) =>
                setSpordiala((prev) => ({ ...prev, [athlete.id]: e.target.value }))
              }
            />
            <input
              placeholder="Tulemus"
              value={tulemus[athlete.id] ?? ""}
              onChange={(e) =>
                setTulemus((prev) => ({ ...prev, [athlete.id]: e.target.value }))
              }
            />
            <button onClick={() => addResult(athlete.id)}>Lisa tulemus</button>
          </div>

          <div>
            <button onClick={() => loadSum(athlete.id)}>Kuva punktisumma</button>
            {sums[athlete.id] !== undefined && (
              <span> Kokku punkte (results tabelist): {sums[athlete.id]}</span>
            )}
          </div>
        </div>
      ))}

      <div style={{ marginTop: "12px" }}>
        <button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
          Eelmine lehekülg
        </button>
        <span style={{ margin: "0 8px" }}>
          Lehekülg {page + 1} / {Math.max(totalPages, 1)}
        </span>
        <button disabled={page + 1 >= totalPages} onClick={() => setPage((p) => p + 1)}>
          Järgmine lehekülg
        </button>
      </div>
    </div>
  );
}

export default App;
