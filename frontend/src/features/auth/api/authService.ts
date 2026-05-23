const BASE_URL = "http://localhost:8080/api/auth";

const authService = {
    register: async () => {
        const res = await fetch(`${BASE_URL}/register`, {
            method: 'POST'
        });
        if (!res.ok) throw new Error('Failed register user');
        return res.json();
    },
    login: async () => {
        const res = await fetch(`${BASE_URL}/login`, {
            method: 'POST'
        })
        if (!res.ok) throw new Error('Failed login user');
        return res.json();
    }
}

export default authService;