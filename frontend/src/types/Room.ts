const ROOM_TYPES = {
    Standard: "STANDARD",
    StandardDouble: "STANDARD_DOUBLE",
    Suite: "SUITE",
    JuniorSuite: "JUNIOR_SUITE",
    Presidential: "PRESIDENTIAL"
} as const;

export type RoomTypes = typeof ROOM_TYPES[keyof typeof ROOM_TYPES]

export interface IRoom {
    id: string,
    roomNumber: number,
    roomType: RoomTypes,
    price: number,
    available: boolean
    capacity: number,
    size: number,
    name: string,
    description: string,
}