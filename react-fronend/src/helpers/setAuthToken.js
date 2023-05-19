import axios from "axios";

const setAuthToken = token => {
    if(token) {
        // Setting authorization token
        axios.defaults.headers.common["Authorization"] = token;
    } else {
        // Delete property Authorization
        delete axios.defaults.headers.common["Authorization"];
    }
}

export default setAuthToken;