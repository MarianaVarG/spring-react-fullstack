import setAuthToken from "./setAuthToken";
import jwt_decode from "jwt-decode";
import store from "../store"
import { logoutUser, setCurrentUser } from "../actions/authActions";

const checkForToken = () => {
    if (localStorage.jwtToken) {
        setAuthToken(localStorage.jwtToken);

        // Decode token from clien side
        const decoded = jwt_decode(localStorage.jwtToken);
        // User our store app
        store.dispatch(setCurrentUser({
            user: decoded,
            loggedIn: true
        }));

        const currentTime = Math.floor(Date.now() / 1000) // Time is in milis

        if (decoded.exp < currentTime) {
            store.dispatch(logoutUser());
            window.location.href = "/singin";
        }
    }
}

export default checkForToken;