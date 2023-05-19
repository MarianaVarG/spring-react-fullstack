import "bootstrap/dist/css/bootstrap.min.css";

import Navigation from "./layouts/Navigation";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Container } from "react-bootstrap";
import { Provider } from "react-redux";

import SignIn from "./pages/SignIn";
import Home from "./pages/Posts";
import store from "./store";


function App() {
  return (
    <Provider store={store}>
      <Router>
        <Navigation />

        <Container>
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/signin" element={<SignIn />} />
          </Routes>
        </Container>
      </Router>
    </Provider>
  );
}

export default App;
