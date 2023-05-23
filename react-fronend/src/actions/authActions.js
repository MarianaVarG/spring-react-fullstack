import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from "../helpers/endpoints";
import { SET_CURRENT_USER } from "./types";
import setAuthToken from "../helpers/setAuthToken";
import axios from "axios";
import jwt_decode from "jwt-decode";

export const loginUser = (userData) => dispatch => {
    return new Promise((resolve, reject) => {
        //Post request = (endpoint, data, headers)
        axios.post(LOGIN_ENDPOINT, userData, {
            headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
        }).then(response => {
            // Petitions is resolved
            const { authorization, userId } = response.headers;
            localStorage.setItem("jwtToken", authorization);

            // Function to add axios token
            setAuthToken(authorization)

            // Decode token from clien side
            const decode = jwt_decode(authorization);
            dispatch(setCurrentUser({ user: decode, loggedIn: true }));

            resolve(response);
        }).catch(error => {
            reject(error);
        })
    })
}

export const setCurrentUser = ({ user, loggedIn }) => {
    return {
        type: SET_CURRENT_USER,
        payload: { user, loggedIn }
    }
}

export const logoutUser = () => dispatch => {
    localStorage.removeItem("jwtToken");

    setAuthToken(false);

    dispatch(setCurrentUser({
        user: {},
        loggedIn: false
    }));
}

export const registerUser = (userData) => dispatch => {
    return new Promise((resolve, reject) => {
        //Post request = (endpoint, data, headers)
        axios.post(REGISTER_ENDPOINT, userData, {
            headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
        }).then(response => {
            // Petitions is resolved
            resolve(response);
        }).catch(error => {
            reject(error);
        })
    })
}