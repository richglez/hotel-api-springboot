import {useEffect, useState} from "react";
import clientService from "../api/clientService.ts";
import type {IClient} from "../types/models/Client.ts";


function ClientList() {
    const [clients, setClients] = useState<IClient[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        clientService.getAll()
            .then(setClients)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false)) // termino de cargar los datos
    }, []);

    if (loading) return <p>Cargando...</p>
    if (error) return <p>Error: {error}</p>

    return (
        <ul>
            {clients.map(client => (
                <li key={client.id}>{client.name}</li>
            ))}
        </ul>
    )
}

export default ClientList;