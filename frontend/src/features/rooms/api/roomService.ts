import type {IRoom} from '../types/models/Room.ts';
import apiClient from "../../../api/apiClient.ts";

// BASE_URL ya no hace falta — apiClient tiene baseURL configurado
// getAuthHeaders ya no hace falta — el interceptor lo maneja

const roomsService = {
    getAll: async (): Promise<IRoom[]> => {
        const res = await apiClient.get<any>("/rooms");
        return res.data.content ?? res.data; // soporta paginado y array simple
    },

    getById: async (id: number): Promise<IRoom> => {
        const res = await apiClient.get<IRoom>(`/rooms/${id}`);
        return res.data;
    },

    create: async (room: IRoom): Promise<IRoom> => {
        const res = await apiClient.post<IRoom>("/rooms", room);
        return res.data;
    },

    update: async (id: number, room: IRoom): Promise<IRoom> => {
        const res = await apiClient.put<IRoom>(`/rooms/${id}`, room);
        return res.data;
    },

    delete: async (id: number): Promise<void> => {
        await apiClient.delete(`/rooms/${id}`);
    }
};

export default roomsService;