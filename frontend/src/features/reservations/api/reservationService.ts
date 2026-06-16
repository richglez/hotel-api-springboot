import type {IReservation} from "../types/models/Reservation.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";
import apiClient from "../../../api/apiClient.ts";
import type {AxiosError} from "axios";
import type { Page } from '../../../shared/types/Page.ts';

// Helper: extrae el status del error de axios y lanza el mensaje correcto
const throwServiceError = (
    error: unknown,
    getErrorMessage: (status: number) => string
): never => {
    const status = (error as AxiosError)?.response?.status ?? 500;
    throw new Error(getErrorMessage(status));
};

const reservationService = {
    getAll: async (): Promise<IReservation[]> => {
        try {
            const res = await apiClient.get<Page<IReservation> | IReservation[]>("/reservations");
            return (res.data as Page<IReservation>).content ?? (res.data as IReservation[]); // soporta paginado y array simple
        } catch (error) {
            return throwServiceError(error, getGeneralError);
        }
    },

    getById: async (id: number): Promise<IReservation> => {
        try {
            const res = await apiClient.get<IReservation>(`/reservations/${id}`);
            return res.data;
        } catch (error) {
            return throwServiceError(error, getGeneralError);
        }
    },

    create: async (reservation: ICreateReservation): Promise<IReservation> => {
        try {
            const res = await apiClient.post<IReservation>("/reservations", reservation);
            return res.data;
        } catch (error) {
            return throwServiceError(error, getCreateError);
        }
    },

    update: async (id: number, reservation: IReservation): Promise<IReservation> => {
        try {
            const res = await apiClient.put<IReservation>(`/reservations/${id}`, reservation);
            return res.data;
        } catch (error) {
            return throwServiceError(error, getUpdateError);
        }
    },

    delete: async (id: number): Promise<void> => {
        try {
            await apiClient.delete(`/reservations/${id}`);
        } catch (error) {
            return throwServiceError(error, getGeneralError);
        }
    }
};

// ── Mensajes por operación (sin cambios) ──────────────────
function getCreateError(status: number): string {
    switch (status) {
        case 400: return "Invalid reservation data.";
        case 409: return "Room is not available for the selected dates.";
        case 422: return "Please verify check-in, check-out and guest details.";
        default:  return "Failed to create reservation. Please try again.";
    }
}

function getUpdateError(status: number): string {
    switch (status) {
        case 400: return "Invalid update data.";
        case 404: return "Reservation not found.";
        case 409: return "Room is no longer available for the new dates.";
        default:  return "Failed to update reservation. Please try again.";
    }
}

function getGeneralError(status: number): string {
    switch (status) {
        case 401: return "Session expired. Please log in again.";
        case 403: return "You don't have permission to perform this action.";
        case 404: return "Reservation not found.";
        default:  return "Something went wrong. Please try again.";
    }
}

export default reservationService;