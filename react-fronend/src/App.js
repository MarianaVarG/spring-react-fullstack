import "bootstrap/dist/css/bootstrap.min.css";

import Navigation from "./layouts/Navigation";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Container } from "react-bootstrap";
import { Provider } from "react-redux";

import SignIn from "./pages/SignIn";
import Home from "./pages/Posts";
import store from "./store";
import checkForToken from "./helpers/checkForToken";
import PrivateRoute from "./utils/PrivateRoute";
import UserPosts from "./pages/UserPosts";
import SignUp from "./pages/SingUp";

checkForToken();

function App() {
  return (
    <Provider store={store}>
      <Router>
        <Navigation />

        <Container>
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/signin" element={<SignIn />} />
            <Route exact path="/signup" element={<SignUp />} />
            <Route exact path="/posts" element={<PrivateRoute > <UserPosts /> </PrivateRoute>} />
          </Routes>
        </Container>
      </Router>
    </Provider>
  );
}

export default App;
