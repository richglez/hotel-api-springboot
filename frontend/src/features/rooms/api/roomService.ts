import type {IRoom} from '../types/models/Room.ts'

const BASE_URL = "http://localhost:8080/api/rooms";

const getAuthHeaders = () => {
    const token = sessionStorage.getItem("Authorization");

    return {"Content-Type": "application/json", "Authorization": token || "",}
}

const roomsService = {
    getAll: async () => {
        const res = await fetch(BASE_URL)
        if (!res.ok) throw new Error("Error al obtener habitaciones")
        return res.json();
    },

    getById: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`)
        if (!res.ok) throw new Error("Habitacion no encontrada")
        return res.json();
    },

    create: async (room: IRoom) => {
        const res = await fetch(BASE_URL, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(room)
        })
        if (!res.ok) throw new Error("Error al crear habitacion")
        return res.json()
    },

    update: async (id: number, room: IRoom) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(room)
        })
        if (!res.ok) throw new Error("Error al actualizar datos de habitacion")
        return res.json();
    },

    delete: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });
        if (!res.ok) throw new Error("Error al eliminar habitacion");
        return res.json();
    }
}

export default roomsService;