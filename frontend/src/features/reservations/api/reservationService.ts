import type {IReservation} from "../types/models/Reservation.ts";

const BASE_URL = "http://localhost:8080/api/reservations" // url backend

const getAuthHeaders = () => {
    const token = sessionStorage.getItem("Authorization");
    return {"Content-Type": "application/json", "Authorization": token || ""};
}

const reservationService = {
    getAll: async () => {
        const res = await fetch(BASE_URL);
        if (!res.ok) throw new Error('Failed to get reservations');
        return res.json();
    },

    getById: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            headers: getAuthHeaders()
        })
        if (!res.ok) throw new Error(`Failed to get reservation`)
        return res.json();
    },

    create: async () => {
        const res = await fetch(BASE_URL, {
            headers: getAuthHeaders()
        })
        if (!res.ok) throw new Error('Failed creating reservation');
        return res.json();
    },

    update: async (id: number, reservation: IReservation) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(reservation)
        })
        if (!res.ok) throw new Error('Failed updating reservation');
        return res.json();
    },

    delete: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders()
        })
        if(!res.ok) throw new Error('Failed deleting reservation')
        return res.json();
    }


}

export default reservationService;