import React from 'react'
import { Container, Nav, NavDropdown, Navbar } from 'react-bootstrap'
import { NavLink } from 'react-router-dom'

export default function Navigation() {
  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container>
        <Navbar.Brand as={NavLink} to={'/'}>React Java</Navbar.Brand>

        <Navbar.Toggle aria-controls='main-menu'></Navbar.Toggle>

        <Navbar.Collapse id='main-menu'>
          <Nav className='me-auto'>
            <Nav.Link>Create Post</Nav.Link>
          </Nav>

          <Nav className='justify-content-end'>
            <Nav.Link>Sing Up</Nav.Link>

            <Nav.Link as={NavLink} to={'/SignIn'}>Login</Nav.Link>

            <NavDropdown title='Mariana Varela' id='menu-dropdown'>
              <NavDropdown.Item>Posts</NavDropdown.Item>
              <NavDropdown.Item>Logout</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>

      </Container>
    </Navbar>
  )
}
