// src/utils/roomTypeMapper.ts
export const ROOM_TYPE_META: Record<string, { icon: string; badge: string }> = {
    STANDARD: {icon: 'ti-bed', badge: 'Estándar'},
    STANDARD_DOUBLE: {icon: 'ti-bed', badge: 'Doble'},
    SUITE: {icon: 'ti-crown', badge: 'Suite'},
    JUNIOR_SUITE: {icon: 'ti-building', badge: 'Junior Suite'},
    PRESIDENTIAL: {icon: 'ti-star', badge: 'Presidencial'},
};