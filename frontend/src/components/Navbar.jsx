import React from "react";
import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav style={{ padding: "1rem", backgroundColor: "#282c34", color: "white" }}>
      <Link to="/" style={{ marginRight: "1rem", color: "white" }}>Inicio</Link>
      <Link to="/tools" style={{ marginRight: "1rem", color: "white" }}>Inventario</Link>
      <Link to="/loans" style={{ marginRight: "1rem", color: "white" }}>Préstamos</Link>
      <Link to="/clients" style={{ marginRight: "1rem", color: "white" }}>Clientes</Link>
      <Link to="/rates" style={{ marginRight: "1rem", color: "white" }}>Tarifas</Link>
      <Link to="/movements" style={{ marginRight: "1rem", color: "white" }}>Movimientos</Link>
      <Link to="/reports" style={{ marginRight: "1rem", color: "white" }}>Reportes</Link>
      <Link to="/users" style={{ color: "white" }}>Usuarios</Link>
    </nav>
  );
}