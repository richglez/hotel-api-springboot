import type {RegisterRequest} from "../types/RegisterRequest.ts";
import type {LoginRequest} from "../types/LoginRequest.ts";
import type {AuthResponse} from "../types/AuthResponse.ts";

const BASE_URL = "http://localhost:8080/api/auth";

// Helper reutilizable para capturar respuestas de error del servidor;
async function handleErrorResponse(res: Response, getError: (status: number) => string): Promise<never> {
    const body = await res.json().catch(() => null) // quiero la respuesta del servidor en body -> res.json y si hay una exception -> .catch() en esta respuesta (no mando un JSON valido o no mando nada o mando un texto/html plano) regresa un nulo a esa variable enves de tumbar toda la app
    const message = body?.message ?? getError(res.status); // body -> undefined o un message, ?? nullish coalescing
    throw new Error(message);
}

const authService = {
    register: async (data: RegisterRequest): Promise<AuthResponse> => {
        const res = await fetch(`${BASE_URL}/register`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (!res.ok) {
            await handleErrorResponse(res, getRegisterError);
        }

        return res.json();
    },
    login: async (data: LoginRequest): Promise<AuthResponse> => {
        const res = await fetch(`${BASE_URL}/login`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (!res.ok) {
            await handleErrorResponse(res, getLoginError);
        }

        return res.json();
    }
}


// fallback login
function getLoginError(status: number): string {
    switch (status) {
        case 401:
            return "Invalid email or password.";
        case 403:
            return "Your account doesn't  have access.";
        case 404:
            return "No account found with that email";
        default:
            return "Login failed. Please try again.";
    }
}

// fallback register
function getRegisterError(status: number): string {
    switch (status) {
        case 409:
            return "An account with that email already exists.";
        case 422:
            return "Invalid registration data.";
        default:
            return "Registration failed. Please try again.";
    }
}


export default authService;