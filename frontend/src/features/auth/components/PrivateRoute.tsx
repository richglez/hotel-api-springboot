import {Navigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.tsx";
import type {ReactNode} from "react";

const PrivateRoute = ({children}: { children: ReactNode }) => {
    const {isAuthenticated} = useAuth();
    return isAuthenticated ? children : <Navigate to = "/login"
    replace />;

};
// cualquier usuario no autenticado es redirigido al login antes de que el componente monte y haga el fetch.

export default PrivateRoute;