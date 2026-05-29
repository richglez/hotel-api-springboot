import type {RegisterRequest} from "../types/RegisterRequest.ts";
import type {LoginRequest} from "../types/LoginRequest.ts";
import type {AuthResponse} from "../types/AuthResponse.ts";
import {handleErrorResponse} from "../../../shared/utils/apiError.ts";

const BASE_URL = "http://localhost:8080/api/auth";

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