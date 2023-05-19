import { LOGIN_ENDPOINT } from "../helpers/endpoints";
import { SET_CURRENT_USER } from "./types";
import setAuthToken from "../helpers/setAuthToken";
import axios from "axios";
import jwtDecode from "jwt-decode";

export const loginUser = (userData) => dispatch => {
    return new Promise((resolve, reject) => {
        //Post request = (endpoint, data, headers)
        axios.post(LOGIN_ENDPOINT, userData, {
            headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
        }).then(response => {
            // Petitions is resolved
            console.log(response);

            const { authorization, userId } = response.headers;
            localStorage.setItem("jwtToken", authorization);

            // Functionto add axios token
            setAuthToken(authorization)

            // Decode token from clien side
            const decode = jwtDecode(authorization);
            dispatch(setAuthToken({ user: decode, loggedIn: true }));

            resolve(response);
        }).catch(error => {
            reject(error);
        })
    })

}