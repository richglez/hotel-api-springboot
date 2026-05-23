import {useState} from "react";
import styles from "./SignUp.module.css";
import {Link, useNavigate} from "react-router-dom";
import authService from "../api/authService";
import type {RegisterRequest} from "../types/RegisterRequest";
import * as React from "react";

const SignUp = () => {
    const navigate = useNavigate();

    const [form, setForm] = useState<RegisterRequest>({
        name: "",
        lastName: "",
        email: "",
        password: "",
        phone: "",
    });

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false); // inicialmente no se esta mandando nada -> false

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm(prev => ({...prev, [e.target.name]: e.target.value}));
    };

    const validate = (): string | null => {
        if (!form.email.trim()) return "Email is required";
        if (!/\S+@\S+\.\S+/.test(form.email)) return "Invalid email format"
        if (!form.name.trim()) return "Name is required";
        if (!form.lastName.trim()) return "Last name is required";
        if (form.password.length < 8) return "Password must be at least 8 characters";
        return null
    }

    const handleSubmit = async (e: React.SyntheticEvent<HTMLFormElement>) => {

        e.preventDefault();

        const validationError = validate();
        if (validationError) {
            setError(validationError)
            return; // ← corta antes de llamar al servicio
        }


        setError(null); // si pasa la prueba de arriba -> no hay un error
        setLoading(true); // pasara a un loading -> send
        try {
            const response = await authService.register(form);
            localStorage.setItem("token", response.token);
            navigate("/"); // despues del registro rederige al usuario al home
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message)
            } else {
                setError("Registration failed")
            }
        } finally {
            setLoading(false); // termina la carga si hubo un exito o un error
        }
    };

    return (
        <div className={styles.container}>
            <form method={"POST"} onSubmit={handleSubmit}>
                <div className={styles.card}>

                    <p className={styles.eyebrow}>Create your account</p>

                    <div className={styles.ornament}>
                        <div className={styles.ornamentLine}/>
                        <div className={styles.ornamentDiamond}/>
                        <div className={styles.ornamentLine}/>
                    </div>

                    <h1 className={styles.brand}>Grand Palacio</h1>
                    <p className={styles.sub}>Hotel &amp; Reservations</p>

                    {error && <p className={styles.error}>{error}</p>}

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Email</label>
                        <input
                            type="email"
                            name="email"
                            className={styles.input}
                            placeholder="email@domain.com"
                            value={form.email}
                            onChange={handleChange}
                        />
                    </div>

                    <div className={styles.row}>
                        <div className={styles.fieldWrap}>
                            <label className={styles.label}>Name</label>
                            <input
                                type="text"
                                name="name"
                                className={styles.input}
                                placeholder="John"
                                value={form.name}
                                onChange={handleChange}
                            />
                        </div>
                        <div className={styles.fieldWrap}>
                            <label className={styles.label}>Last name</label>
                            <input
                                type="text"
                                name="lastName"
                                className={styles.input}
                                placeholder="Doe"
                                value={form.lastName}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Password</label>
                        <input
                            minLength={8}
                            type="password"
                            name="password"
                            className={styles.input}
                            placeholder="••••••••••••"
                            value={form.password}
                            onChange={handleChange}
                        />
                        <p className={styles.hint}>Minimum 8 characters</p>
                    </div>

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Phone</label>
                        <input
                            type="tel"
                            name="phone"
                            className={styles.input}
                            placeholder="+555 0000 000"
                            value={form.phone}
                            onChange={handleChange}
                        />
                    </div>

                    <button
                        type={"submit"}
                        className={styles.btn}
                        disabled={loading}
                    >
                        {loading ? "Creating account..." : "Create account"}
                    </button>

                    <div className={styles.divider}>
                        <span>o</span>
                    </div>

                    <div className={styles.footer}>
                        <span>Already have an account?</span>
                        <Link to="/login">Sign in</Link>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default SignUp;