import React from "react";
import { Container, Nav, NavDropdown, Navbar } from "react-bootstrap";

import { useSelector, useDispatch } from "react-redux";
import { logoutUser } from "../actions/authActions";
import { NavLink } from "react-router-dom";

export default function NavbarLogged() {
    const user = useSelector((state) => state.auth.user);
    const dispatch = useDispatch();

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
                            <NavDropdown.Item as={NavLink} to={"/posts"}>Posts</NavDropdown.Item>
                            <NavDropdown.Item onClick={() => dispatch(logoutUser())} >Logout</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}
