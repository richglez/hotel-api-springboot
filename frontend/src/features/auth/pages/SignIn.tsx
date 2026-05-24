import styles from "./SignIn.module.css"
import {Link, useNavigate} from "react-router-dom";
import {type ChangeEvent, type SyntheticEvent, useState} from "react";
import type {LoginRequest} from "../types/LoginRequest.ts";
import authService from "../api/authService.ts";

const SignIn = () => {
    const navigate = useNavigate();

    const [form, setForm] = useState<LoginRequest>({
        email: "",
        password: ""
    })

    const [touched, setTouched] = useState<Record<string, boolean>>({})
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setForm(prev => ({...prev, [e.target.name]: e.target.value}))
    }

    const handleBlur = (e: ChangeEvent<HTMLInputElement>) => {
        setTouched(prev => ({...prev, [e.target.name]: true}));
    }

    const isFieldValid = (field: keyof LoginRequest) => {
        switch (field) {
            case "email":
                return /\S+@\S+\.\S+/.test(form.email);
            case "password":
                return form.password.length >= 8
            default:
                return true;
        }
    }

    const validate = (): string | null => {
        if (!form.email.trim()) return "Email is required";
        if (!form.password.trim()) return "Password is required";
        return null;
    }

    const handleSubmit = async (e: SyntheticEvent<HTMLFormElement>) => {
        e.preventDefault();

        const validationError = validate();
        if (validationError) {
            setError(validationError);
            return;
        }

        setError(null);
        setLoading(true);
        try {
            const response = await authService.login(form);
            localStorage.setItem("token", response.token) // save token in browser
            navigate("/");
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message)
            } else {
                setError("Login failed. Please try again.")
            }
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className={styles.container}>
            <form method={"POST"} onSubmit={handleSubmit}>
                <div className={styles.card}>

                    <p className={styles.eyebrow}>Welcome back again!</p>

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
                        <div className={styles.inputWrap}>
                            <input
                                type="email"
                                name={"email"}
                                value={form.email}
                                className={styles.input}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                placeholder={"email@domain.com"}/>
                            {touched.email && isFieldValid("email") && (
                                <span className={styles.checkIcon}>
                                        <svg viewBox="0 0 20 20" fill="none">
                                            <circle cx="10" cy="10" r="9" stroke="currentColor" strokeWidth="1.5"/>
                                            <path d="M6 10l3 3 5-5" stroke="currentColor" strokeWidth="1.5"
                                                  strokeLinecap="round"
                                                  strokeLinejoin="round"/>
                                        </svg>
                                    </span>
                            )}
                        </div>

                    </div>

                    <div className={styles.fieldWrap}>
                        <label className={styles.label}>Password</label>
                        <div className={styles.inputWrapper}>
                            <input
                                type="password"
                                name={"password"}
                                value={form.password}
                                className={styles.input}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                placeholder={"*************"}/>
                            {touched.email && isFieldValid("email") && (
                                <span className={styles.checkIcon}>
                                        <svg viewBox="0 0 20 20" fill="none">
                                            <circle cx="10" cy="10" r="9" stroke="currentColor" strokeWidth="1.5"/>
                                            <path d="M6 10l3 3 5-5" stroke="currentColor" strokeWidth="1.5"
                                                  strokeLinecap="round"
                                                  strokeLinejoin="round"/>
                                        </svg>
                                    </span>
                            )}
                        </div>
                    </div>

                    <div className={styles.forgot}>
                        <a href="#">Forget password?</a>
                    </div>

                    <button
                        type={"submit"}
                        className={styles.btn}
                    >{loading ? "Signing in..." : "Sign in"}</button>

                    <div className={styles.divider}>
                        <span>o</span>
                    </div>

                    <div className={styles.footer}>
                        <span>Don't have an account?</span>
                        <Link to="/register">Signup here</Link>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default SignIn;