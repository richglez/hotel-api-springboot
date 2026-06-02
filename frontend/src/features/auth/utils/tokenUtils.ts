// tokenUtils.ts

// Decodificar el JWT para leer el id del usuario autenticado.
export const getClientIdFromToken = (token: string | null): number => {
    if (!token) return 0;
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.id ?? 0;
    } catch {
        return 0;
    }
};