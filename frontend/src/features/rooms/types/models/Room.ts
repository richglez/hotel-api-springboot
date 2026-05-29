const ROOM_TYPES = {
    Standard: "STANDARD",
    StandardDouble: "STANDARD_DOUBLE",
    Suite: "SUITE",
    JuniorSuite: "JUNIOR_SUITE",
    Presidential: "PRESIDENTIAL"
} as const;

export type RoomTypes = typeof ROOM_TYPES[keyof typeof ROOM_TYPES]

export interface IRoom {
    id: number,
    roomNumber: string,
    roomType: RoomTypes,
    price: number,
    available: boolean
    capacity: number,
    size: number,
    name: string,
    description: string,
}

// Diccionario profesional para las etiquetas de la interfaz de usuario (UI)
export const ROOM_TYPE_LABELS: Record<RoomTypes, string> = {
    [ROOM_TYPES.Standard]: "Standard Room",
    [ROOM_TYPES.StandardDouble]: "Standard Double Room",
    [ROOM_TYPES.Suite]: "Suite",
    [ROOM_TYPES.JuniorSuite]: "Junior Suite",
    [ROOM_TYPES.Presidential]: "Suite Presidencial"
};