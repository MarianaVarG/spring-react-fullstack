import React, { useState } from "react";
import { Form, Button } from "react-bootstrap";

export default function SignInForm({ errors, onSubmitCallback }) {
    // Email and password setters
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    // Function used when submitting the form
    const submitForm = (e) => {
        e.preventDefault();
        // Send email and password to the father
        onSubmitCallback({ email, password });
    }

    // Form with bootstrap
    return (
        // onSubmit call submitForrm function
        <Form onSubmit={submitForm}>
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
                    Login
                </Button>
            </div>
        </Form>
    )

}