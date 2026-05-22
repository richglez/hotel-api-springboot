import type {IClient} from "./Client.ts";
import type {IRoom} from "./Room.ts";

export interface IReservation {
    id: number,
    checkIn: string,
    checkOut: string,
    client: IClient,
    room: IRoom
}