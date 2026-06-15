import {useState, type ReactNode, useCallback, useEffect} from "react";
import {AuthContext} from "./AuthContext.tsx";
import {setupInterceptors} from "../../../api/apiClient.ts";
import {isTokenExpired} from "../utils/tokenUtils.ts";

export const AuthProvider = ({children}: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(() => {
        const stored = localStorage.getItem("token");
        if (isTokenExpired(stored)) {
            localStorage.removeItem("token");
            return null;
        }
        return stored;
    });

    const login = (newToken: string) => {
        localStorage.setItem("token", newToken);
        setToken(newToken);
    };

    const logout = useCallback(() => {
        setToken(null);
        localStorage.removeItem("token");
    }, []);

    useEffect(() => {
        setupInterceptors(logout);
    }, [logout]);

    return (
        <AuthContext.Provider value={{token, isAuthenticated: Boolean(token), login, logout}}>
            {children}
        </AuthContext.Provider>
    );
};