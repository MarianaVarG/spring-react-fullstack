import React from "react";
import { Container, Nav, NavDropdown, Navbar } from "react-bootstrap";

import { useSelector } from "react-redux";

export default function NavbarLogged() {
    const user = useSelector((state) => state.auth.user);

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Toggle aria-controls="main-menu"></Navbar.Toggle>

                <Navbar.Collapse id="main-menu">
                    <Nav className="me-auto">
                        <Nav.Link>Create Post</Nav.Link>
                    </Nav>

                    <Nav className="justify-content-end">
                        <NavDropdown title={user.sub} id="menu-dropdown">
                            <NavDropdown.Item>Posts</NavDropdown.Item>
                            <NavDropdown.Item>Logout</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
