import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import ToolList from "./components/ToolList";
import LoanList from "./components/LoanList";
import ClientList from "./components/ClientList";
import RateConfig from "./components/RateConfig";
import MovementList from "./components/MovementList";
import ReportList from "./components/ReportList";
import UserList from "./components/UserList";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/tools" element={<ToolList />} />
        <Route path="/loans" element={<LoanList />} />
        <Route path="/clients" element={<ClientList />} />
        <Route path="/rates" element={<RateConfig />} />
        <Route path="/movements" element={<MovementList />} />
        <Route path="/reports" element={<ReportList />} />
        <Route path="/users" element={<UserList />} />
      </Routes>
    </Router>
  );
}

export default App;
