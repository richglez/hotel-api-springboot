// reservationUtils.ts
export const calculateNights = (checkIn: string, checkOut: string): number => {
    if (!checkIn || !checkOut) return 0;
    return Math.max(
        0,
        Math.ceil(
            (new Date(checkOut).getTime() - new Date(checkIn).getTime())
            / (1000 * 60 * 60 * 24)
        )
    );
};

// calculateNights — lógica pura, testeable, reutilizable en un futuro summary o historial de reservas.