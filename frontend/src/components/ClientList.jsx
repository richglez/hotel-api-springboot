import {useEffect, useState} from "react";
import {clientService} from "../services/clientService.js";

function ClientList() {
    const [clients, setClients] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        clientService.getAll()
            .then(setClients)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false)) // termino de cargar los datos
    }, []);

    if (loading) return <p>Cargando...</p>
    if (error) return <p>Error: {error}</p>
}

export default ClientList