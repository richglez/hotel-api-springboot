const ROOM_TYPES = {
    Standard: "STANDARD",
    StandardDouble: "STANDARD_DOUBLE",
    Suite: "SUITE",
    JuniorSuite: "JUINIOR_SUITE",
    Presidental: "PRESIDENTAL"
} as const;

export type RoomTypes = typeof ROOM_TYPES[keyof typeof ROOM_TYPES]

export interface IRoom {
    id: string,
    name: string,
    description: string,
    guest: number,
    roomNumber: number,
    roomType: RoomTypes,
    price: number,
    available: boolean
}