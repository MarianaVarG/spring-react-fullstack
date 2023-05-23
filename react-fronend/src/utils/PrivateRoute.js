import React from "react";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";


const PrivateRoute = ({ children }) => {
    const loggedIn = useSelector((state) => state.auth.loggedIn);
  
    return loggedIn ? children : <Navigate to="/signin" /> 
    
};

export default PrivateRoute;

