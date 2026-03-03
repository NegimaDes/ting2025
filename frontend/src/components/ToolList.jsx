import React, { useEffect, useState } from "react";
import axios from "axios";

export default function ToolList() {
  const [tools, setTools] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/tools")
      .then(res => setTools(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Inventario de Herramientas</h2>
      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Estado</th>
            <th>Stock</th>
          </tr>
        </thead>
        <tbody>
          {tools.map(tool => (
            <tr key={tool.id}>
              <td>{tool.id}</td>
              <td>{tool.name}</td>
              <td>{tool.category}</td>
              <td>{tool.status}</td>
              <td>{tool.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}