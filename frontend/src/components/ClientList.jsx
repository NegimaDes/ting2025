import React, { useEffect, useState } from "react";
import axios from "axios";

export default function ClientList() {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/clients")
      .then(res => setClients(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Clientes</h2>
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th><th>Nombre</th><th>Email</th><th>Teléfono</th><th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {clients.map(client => (
            <tr key={client.id}>
              <td>{client.id}</td>
              <td>{client.name}</td>
              <td>{client.email}</td>
              <td>{client.phone}</td>
              <td>{client.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}