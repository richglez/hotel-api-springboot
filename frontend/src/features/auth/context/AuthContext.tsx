import {createContext, useContext, useState, type ReactNode} from "react";

type AuthContextType = {
    token: string | null;
    isAuthenticated: boolean;
    login: (token: string) => void;
    logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);



// El objeto debe tener una propiedad children que sea renderizable por React
// “del objeto recibido, saca la propiedad children
export const AuthProvider = ({children}: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(() => {
        return localStorage.getItem("token")
    });

    const login = (newToken: string) => {
        localStorage.setItem("token", newToken);
        setToken(newToken)
    }

    const logout = () => {
        localStorage.removeItem("token");
        setToken(null);
    }



    return (
        <AuthContext.Provider></AuthContext.Provider>
    )


}