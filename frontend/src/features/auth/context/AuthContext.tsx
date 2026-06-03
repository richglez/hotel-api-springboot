import {createContext, useContext, useState, type ReactNode, useCallback, useEffect} from "react";
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
const AuthContext = createContext<AuthContextType | null>(null);

// 3. Creamos el componente Provider
export const AuthProvider = ({children}: { children: ReactNode }) => {
    const [token, setToken] = useState<string | null>(() => {
        return localStorage.getItem("token")
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
        if (isTokenExpired(token)) {
            logout();
        }
    }, [token, logout]);

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

// custom hook useContext -> permite usar esos datos desde cualquier componente
export const useAuth = () => {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used inside AuthProvider")
    }

    return context;
}