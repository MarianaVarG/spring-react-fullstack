import React, { useState } from "react";
import { Form, Button, Row, Col } from "react-bootstrap";

export default function SignUpForm({ errors, onSubmitCallback }) {
    // Email and password setters
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');

    // Function used when submitting the form
    const submitForm = (e) => {
        e.preventDefault();
        // Send email and password to the father
        onSubmitCallback({ email, password, firstName, lastName });
    }

    // Form with bootstrap
    return (
        // onSubmit call submitForrm function
        <Form onSubmit={submitForm}>
            <Row>
                <Col md="6" xs="12">
                    <Form.Group controlId="firstName" className="mb-3">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            placeholder="First Name"
                            isInvalid={errors.firstName} // When errors.email is not empty, meand there are and error
                        />
                        {/* Displayed when an email validation error occurs */}
                        <Form.Control.Feedback type="invalid">
                            {errors.firstName}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col md="6" xs="12">
                    <Form.Group controlId="lastName" className="mb-3">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            placeholder="Last Name"
                            isInvalid={errors.lastName} // When errors.email is not empty, meand there are and error
                        />
                        {/* Displayed when an email validation error occurs */}
                        <Form.Control.Feedback type="invalid">
                            {errors.lastName}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
            </Row>


            <Form.Group controlId="email" className="mb-3">
                <Form.Label>Email</Form.Label>

                <Form.Control
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="email@example.com"
                    isInvalid={errors.email} // When errors.email is not empty, meand there are and error
                />
                {/* Displayed when an email validation error occurs */}
                <Form.Control.Feedback type="invalid">
                    {errors.email}
                </Form.Control.Feedback>
            </Form.Group>

            <Form.Group controlId="password" className="mb-3">
                <Form.Label>Password</Form.Label>

                <Form.Control
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    isInvalid={errors.password} // When errors.email is not empty, meand there are and error
                />
                {/* Displayed when an password validation error occurs */}
                <Form.Control.Feedback type="invalid">
                    {errors.password}
                </Form.Control.Feedback>
            </Form.Group>

            <div className="d-grid">
                <Button variant="outline-primary" type="submit">
                    SingUp
                </Button>
            </div>
        </Form>
    )

}