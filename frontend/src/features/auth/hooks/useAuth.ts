import {useContext} from "react";
import {AuthContext} from "../context/AuthContext";

// custom hook -> allows using auth data from any component
export const useAuth = () => {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used inside AuthProvider")
    }

    return context;
}