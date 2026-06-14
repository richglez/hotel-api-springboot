// src/api/apiClient.ts
import axios from "axios";

const apiClient = axios.create({
    baseURL: "http://localhost:8080/api",
});

// Función para inyectar el header y el logout desde afuera
let _logout: (() => void) | null = null; // esto puede ser una funcion o un null, su valor inicial null

export const setupInterceptors = (logout: () => void) => {
    _logout = logout;
};

// Request: adjunta el token automáticamente
apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Response: si el server devuelve 401 → logout
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            _logout?.();
        }
        return Promise.reject(error);
    }
);

export default apiClient;