import type {IReservation} from "../types/models/Reservation.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";
import {handleErrorResponse} from "../../../shared/utils/apiError.ts";

const BASE_URL = "http://localhost:8080/api/reservations" // url backend

const getAuthHeaders = () => {
    const token = localStorage.getItem("token"); // ← corregido: localStorage, no sessionStorage
    return {
        "Content-Type": "application/json",
        "Authorization": token ? `Bearer ${token}` : "",
    };
};

const reservationService = {
    getAll: async () => {
        const res = await fetch(BASE_URL);
        if (!res.ok) await handleErrorResponse(res, getGeneralError)
        return res.json();
    },

    getById: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            headers: getAuthHeaders()
        })
        if (!res.ok) await handleErrorResponse(res, getGeneralError);
        return res.json();
    },

    create: async (reservation: ICreateReservation) => {
        const res = await fetch(BASE_URL, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(reservation)
        })
        if (!res.ok) await handleErrorResponse(res, getCreateError);
        return res.json();
    },

    update: async (id: number, reservation: IReservation) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(reservation)
        })
        if (!res.ok) await handleErrorResponse(res, getUpdateError);
        return res.json();
    },

    delete: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders()
        })
        if (!res.ok) await handleErrorResponse(res, getGeneralError);
        return res.json();
    }
}

// ── Fallbacks por operación ──────────────────────────────
function getCreateError(status: number): string {
    switch (status) {
        case 400:
            return "Invalid reservation data.";
        case 409:
            return "Room is not available for the selected dates.";
        case 422:
            return "Please verify check-in, check-out and guest details.";
        default:
            return "Failed to create reservation. Please try again.";
    }
}

function getUpdateError(status: number): string {
    switch (status) {
        case 400:
            return "Invalid update data.";
        case 404:
            return "Reservation not found.";
        case 409:
            return "Room is no longer available for the new dates.";
        default:
            return "Failed to update reservation. Please try again.";
    }
}

function getGeneralError(status: number): string {
    switch (status) {
        case 401:
            return "Session expired. Please log in again.";
        case 403:
            return "You don't have permission to perform this action.";
        case 404:
            return "Reservation not found.";
        default:
            return "Something went wrong. Please try again.";
    }
}

export default reservationService;