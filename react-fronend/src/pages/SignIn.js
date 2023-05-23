import React, { useState, useEffect } from 'react'
import validator from 'validator';

import { Container, Row, Col, Card, Alert } from 'react-bootstrap';
import SignInForm from '../components/forms/SingInForm';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { isObjectEmpty } from '../helpers/helpers';
import { useNavigate } from 'react-router-dom';

import { loginUser } from '../actions/authActions';

export default function SignIn() {
  const [errors, setErrors] = useState({}) // Empty object
  const dispatch = useDispatch();

  /* Getting the state 
  state.auth because we call auth the reducer in the index
  */
  const loggedIn = useSelector(state => state.auth.loggedIn)
  const navigate = useNavigate();

  useEffect(() =>{
    // Mount de component
    if (loggedIn) {
      // Redirect to main page
      navigate("/");
    }
  });

  const login = ({email, password}) => {
    const errors = {};
    // Validate fields
    if (!validator.isEmail(email)) {
      errors.email = "Email is invalid";
    }
    if (validator.isEmpty(password)) {
      errors.password = "Empty password"
    }
    if (!isObjectEmpty(errors)) {
      setErrors(errors);
      return;
    }

    // Call us login function that we create on authActions
    dispatch(loginUser({email, password}))
    .then(response =>{

    })
    .catch(err =>{
      // Error messages when user is not logged
      setErrors({ auth: "Cannot log in with these credentials" });
    });
  }


  return (
    // Margin-Top = 5
    <Container className='mt-5'>
      <Row>
        <Col sm='12' md={{span: 8, offset: 2}} lg={{span: 6, offset: 3}}>
          <Card className='shadow'>
            <Card.Body>
              {errors.auth && <Alert variant='danger'>{ errors.auth }</Alert>}

              <h3 className='fw-bold mb-2 text-uppercase'>Login</h3>
              <hr></hr>

              <SignInForm errors={errors} onSubmitCallback={login}></SignInForm>

              <div className='mt-3'>
                <p className='mb-0 text-center'>
                  Don't have a account?{" "}
                  <Link to={"/sinup"}>SingUp</Link>
                </p>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  )
}
