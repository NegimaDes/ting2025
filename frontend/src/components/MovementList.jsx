import React, { useEffect, useState } from "react";
import axios from "axios";

export default function MovementList() {
  const [movements, setMovements] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/movements")
      .then(res => setMovements(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Movimientos de Herramientas</h2>
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th><th>Herramienta</th><th>Tipo</th><th>Cantidad</th><th>Responsable</th><th>Fecha</th>
          </tr>
        </thead>
        <tbody>
          {movements.map(m => (
            <tr key={m.id}>
              <td>{m.id}</td>
              <td>{m.tool?.name}</td>
              <td>{m.type}</td>
              <td>{m.quantity}</td>
              <td>{m.responsibleUser}</td>
              <td>{m.date}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}