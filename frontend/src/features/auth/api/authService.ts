import type {RegisterRequest} from "../types/RegisterRequest.ts";
import type {LoginRequest} from "../types/LoginRequest.ts";
import type {AuthResponse} from "../types/AuthResponse.ts";

const BASE_URL = "http://localhost:8080/api/auth";

const authService = {
    register: async (data: RegisterRequest): Promise<AuthResponse> => {
        const res = await fetch(`${BASE_URL}/register`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (!res.ok) {
            const message = await res.text();
            throw new Error(message);
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
            const message = await res.text();
            throw new Error(message);
        }
        return res.json();
    }
}

export default authService;