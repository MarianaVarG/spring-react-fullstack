import React from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import { NavLink } from "react-router-dom";

export default function NavbarAnonymous() {

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>

                <Navbar.Toggle aria-controls="main-menu"></Navbar.Toggle>

                <Navbar.Collapse id="main-menu">

                    <Nav className="justify-content-end">
                        <Nav.Link as={NavLink} to={"/signup"}> Sing Up </Nav.Link>

                        <Nav.Link as={NavLink} to={"/signin"}> Login </Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
