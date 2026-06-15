import {createContext, useState, type ReactNode, useCallback, useEffect} from "react";
import {setupInterceptors} from "../../../api/apiClient.ts";
import {isTokenExpired} from "../utils/tokenUtils.ts";

// 1. molde
type AuthContextType = {
    token: string | null; // ¿hay token?
    isAuthenticated: boolean; // ¿el usuario está autenticado?
    login: (token: string) => void; // ¿cómo hago login?
    logout: () => void; // ¿cómo hago logout?
};

// 2. crear contexto de react de tipo authcontexttype o null, por defecto null
export const AuthContext = createContext<AuthContextType | null>(null);

// 3. Creamos el componente Provider
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
        setToken(newToken)
    }

    const logout = useCallback(() => {
        setToken(null);
        localStorage.removeItem("token");
    }, []);


    useEffect(() => {
        setupInterceptors(logout);
    }, [logout]);

    return (
        <AuthContext.Provider
            value={{
                token,
                isAuthenticated: Boolean(token),
                login,
                logout
            }}>
            {children}
        </AuthContext.Provider>
    )
}