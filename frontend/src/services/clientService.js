const BASE_URL = "http://localhost:8080/api/clients" // url backend

export const clientService = {
    getAll: async () => {
        const res = await fetch(BASE_URL)
        if (!res.ok) throw new Error("Error al obtener clientes")
        return res.json()
    },

    getById: async (id) => {
        const res = await fetch(`${BASE_URL}/${id}`)
        if (!res.ok) throw new Error("Cliente no encontrado")
        return res.json()
    },

    create: async (client) => {
        const res = await fetch(BASE_URL, {
            method: "POST",
            headers: {"Content-Type": "application/json"}, // le dice al backend estoy enviando un json
            body: JSON.stringify(client),
        })
        if (!res.ok) throw new Error("Error al crear cliente")
        return res.json();
    },

    update: async (id, client) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "aplication/json"},
            body: JSON.stringify(client)
        })
        if (!res.ok) throw new Error("Error al actualizar cliente")
        return res.json()
    },

    remove: async (id) => {
        const res = await fetch(`${BASE_URL}/${id}`, {
            method: "DELETE"
        });
        if (!res.ok) throw new Error("Error al eliminar cliente");
    },
}