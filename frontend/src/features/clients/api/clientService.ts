import type {IClient} from "../types/models/Client.ts";

const BASE_URL = "http://localhost:8080/api/clients" // url backend

const getAuthHeaders = () => {
    const token = localStorage.getItem("token");

    return {
        "Content-Type": "application/json",
        "Authorization": token ? `Bearer ${token}` : "",
    }
}

const clientService = {
    getAll: async () => {
        const res = await fetch(BASE_URL, {
            headers: getAuthHeaders()
        })
        if (!res.ok) throw new Error("Error al obtener clientes")
        return res.json()
    },

    getById: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            headers: getAuthHeaders()
        })
        if (!res.ok) throw new Error("Cliente no encontrado")
        return res.json()
    },

    create: async (client: IClient) => {
        const res = await fetch(BASE_URL, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(client),
        })
        if (!res.ok) throw new Error("Error al crear cliente")
        return res.json();
    },

    update: async (id: number, client: IClient) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(client)
        })
        if (!res.ok) throw new Error("Error al actualizar cliente")
        return res.json()
    },

    remove: async (id: number) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders(),
        });
        if (!res.ok) throw new Error("Error al eliminar cliente");
    },
}

export default clientService;