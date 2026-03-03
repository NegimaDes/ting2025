import React, { useEffect, useState } from "react";
import axios from "axios";

export default function RateConfig() {
  const [rates, setRates] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:8080/rates")
      .then(res => setRates(res.data))
      .catch(err => console.error(err));
  }, []);

  if (!rates) return <p>Cargando tarifas...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Configuración de Tarifas</h2>
      <ul>
        <li>Tarifa diaria: ${rates.dailyRentalFee}</li>
        <li>Multa diaria: ${rates.dailyLateFee}</li>
        <li>Valor de reemplazo: ${rates.defaultReplacementValue}</li>
      </ul>
    </div>
  );
}