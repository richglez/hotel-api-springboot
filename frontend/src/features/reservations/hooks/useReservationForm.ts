import {type ChangeEvent, type SyntheticEvent, useState} from "react";
import type {IRoom} from "../../rooms/types/models/Room.ts";
import type {ICreateReservation} from "../types/dtos/reservations.dto.create.ts";
import reservationService from "../api/reservationService.ts";
import {getClientIdFromToken} from "../../auth/utils/tokenUtils.ts";
import {calculateNights} from "../utils/reservationUtils.ts";

interface UseReservationFormReturn {
    reservation: ICreateReservation;
    selectedRoom: IRoom | null;
    submitting: boolean;
    success: boolean;
    formError: string | null;
    nights: number;
    total: number;
    handleChange: (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    changeGuests: (field: "adults" | "children", delta: number) => void;
    handleSubmit: (e: SyntheticEvent<HTMLFormElement>) => Promise<void>;
}

const useReservationForm = (
    rooms: IRoom[],
    token: string | null
): UseReservationFormReturn => {

    const [selectedRoom, setSelectedRoom] = useState<IRoom | null>(null);
    const [formError, setFormError] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const [success, setSuccess] = useState<boolean>(false);

    const [reservation, setReservation] = useState<ICreateReservation>({
        checkIn: "",
        checkOut: "",
        clientId: getClientIdFromToken(token),
        roomId: 0,
        adults: 1,
        children: 0,
    });

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const {name, value} = e.target;
        if (name === "roomId") {
            const numericId = Number(value);
            setSelectedRoom(rooms.find(r => r.id === numericId) ?? null);
            setReservation(prev => ({...prev, roomId: numericId}));
        } else {
            setReservation(prev => ({...prev, [name]: value}));
        }
    };

    const changeGuests = (field: "adults" | "children", delta: number): void => {
        setReservation(prev => ({
            ...prev,
            [field]: Math.max(0, (prev[field] ?? 0) + delta),
        }));
    };

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!reservation.checkIn || !reservation.checkOut || reservation.roomId === 0) {
            setFormError("Please complete check-in, check-out and select a room.");
            return;
        }
        if (new Date(reservation.checkOut) <= new Date(reservation.checkIn)) {
            setFormError("Check-out must be after check-in.");
            return;
        }

        const payload: ICreateReservation = {
            ...reservation,
            checkIn: `${reservation.checkIn}T12:00:00`,
            checkOut: `${reservation.checkOut}T12:00:00`,
        };

        try {
            setSubmitting(true);
            setFormError(null);
            await reservationService.create(payload);
            setSuccess(true);
        } catch (err: any) {
            setFormError(err.message ?? "Failed to create reservation.");
        } finally {
            setSubmitting(false);
        }
    };

    const nights = calculateNights(reservation.checkIn, reservation.checkOut);
    const total = selectedRoom ? selectedRoom.price * nights : 0;

    return {
        reservation, selectedRoom, submitting, success,
        formError, nights, total,
        handleChange, changeGuests, handleSubmit,
    };
};

export default useReservationForm;