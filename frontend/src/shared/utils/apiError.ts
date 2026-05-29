export async function handleErrorResponse(
    res: Response,
    getError: (status: number) => string
): Promise<never> {
    const body = await res.json().catch(() => null);
    const message = body?.message ?? getError(res.status);
    throw new Error(message);
}