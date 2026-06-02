import {useState, useEffect} from "react";
import type {IRoom} from "../types/models/Room.ts";
import roomService from "../api/roomService.ts";

interface UseRoomsReturn {
    rooms: IRoom[];
    loading: boolean;
    error: string | null
}

const useRooms = (): UseRoomsReturn => {
    const [rooms, setRooms] = useState<IRoom[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        roomService.getAll()
            .then(setRooms)
            .catch((err: Error) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    return {rooms, loading, error}
};

export default useRooms;
