import "bootstrap/dist/css/bootstrap.min.css";

import Navigation from "./layouts/Navigation";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Container } from "react-bootstrap";

import SignIn from "./pages/SignIn";
import Home from "./pages/Posts"


function App() {
  return (
    <Router>
      <div> <Navigation /> </div>
      <Container>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route exact path="/signin" element={<SignIn />} />
        </Routes>
      </Container>
    </Router>

  );
}

export default App;
