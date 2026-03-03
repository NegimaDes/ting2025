import React, { useEffect, useState } from "react";
import axios from "axios";

export default function ReportList() {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/reports/clients/overdue")
      .then(res => setClients(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Clientes con Préstamos Atrasados</h2>
      <ul>
        {clients.map(c => (
          <li key={c.id}>{c.name} - {c.email}</li>
        ))}
      </ul>
    </div>
  );
}