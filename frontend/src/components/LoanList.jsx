import React, { useEffect, useState } from "react";
import axios from "axios";

export default function LoanList() {
  const [loans, setLoans] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/reports/loans/active")
      .then(res => setLoans(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Préstamos Activos</h2>
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th><th>Cliente</th><th>Herramienta</th><th>Entrega</th><th>Devolución Esperada</th>
          </tr>
        </thead>
        <tbody>
          {loans.map(loan => (
            <tr key={loan.id}>
              <td>{loan.id}</td>
              <td>{loan.client?.name}</td>
              <td>{loan.tool?.name}</td>
              <td>{loan.deliveryDate}</td>
              <td>{loan.expectedReturnDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}