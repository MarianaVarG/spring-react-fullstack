import React, { useState, useEffect } from 'react'
import validator from 'validator';

import { Container, Row, Col, Card, Alert } from 'react-bootstrap';
import SignUpForm from '../components/forms/SingUpForm';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { isObjectEmpty } from '../helpers/helpers';
import { useNavigate } from 'react-router-dom';

import { registerUser, loginUser } from '../actions/authActions';

export default function SignUp() {
    const [errors, setErrors] = useState({}) // Empty object
    const dispatch = useDispatch();

    /* Getting the state 
    state.auth because we call auth the reducer in the index
    */
    const loggedIn = useSelector(state => state.auth.loggedIn)
    const navigate = useNavigate();

    useEffect(() => {
        // Mount de component
        if (loggedIn) {
            // Redirect to main page
            navigate("/");
        }
    });

    const register = ({ email, password, firstName, lastName }) => {
        const errors = {};
        // Validate fields
        if (!validator.isEmail(email)) {
            errors.email = "Email is invalid";
        }
        if (!validator.isLength(password, { min: 8, max: 30 })) {
            errors.password = "Password invalid"
        }
        if (validator.isEmpty(firstName)) {
            errors.firstName = "First Name is mandatory. ";
        }
        if (validator.isEmpty(lastName)) {
            errors.lastName = "Last Name is mandatory";
        }
        if (!isObjectEmpty(errors)) {
            setErrors(errors);
            return;
        }

        // Call us login function that we create on authActions
        dispatch(registerUser({ email, password, firstName, lastName }))
             .then(response => {
                // Valid reponse: loggin user
                dispatch(loginUser({ email, password }))
             })
             .catch(err => {
                 // Error messages when user is not logged
                 setErrors({ registerError: err.response.data.message });
                 console.log(err.response.data.message);
             });
    }


    return (
        // Margin-Top = 5
        <Container className='mt-5'>
            <Row>
                <Col sm='12' md={{ span: 8, offset: 2 }} lg={{ span: 6, offset: 3 }}>
                    <Card className='shadow'>
                        <Card.Body>
                            {errors.registerError && <Alert variant='danger'>{errors.registerError}</Alert>}

                            <h3 className='fw-bold mb-2 text-uppercase'>SingUp</h3>
                            <hr></hr>

                            <SignUpForm errors={errors} onSubmitCallback={register}></SignUpForm>

                            <div className='mt-3'>
                                <p className='mb-0 text-center'>
                                    Do you have a account?{" "}
                                    <Link to={"/singin"}>SingIn</Link>
                                </p>
                            </div>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}
