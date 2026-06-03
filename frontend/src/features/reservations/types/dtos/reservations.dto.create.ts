export interface ICreateReservation {
    checkIn: string;
    checkOut: string;
    clientId: number;
    roomId: number;
    adults: number,
    children: number
}