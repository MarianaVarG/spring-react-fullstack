import React from 'react'
import { Nav, NavDropdown, Navbar } from 'react-bootstrap'

export default function Navigation() {
  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Navbar.Brand>React Java</Navbar.Brand>

      <Navbar.Toggle aria-controls='main-menu'></Navbar.Toggle>

      <Navbar.Collapse id='main-menu'>
        <Nav className='me-auto'>
          <Nav.Link>Create Post</Nav.Link>
        </Nav>

        <Nav className='justify-content-end'>
            <Nav.Link>Create account</Nav.Link>

            <Nav.Link>Login</Nav.Link>

            <NavDropdown title='Mariana Varela' id='menu-dropdown'>
              <NavDropdown.Item>Posts</NavDropdown.Item>
              <NavDropdown.Item>Logout</NavDropdown.Item>
            </NavDropdown>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  )
}
