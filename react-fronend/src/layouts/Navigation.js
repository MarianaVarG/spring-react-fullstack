import React from "react";
import { Container, Navbar } from "react-bootstrap";
import { NavLink } from "react-router-dom";

import { useSelector } from "react-redux";
import NavbarLogged from "../components/NavbarLogged";
import NavbarAnonymous from "../components/NavbarAnonymous";

export default function Navigation() {
  const loggedIn = useSelector((state) => state.auth.loggedIn);

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container>
        <Navbar.Brand as={NavLink} to={"/"}>
          React Java
        </Navbar.Brand>

          
            {/* If user is not logged or is it */}
            {!loggedIn ? (
              <NavbarAnonymous />
            ) : (
              <NavbarLogged />
            )}
      </Container>
    </Navbar>
  );
}
